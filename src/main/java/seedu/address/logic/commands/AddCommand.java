package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MAX_MODS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModuleList;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Personal;
import seedu.address.model.profile.exceptions.MaxModsException;

//@@author joycelynteo

/**
 * Adds a profile to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module or task to the module.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_YEAR + "SEMESTER "
            + "(" + PREFIX_TASK + "TASK) "
            + "(" + PREFIX_DEADLINE + "DEADLINE) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_YEAR + "4 "
            + "(" + PREFIX_TASK + "assignment) "
            + "(" + PREFIX_DEADLINE + "2020-03-16 23:59) ";

    public static final String MESSAGE_ADD_SUCCESS = "New Module(s) added: %1$s";
    public static final String MESSAGE_EDIT_SUCCESS = "Existing module updated: %1$s";
    public static final String MESSAGE_DEADLINE_INVALID_SEMESTER = "Error: You can only add tasks to modules that "
            + "are already added in the current semester";
    public static final String MESSAGE_DUPLICATE_MODULE = "Error: Module(s) already in profile: %1$s\n"
            + "If you're adding task(s), please specify specify task name and/or deadline.\n"
            + "If you're adding module(s), check that the module(s) are not currently in the profile.";
    public static final String MESSAGE_MODULE_NOT_ADDED = "Error: Please add this module to a semester first.";
    public static final String MESSAGE_UNFULFILLED_PREREQS = "NOTE: You may not have fulfilled the prerequisites of "
            + "%1$s before year %2$s semester %3$s";
    public static final String PREREQ_STRING = "\nPrerequisites of %1$s: %2$s";

    private final List<ModuleCode> toAdd;
    private int addSemester;
    private String addGrade;
    private ArrayList<Deadline> addDeadlines;

    public AddCommand(List<ModuleCode> moduleCodes, int semester, String grade, ArrayList<Deadline> deadlines) {
        requireNonNull(moduleCodes);
        requireNonNull(semester);

        toAdd = moduleCodes;
        addSemester = semester;
        if (grade != null) {
            addGrade = grade;
        }
        if (deadlines.size() != 0) {
            addDeadlines = deadlines;
        }
    }

    /*@Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);


        if (!profileManager.hasOneProfile()) {
            throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
        }
        Profile profile = profileManager.getFirstProfile();

        // If some module codes are invalid, raise error
        // If there are multiple modules and some modules currently exist, raise error
        List<ModuleCode> invalidMods = new ArrayList<>();
        List<ModuleCode> existingMods = new ArrayList<>();
        for (ModuleCode moduleCode: toAdd) {
            if (!moduleManager.hasModule(moduleCode)) {
                invalidMods.add(moduleCode);
            }
            if (profile.hasModule(moduleCode)) {
                existingMods.add(moduleCode);
            }
        }
        if (!invalidMods.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE, invalidMods));
        }
        if (toAdd.size() > 1 && !existingMods.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, existingMods));
        }

        // Case of multiple module codes: Execute AddCommand multiple times
        if (toAdd.size() > 1) {
            List<ModuleCode> modsUnfulfilledPrereqs = new ArrayList<>();
            StringBuilder prereqMsg = new StringBuilder();
            for (ModuleCode moduleCode: toAdd) {
                AddCommand command = new AddCommand(Collections.singletonList(moduleCode), addSemester, null,
                        new ArrayList<>());
                CommandResult result = command.execute(profileManager, courseManager, moduleManager);
                // Store unfulfilled prerequisites in a list and provide user with more information
                if (result.getFeedbackToUser().contains("prerequisites")) {
                    modsUnfulfilledPrereqs.add(moduleCode);
                    prereqMsg.append(String.format(PREREQ_STRING, moduleCode,
                            moduleManager.getModule(moduleCode).getPrereqs()));
                }
            }
            // Modules with unfulfilled prerequisites are being added
            if (modsUnfulfilledPrereqs.size() > 0) {
                int year = (addSemester + 1) / 2;
                int sem = 2 - (addSemester % 2);
                return new CommandResult(
                        String.format(MESSAGE_UNFULFILLED_PREREQS, modsUnfulfilledPrereqs, year, sem) + prereqMsg,
                        true);
            }
            // All prerequisites of modules are fulfilled
            return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, toAdd), true);
        }

        ModuleCode moduleCodeToAdd = toAdd.iterator().next();
        Module moduleToAdd = moduleManager.getModule(moduleCodeToAdd);
        boolean hasModule = false;
        int semesterOfModule = 0;

        // Check whether this module has been added to Profile semester TreeMap
        for (ModuleList semesterList: profile.getSemModTreeMap().values()) {
            for (Module moduleInSem: semesterList) {
                if (moduleToAdd.isSameModule(moduleInSem)) {
                    hasModule = true;
                    moduleToAdd = moduleInSem;
                    TreeMap<Integer, ModuleList> treeMap = profile.getSemModTreeMap();
                    semesterOfModule = getKey(treeMap, semesterList);
                }
            }
        }

        Personal personal;
        if (hasModule) { // Module already added to semester
            personal = moduleToAdd.getPersonal();
            if (addGrade == null && addDeadlines == null) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, moduleCodeToAdd));
            }
        } else { // Module does not exist
            if (addSemester == 0 | addDeadlines != null) {
                throw new CommandException(MESSAGE_MODULE_NOT_ADDED);
            }
            // Create Personal object
            personal = new Personal();

        }

        int currentSemester = profile.getOverallSemester();

        if (addGrade != null && !hasModule) {
            personal.setGrade(addGrade);
        } else if (addGrade != null && hasModule) {
            throw new CommandException("To add grade to an existing module, use: edit m/MODULE g/GRADE");
        }

        String addDeadlineAppendMsg = "";
        if (addDeadlines != null) {
            // Check if the deadline is added to a module in the current semester
            if (hasModule && semesterOfModule != currentSemester) {
                // This module has been added but it is not in current semester
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }
            if (!hasModule && addSemester != currentSemester) {
                // This module has not been added anywhere and semester given by user does not match current semester
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }

            if (!hasModule) {
                try {
                    profile.addModule(addSemester, moduleToAdd);
                    hasModule = true;
                } catch (MaxModsException e) {
                    throw new CommandException(MESSAGE_MAX_MODS);
                }
            }

            String addDeadlinesSuccessAppendMsg = "";
            String addDeadlinesDuplicateAppendMsg = "";
            for (Deadline deadline : addDeadlines) {
                if (personal.hasDeadline(deadline)) {
                    if (addDeadlinesDuplicateAppendMsg.equals("")) {
                        addDeadlinesDuplicateAppendMsg += "\nFailed to add these duplicate task(s): ";
                    }
                    addDeadlinesDuplicateAppendMsg += deadline.getDescription() + "; ";
                } else {
                    if (addDeadlinesSuccessAppendMsg.equals("")) {
                        addDeadlinesSuccessAppendMsg += "\nThese task(s) have been added: ";
                    }
                    addDeadlinesSuccessAppendMsg += deadline.getDescription() + "; ";
                    personal.addDeadline(deadline);
                    profileManager.addDeadline(deadline);
                }
            }
            if (!addDeadlinesSuccessAppendMsg.equals("")) {
                addDeadlineAppendMsg += addDeadlinesSuccessAppendMsg;
            }
            if (!addDeadlinesDuplicateAppendMsg.equals("")) {
                addDeadlineAppendMsg += addDeadlinesDuplicateAppendMsg;
            }
        }

        // Set the status of the module
        if (addSemester < currentSemester) {
            personal.setStatus("completed");
        } else if (addSemester == currentSemester) {
            personal.setStatus("in progress");
        } else {
            personal.setStatus("not taken");
        }

        moduleToAdd.setPersonal(personal);

        String messageShown;
        if (!hasModule) {
            try {
                profile.addModule(addSemester, moduleToAdd);
            } catch (MaxModsException e) {
                throw new CommandException(MESSAGE_MAX_MODS);
            }
            // Check if prerequisites of the module have been fulfilled
            if (moduleToAdd.getPrereqTreeNode() != null && !moduleToAdd.getPrereqTreeNode()
                    .hasFulfilledPrereqs(profile.getAllModuleCodesBefore(addSemester))) {
                int year = (addSemester + 1) / 2;
                int sem = 2 - (addSemester % 2);
                messageShown = String.format(MESSAGE_UNFULFILLED_PREREQS, moduleCodeToAdd, year, sem)
                        + String.format(PREREQ_STRING, moduleCodeToAdd, moduleToAdd.getPrereqs());
            } else {
                messageShown = MESSAGE_ADD_SUCCESS;
            }
            profileManager.setDisplayedView(profile);
            profile.updateCap();
            return new CommandResult(String.format(messageShown, moduleCodeToAdd), true);
        } else {
            messageShown = MESSAGE_EDIT_SUCCESS;
            if (addDeadlines != null) {
                messageShown += addDeadlineAppendMsg;
            }
        }

        profile.updateCap();
        return new CommandResult(String.format(messageShown, moduleCodeToAdd), false);
    }*/

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasOneProfile()) {
            throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
        }
        Profile profile = model.getFirstProfile();

        // If some module codes are invalid, raise error
        // If there are multiple modules and some modules currently exist, raise error
        List<ModuleCode> invalidMods = new ArrayList<>();
        List<ModuleCode> existingMods = new ArrayList<>();
        for (ModuleCode moduleCode: toAdd) {
            if (!model.hasModule(moduleCode)) {
                invalidMods.add(moduleCode);
            }
            if (profile.hasModule(moduleCode)) {
                existingMods.add(moduleCode);
            }
        }
        if (!invalidMods.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE, invalidMods));
        }
        if (toAdd.size() > 1 && !existingMods.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, existingMods));
        }

        // Case of multiple module codes: Execute AddCommand multiple times
        if (toAdd.size() > 1) {
            List<ModuleCode> modsUnfulfilledPrereqs = new ArrayList<>();
            StringBuilder prereqMsg = new StringBuilder();
            for (ModuleCode moduleCode: toAdd) {
                AddCommand command = new AddCommand(Collections.singletonList(moduleCode), addSemester, null,
                        new ArrayList<>());
                CommandResult result = command.execute(model);
                // Store unfulfilled prerequisites in a list and provide user with more information
                if (result.getFeedbackToUser().contains("prerequisites")) {
                    modsUnfulfilledPrereqs.add(moduleCode);
                    prereqMsg.append(String.format(PREREQ_STRING, moduleCode,
                            model.getModule(moduleCode).getPrereqs()));
                }
            }
            // Modules with unfulfilled prerequisites are being added
            if (modsUnfulfilledPrereqs.size() > 0) {
                int year = (addSemester + 1) / 2;
                int sem = 2 - (addSemester % 2);
                return new CommandResult(
                        String.format(MESSAGE_UNFULFILLED_PREREQS, modsUnfulfilledPrereqs, year, sem) + prereqMsg,
                        true);
            }
            // All prerequisites of modules are fulfilled
            return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, toAdd), true);
        }

        ModuleCode moduleCodeToAdd = toAdd.iterator().next();
        Module moduleToAdd = model.getModule(moduleCodeToAdd);
        boolean hasModule = false;
        int semesterOfModule = 0;

        // Check whether this module has been added to Profile semester TreeMap
        for (ModuleList semesterList: profile.getSemModTreeMap().values()) {
            for (Module moduleInSem: semesterList) {
                if (moduleToAdd.isSameModule(moduleInSem)) {
                    hasModule = true;
                    moduleToAdd = moduleInSem;
                    TreeMap<Integer, ModuleList> treeMap = profile.getSemModTreeMap();
                    semesterOfModule = getKey(treeMap, semesterList);
                }
            }
        }

        Personal personal;
        if (hasModule) { // Module already added to semester
            personal = moduleToAdd.getPersonal();
            if (addGrade == null && addDeadlines == null) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE, moduleCodeToAdd));
            }
        } else { // Module does not exist
            if (addSemester == 0 | addDeadlines != null) {
                throw new CommandException(MESSAGE_MODULE_NOT_ADDED);
            }
            // Create Personal object
            personal = new Personal();

        }

        int currentSemester = profile.getOverallSemester();

        if (addGrade != null && !hasModule) {
            personal.setGrade(addGrade);
        } else if (addGrade != null && hasModule) {
            throw new CommandException("To add grade to an existing module, use: edit m/MODULE g/GRADE");
        }

        String addDeadlineAppendMsg = "";
        if (addDeadlines != null) {
            // Check if the deadline is added to a module in the current semester
            if (hasModule && semesterOfModule != currentSemester) {
                // This module has been added but it is not in current semester
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }
            if (!hasModule && addSemester != currentSemester) {
                // This module has not been added anywhere and semester given by user does not match current semester
                throw new CommandException(MESSAGE_DEADLINE_INVALID_SEMESTER);
            }

            if (!hasModule) {
                try {
                    profile.addModule(addSemester, moduleToAdd);
                    hasModule = true;
                } catch (MaxModsException e) {
                    throw new CommandException(MESSAGE_MAX_MODS);
                }
            }

            String addDeadlinesSuccessAppendMsg = "";
            String addDeadlinesDuplicateAppendMsg = "";
            for (Deadline deadline : addDeadlines) {
                if (personal.hasDeadline(deadline)) {
                    if (addDeadlinesDuplicateAppendMsg.equals("")) {
                        addDeadlinesDuplicateAppendMsg += "\nFailed to add these duplicate task(s): ";
                    }
                    addDeadlinesDuplicateAppendMsg += deadline.getDescription() + "; ";
                } else {
                    if (addDeadlinesSuccessAppendMsg.equals("")) {
                        addDeadlinesSuccessAppendMsg += "\nThese task(s) have been added: ";
                    }
                    addDeadlinesSuccessAppendMsg += deadline.getDescription() + "; ";
                    personal.addDeadline(deadline);
                    model.addDeadline(deadline);
                }
            }
            if (!addDeadlinesSuccessAppendMsg.equals("")) {
                addDeadlineAppendMsg += addDeadlinesSuccessAppendMsg;
            }
            if (!addDeadlinesDuplicateAppendMsg.equals("")) {
                addDeadlineAppendMsg += addDeadlinesDuplicateAppendMsg;
            }
        }

        // Set the status of the module
        if (addSemester < currentSemester) {
            personal.setStatus("completed");
        } else if (addSemester == currentSemester) {
            personal.setStatus("in progress");
        } else {
            personal.setStatus("not taken");
        }

        moduleToAdd.setPersonal(personal);

        String messageShown;
        if (!hasModule) {
            try {
                profile.addModule(addSemester, moduleToAdd);
            } catch (MaxModsException e) {
                throw new CommandException(MESSAGE_MAX_MODS);
            }
            // Check if prerequisites of the module have been fulfilled
            if (moduleToAdd.getPrereqTreeNode() != null && !moduleToAdd.getPrereqTreeNode()
                    .hasFulfilledPrereqs(profile.getAllModuleCodesBefore(addSemester))) {
                int year = (addSemester + 1) / 2;
                int sem = 2 - (addSemester % 2);
                messageShown = String.format(MESSAGE_UNFULFILLED_PREREQS, moduleCodeToAdd, year, sem)
                        + String.format(PREREQ_STRING, moduleCodeToAdd, moduleToAdd.getPrereqs());
            } else {
                messageShown = MESSAGE_ADD_SUCCESS;
            }
            model.setDisplayedView(profile);
            profile.updateCap();
            return new CommandResult(String.format(messageShown, moduleCodeToAdd), true);
        } else {
            messageShown = MESSAGE_EDIT_SUCCESS;
            if (addDeadlines != null) {
                messageShown += addDeadlineAppendMsg;
            }
        }

        profile.updateCap();
        return new CommandResult(String.format(messageShown, moduleCodeToAdd), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    /**
     * Returns key of the given value
     */
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
