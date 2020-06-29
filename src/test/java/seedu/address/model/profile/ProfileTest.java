package seedu.address.model.profile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
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
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.profile.exceptions.MaxModsException;

//@@author joycelynteo

public class ProfileTest {

    private Name name = new Name(VALID_NAME_AMY);
    private CourseName courseName = new CourseName(VALID_COURSE_AMY);
    private int semester = new Year(VALID_SEMESTER_AMY).getSemester();
    private FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
    private Profile amy = new Profile(name, courseName, semester, focusArea);

    @Test
    public void constructor_nullCompulsoryInputs_throwsNullPointerException() {
        // null name
        assertThrows(NullPointerException.class, () -> new Profile(null, courseName, semester, focusArea));

        // null course
        assertThrows(NullPointerException.class, () -> new Profile(name, null, semester, focusArea));
    }

    @Test
    public void getModules_noModulesPresent_throwsParseException() {
        assertThrows(ParseException.class, () -> amy.getModules(1));
    }

    @Test
    public void getModuleSemester_moduleNotPresent_throwsNoSuchElementException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(NoSuchElementException.class, () -> amy.getModuleSemester(moduleCode));
    }

    @Test
    public void getModule_moduleNotPresent_throwsModuleNotFoundException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(ModuleNotFoundException.class, () -> amy.getModule(moduleCode));
    }

    @Test
    public void hasModule() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        Module moduleAmy = new Module(moduleCodeAmy, new Title(""), new Prereqs(""),
                new Preclusions(""), new ModularCredits("4"), new Description(""),
                new SemesterData(new ArrayList<>()), new PrereqTreeNode());
        try {
            amy.addModule(2, moduleAmy);
        } catch (MaxModsException e) {
            fail();
        }

        // has module -> return true
        assertTrue(amy.hasModule(moduleCodeAmy));

        // does not have module -> return false
        ModuleCode moduleCodeBob = new ModuleCode(VALID_MODCODE_BOB);
        assertFalse(amy.hasModule(moduleCodeBob));
    }

    @Test
    public void deleteModule_moduleNotPresent_throwsModuleNotFoundException() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(ModuleNotFoundException.class, () -> amy.deleteModule(moduleCodeAmy));
    }

    @Test
    public void isSameProfile() {
        // same object -> returns true
        assertTrue(amy.isSameProfile(amy));

        // null -> returns false
        assertFalse(amy.isSameProfile(null));

        // different name -> returns false
        Profile amyDifferentName = new Profile(new Name(VALID_NAME_BOB), courseName, semester, focusArea);
        assertFalse(amy.isSameProfile(amyDifferentName));

        // same name -> returns true
        Profile otherAmy = new Profile(name, new CourseName(VALID_COURSE_BOB),
                new Year(VALID_SEMESTER_BOB).getSemester(), null);
        assertTrue(amy.isSameProfile(otherAmy));
    }

    @Test
    public void equals() {

        // same name and course -> true
        Profile amyCopy = new Profile(name, courseName, 4, null);
        assertTrue(amy.equals(amyCopy));

        // same object -> returns true
        assertTrue(amy.equals(amy));

        // null -> returns false
        assertFalse(amy.equals(null));

        // different type -> returns false
        assertFalse(amy.equals(5));

        // different profile -> returns false
        Profile bob = new Profile(new Name(VALID_NAME_BOB), new CourseName(VALID_COURSE_BOB),
                new Year(VALID_SEMESTER_BOB).getSemester(), new FocusArea(VALID_FOCUS_AREA_BOB));
        assertFalse(amy.equals(bob));

        // different name -> returns false
        Profile amyDifferentName = new Profile(new Name(VALID_NAME_BOB), courseName, semester, focusArea);
        assertFalse(amy.equals(amyDifferentName));

        // different course -> returns false
        Profile amyDifferentCourse = new Profile(name, new CourseName(VALID_COURSE_BOB), semester, focusArea);
        assertFalse(amy.equals(amyDifferentCourse));
    }
}
