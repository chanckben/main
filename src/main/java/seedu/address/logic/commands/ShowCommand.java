package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModuleList;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;


//@@author wanxuanong
/**
 * Displays details requested by user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": The following items can be shown:\n"
            + "Course, with " + PREFIX_COURSE_NAME + "COURSE\n"
            + "Course Focus Area, with " + PREFIX_FOCUS_AREA + "FOCUS_AREA\n"
            + "Module, with " + PREFIX_MODULE + "MODULE_CODE\n"
            + "Modules taken in a semester, with " + PREFIX_YEAR + "SEMESTER_NUMBER\n";

    public static final String MESSAGE_SUCCESS_NAME = "Here is your academic overview: ";
    public static final String MESSAGE_SUCCESS_MODULE_LIST = "All modules taken in semester are shown: "
            + "\nEnter [show m/MODULE_CODE] to find out more about the module";
    public static final String MESSAGE_SUCCESS_MODULE = "The details for this module are show below:";
    public static final String MESSAGE_SUCCESS_FOCUS_AREA = "Modules in this focus area are shown below: ";
    public static final String MESSAGE_SUCCESS_COURSE = "Course requirements for this course are show below: ";



    private final Object toParse;
    private Object toShow;
    /**
     * Creates a ShowCommand to show the specified object
     * Can be any object from this list: (Course, CourseFocusArea, Module, List of Modules in a semester)
     */

    public ShowCommand(Name name) {
        requireNonNull(name);
        this.toParse = name;
    }

    public ShowCommand(Year year) {
        requireNonNull(year);
        this.toParse = year;
    }

    public ShowCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        this.toParse = moduleCode;
    }

    public ShowCommand(String focusArea) {
        requireNonNull(focusArea);
        this.toParse = focusArea;
    }

    public ShowCommand(CourseName courseName) {
        requireNonNull(courseName);
        this.toParse = courseName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String message = "";
        try {
            if (toParse instanceof Name) {
                message = MESSAGE_SUCCESS_NAME;

                if (model.getFilteredPersonList().size() != 0) {
                    Profile profile = model.getFirstProfile();

                    if ((profile.getName().toString().toLowerCase()).equals(toParse.toString().toLowerCase())) {
                        toShow = profile;
                        model.setDisplayedView((Profile) toShow);
                    } else {
                        throw new CommandException("Profile with name does not exist.");
                    }
                } else {
                    throw new CommandException("Profile does not exist.");
                }

            } else if (toParse instanceof Year) {
                if (!model.hasOneProfile()) {
                    throw new CommandException(MESSAGE_EMPTY_PROFILE_LIST);
                }

                message = MESSAGE_SUCCESS_MODULE_LIST;
                Integer semester = ((Year) toParse).getSemester();
                toShow = model.getFirstProfile().getModules(semester);
                FilteredList<Module> filteredModules = new FilteredList<>(((ModuleList) toShow).getModuleList());
                model.setDisplayedView(filteredModules);

            } else if (toParse instanceof ModuleCode) {
                message = MESSAGE_SUCCESS_MODULE;
                ModuleCode moduleCode = (ModuleCode) toParse;

                if (!model.hasModule(moduleCode)) {
                    throw new CommandException(String.format(MESSAGE_INVALID_MODULE, moduleCode));
                }

                toShow = model.getModule(moduleCode);
                model.setDisplayedView((Module) toShow);

            } else if (toParse instanceof String) {
                message = MESSAGE_SUCCESS_FOCUS_AREA;
                String focusArea = (String) toParse;
                toShow = model.getCourseFocusArea(focusArea); (
                        (CourseFocusArea) toShow).initListModule(model.getModuleList());
                model.setDisplayedView((CourseFocusArea) toShow);

            } else if (toParse instanceof CourseName) {
                message = MESSAGE_SUCCESS_COURSE;
                CourseName courseName = (CourseName) toParse;
                toShow = model.getCourse(courseName);
                model.setDisplayedView((Course) toShow);

            }

            return new CommandResult(String.format(message, toShow), true);

        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && toParse.equals(((ShowCommand) other).toParse));
    }
}
