package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_COURSE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_FOCUS_AREA;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_MODULE;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_MODULE_LIST;
import static seedu.address.logic.commands.ShowCommand.MESSAGE_SUCCESS_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManagerStub;
import seedu.address.model.ModelManagerStubWithCourse;
import seedu.address.model.ModelManagerStubWithEmptyProfile;
import seedu.address.model.ModelManagerStubWithModule;
import seedu.address.model.ModelManagerStubWithNonEmptyProfileModule;
import seedu.address.model.ModelStubEmpty;
import seedu.address.model.ModelStubWithCourse;
import seedu.address.model.ModelStubWithEmptyProfile;
import seedu.address.model.ModelStubWithNonEmptyProfileModule;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Preclusions;
import seedu.address.model.module.PrereqTreeNode;
import seedu.address.model.module.Prereqs;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Year;

//@@author chanckben
public class ShowCommandTest {

    // No profile has been added, user inputs "show n/John" and "show n/123"
    @Test
    public void execute_nameNoProfile_throwsCommandException() {
        ShowCommand showCommandJohn = new ShowCommand(new Name("John"));
        /*assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommandJohn.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));*/
        // Unit Test
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommandJohn.execute(new ModelStubEmpty()));
        // Integration Test
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommandJohn.execute(new ModelManagerStub()));
        ShowCommand showCommand123 = new ShowCommand(new Name("123"));
        /*assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommand123.execute(new ProfileManagerStub(), new CourseManagerStub(), new ModuleManagerStub()));*/
        // Unit Test
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommand123.execute(new ModelStubEmpty()));
        // Integration Test
        assertThrows(CommandException.class, "Profile does not exist.", () ->
                showCommand123.execute(new ModelManagerStub()));
    }

    // No profile has been added, user inputs "show n/"
    // Note that the CommandException would have been thrown in ShowCommandParser
    @Test
    public void constructor_nullNameNoProfile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowCommand(new Name(null)));
    }

    // Valid name, same capitalisation, e.g. show n/John
    // Valid name, all capitalised, e.g. show n/JOHN
    // Valid name, all non-caps, e.g. show n/john
    @Test
    public void execute_validNameOneProfile_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand(new Name("John"));
        ShowCommand showCommandAllCap = new ShowCommand(new Name("JOHN"));
        ShowCommand showCommandNoCap = new ShowCommand(new Name("john"));

        try {
            // Unit Test
            assertTrue(showCommandFirstLetterCap.execute(
                    new ModelStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandAllCap.execute(
                    new ModelStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandNoCap.execute(
                    new ModelStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            // Integration Test
            assertTrue(showCommandFirstLetterCap.execute(
                    new ModelManagerStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandAllCap.execute(
                    new ModelManagerStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
            assertTrue(showCommandNoCap.execute(
                    new ModelManagerStubWithEmptyProfile()).getFeedbackToUser().equals(MESSAGE_SUCCESS_NAME));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid name, e.g. show n/Mark
    @Test
    public void execute_invalidNameOneProfile_throwsCommandException() {
        ShowCommand invalidName = new ShowCommand(new Name("Mark"));

        // Unit Test
        assertThrows(CommandException.class, "Profile with name does not exist.", () ->
                invalidName.execute(new ModelStubWithEmptyProfile()));
        // Integration Test
        assertThrows(CommandException.class, "Profile with name does not exist.", () ->
                invalidName.execute(new ModelManagerStubWithEmptyProfile()));
    }

    // No profile has been added, user inputs "show y/1.1"
    // One profile has been added but with no modules, user inputs "show y/1.1"
    @Test
    public void execute_semesterNoModules_throwsCommandException() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.1"));
        // Integration Tests
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                showCommandSem.execute(new ModelManagerStub()));
        assertThrows(CommandException.class, MESSAGE_INVALID_SEMESTER, () ->
                showCommandSem.execute(new ModelManagerStubWithEmptyProfile()));
    }

    // One profile has been added with a module. Valid semester, user inputs "show y/1.1"
    @Test
    public void execute_validSemesterWithModules_success() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.1"));
        try {
            // Unit Test
            assertTrue(showCommandSem.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE_LIST));
            // Integration Test
            assertTrue(showCommandSem.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE_LIST));
        } catch (CommandException e) {
            fail();
        }
    }

    // One profile has been added with a module. Invalid semester, user inputs "show y/1.2"
    @Test
    public void execute_invalidSemesterWithModules_throwsCommandException() {
        ShowCommand showCommandSem = new ShowCommand(new Year("1.2"));
        // Unit Test
        assertThrows(CommandException.class, MESSAGE_INVALID_SEMESTER, () ->
                showCommandSem.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_INVALID_SEMESTER, () ->
                showCommandSem.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Valid course, first letter of each word capitalised, e.g. show c/Computer Science
    // Valid course, all words capitalised, e.g. show c/COMPUTER SCIENCE
    // Valid course, all words in non-caps, e.g. show c/computer science
    @Test
    public void execute_validCourseName_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand(new CourseName("Computer Science"));
        ShowCommand showCommandAllCap = new ShowCommand(new CourseName("COMPUTER SCIENCE"));
        ShowCommand showCommandNoCap = new ShowCommand(new CourseName("computer science"));

        try {
            // Unit Tests
            assertTrue(showCommandFirstLetterCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandAllCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandNoCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
            // Integration Tests
            assertTrue(showCommandFirstLetterCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandAllCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
            assertTrue(showCommandNoCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_COURSE));
        } catch (CommandException e) {
            fail();
        }
    }

    // No profile has been added, user inputs "show c/"
    // Note that the CommandException would have been thrown in ShowCommandParser
    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ShowCommand(new CourseName(null)));
    }

    // Valid focus area, first letter of each word capitalised, e.g. show f/Computer Security
    // Valid focus area, all words capitalised, e.g. show c/COMPUTER SECURITY
    // Valid focus area, all words in non-caps, e.g. show c/computer security
    @Test
    public void execute_validFocusArea_success() {
        ShowCommand showCommandFirstLetterCap = new ShowCommand("Computer Security");
        ShowCommand showCommandAllCap = new ShowCommand("COMPUTER SECURITY");
        ShowCommand showCommandNoCap = new ShowCommand("computer security");

        try {
            // Unit Tests
            assertTrue(showCommandFirstLetterCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandAllCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandNoCap.execute(new ModelStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
            // Integration Tests
            assertTrue(showCommandFirstLetterCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandAllCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
            assertTrue(showCommandNoCap.execute(new ModelManagerStubWithCourse()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_FOCUS_AREA));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid focus area, e.g. show f/focus area x
    // Integer focus area, e.g. show f/123
    @Test
    public void execute_invalidFocusArea_throwsCommandException() {
        ShowCommand invalidFocusArea = new ShowCommand("focus area x");
        ShowCommand integerFocusArea = new ShowCommand("123");

        // Unit Tests
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                invalidFocusArea.execute(new ModelStubWithCourse()));
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                integerFocusArea.execute(new ModelStubWithCourse()));
        // Integration Tests
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                invalidFocusArea.execute(new ModelManagerStubWithCourse()));
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                integerFocusArea.execute(new ModelManagerStubWithCourse()));
    }

    // Valid module code, all capitalised, e.g. CS1101S
    // Valid module code, some in caps, some non-caps, e.g. Cs1101s
    // Valid module code, all non-caps, e.g. cs1101s
    @Test
    public void execute_validModuleCode_success() {
        ShowCommand showCommandAllCap = new ShowCommand(new ModuleCode("CS1101S"));
        ShowCommand showCommandVariety = new ShowCommand(new ModuleCode("Cs1101s"));
        ShowCommand showCommandNoCap = new ShowCommand(new ModuleCode("cs1101s"));

        try {
            // Unit Tests
            assertTrue(showCommandAllCap.execute(new ModelStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandVariety.execute(new ModelStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandNoCap.execute(new ModelStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
            // Integration Tests
            assertTrue(showCommandAllCap.execute(new ModelManagerStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandVariety.execute(new ModelManagerStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
            assertTrue(showCommandNoCap.execute(new ModelManagerStubWithModule()).getFeedbackToUser()
                    .equals(MESSAGE_SUCCESS_MODULE));
        } catch (CommandException e) {
            fail();
        }
    }

    // Invalid module code, e.g. show m/CS1111
    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ShowCommand invalidCommand = new ShowCommand(new ModuleCode("CS1111"));
        // Unit Tests
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, "CS1111"), () ->
                invalidCommand.execute(new ModelStubWithModule()));
        // Integration Tests
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, "CS1111"), () ->
                invalidCommand.execute(new ModelManagerStubWithModule()));
    }

    private class ModelStubWithModule extends ModelStubEmpty {
        private ModelStubWithModule() {
            Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                    new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                    new PrereqTreeNode());
            moduleList.add(module);
        }
    }

}
