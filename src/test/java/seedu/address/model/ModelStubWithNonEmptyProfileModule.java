package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.course.AcceptedCourses;
import seedu.address.model.course.AcceptedFocusArea;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Preclusions;
import seedu.address.model.module.PrereqTreeNode;
import seedu.address.model.module.Prereqs;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.module.personal.Personal;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.MaxModsException;

/**
 * Stub for Model with a non-empty profile and some modules
 */
public class ModelStubWithNonEmptyProfileModule extends ModelStubEmpty {
    public ModelStubWithNonEmptyProfileModule() {
        // Set profileList

        Module moduleAmy = new Module(new ModuleCode(VALID_MODCODE_AMY), new Title(""), new Prereqs(""),
                new Preclusions(""), new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        Module moduleCs = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""),
                new Preclusions(""), new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        Module moduleIs = new Module(new ModuleCode("IS1103"), new Title(""), new Prereqs(""),
                new Preclusions(""), new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());

        Deadline deadlineA = new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY);
        Deadline deadlineB = new Deadline(VALID_MODCODE_AMY, VALID_TASK_BOB);
        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(deadlineA);
        deadlines.add(deadlineB);

        String gradeA = VALID_GRADE_AMY;
        String gradeIs = VALID_GRADE_BOB;

        ObservableList<Profile> profileList = FXCollections.observableArrayList();
        Profile profile = new Profile(new Name("JOHN"), new CourseName(
                AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));

        try {
            profile.addModule(1, moduleAmy);
            profile.addModule(1, moduleCs);
            profile.addModule(3, moduleIs);
        } catch (MaxModsException e) {
            fail();
        }

        Personal personalA = new Personal();
        moduleAmy.setPersonal(personalA);
        personalA.addDeadline(deadlineA);
        personalA.addDeadline(deadlineB);
        personalA.setGrade(gradeA);

        Personal personalIs = new Personal();
        moduleIs.setPersonal(personalIs);
        personalIs.setGrade(gradeIs);
        profileList.add(profile);
        this.profileList = profileList;
        filteredProfiles = new FilteredList<>(this.profileList);

        // Set moduleList

        Module moduleBob = new Module(new ModuleCode(VALID_MODCODE_BOB), new Title(""), new Prereqs(""),
                new Preclusions(""), new ModularCredits("4"), new Description(""),
                new SemesterData(new ArrayList<>()), null);
        Module moduleGe = new Module(new ModuleCode("GER1000"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                null);
        moduleList.add(moduleAmy);
        moduleList.add(moduleBob);
        moduleList.add(moduleCs);
        moduleList.add(moduleIs);
        moduleList.add(moduleGe);
    }

    @Override
    public boolean hasOneProfile() {
        return true;
    }

    @Override
    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);
        this.deadlineList.add(deadline);
    }

    @Override
    public void deleteDeadline(Deadline deadline) {
        requireNonNull(deadline);
        Iterator<Deadline> iter = this.deadlineList.iterator();
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(deadline.getModuleCode())
                    && dl.getDescription().toUpperCase().equals(deadline.getDescription().toUpperCase())) {
                iter.remove();
            }
        }
    }

    @Override
    public void replaceDeadline(Deadline oldDeadline, Deadline newDeadline) {
        requireAllNonNull(oldDeadline, newDeadline);
        Iterator<Deadline> iter = this.deadlineList.iterator();
        boolean flag = false;
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(oldDeadline.getModuleCode())
                    && dl.getDescription().equals(oldDeadline.getDescription())) {
                iter.remove();
                flag = true;
            }
        }
        if (flag) {
            this.deadlineList.add(newDeadline);
        }
    }

    @Override
    public void deleteModuleDeadlines(ModuleCode mc) {
        Iterator<Deadline> iter = this.deadlineList.iterator();
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().toString().equals(mc.toString())) {
                iter.remove();
            }
        }
    }

    @Override
    public void clearDeadlineList() {
        this.deadlineList.clear();
    }

    @Override
    public void setNewDeadlineList(Profile editedProfile) {
        if (editedProfile.getDeadlines() != null) {
            this.deadlineList.addAll(editedProfile.getDeadlines());
        }
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
        return moduleList.stream().filter(mod -> mod.getModuleCode().equals(moduleCode)).findFirst().get();
    }
}