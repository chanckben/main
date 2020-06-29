package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_DEADLINE_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_MODULE_DATA;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MODULE_NOT_ADDED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOCUS_AREA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_MODULE_SUCCESS;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PROFILE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManagerStub;
import seedu.address.model.ModelManagerStubWithEmptyProfile;
import seedu.address.model.ModelManagerStubWithNonEmptyProfileModule;
import seedu.address.model.ModelStubEmpty;
import seedu.address.model.ModelStubWithEmptyProfile;
import seedu.address.model.ModelStubWithNonEmptyProfileModule;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Year;

//@@author joycelynteo

public class EditCommandTest {


    // No profile exists, hence no profile or module to be edited
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        EditCommand editCommand = new EditCommand(name, null, 0, null);

        // Unit Test
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                editCommand.execute(new ModelStubEmpty()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                editCommand.execute(new ModelManagerStub()));
    }

    // No module data, hence there are no modules to edit
    @Test
    public void execute_noModuleData_throwsCommandException() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        EditCommand editCommand = new EditCommand(moduleCodeAmy, 0, VALID_GRADE_AMY, null,
                null, null);

        // Unit Test
        assertThrows(CommandException.class, MESSAGE_EMPTY_MODULE_DATA, () ->
                editCommand.execute(new ModelStubWithEmptyProfile()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_EMPTY_MODULE_DATA, () ->
                editCommand.execute(new ModelManagerStubWithEmptyProfile()));
    }

    // Module to be edited has not been added before
    @Test
    public void execute_moduleNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        EditCommand editCommand = new EditCommand(moduleCode, semester, null, null, null,
                null);

        // Integration Test
        assertThrows(CommandException.class, MESSAGE_MODULE_NOT_ADDED, () ->
                editCommand.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Deadline to be edited does not exist
    @Test
    public void execute_deadlineDoesNotExist_throwsCommandException() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);

        // When editing task description
        EditCommand editTaskCommand = new EditCommand(moduleCodeAmy, 0, null, "task",
                VALID_NEW_TASK_AMY, null);
        // Unit Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editTaskCommand.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editTaskCommand.execute(new ModelManagerStubWithNonEmptyProfileModule()));

        // When editing deadline
        EditCommand editDeadlineCommand = new EditCommand(moduleCodeAmy, 0, null, "task",
                null, VALID_DEADLINE_AMY);
        // Unit Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editDeadlineCommand.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_DOES_NOT_EXIST, () ->
                editDeadlineCommand.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Invalid Focus Area (based off course)
    @Test
    public void execute_invalidFocusArea_throwsCommandException() {
        // Invalid focus area
        EditCommand editCommandInvalid = new EditCommand(null, null, 0,
                INVALID_FOCUS_AREA_DESC);
        // Unit Test
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandInvalid.execute(new ModelStubWithEmptyProfile()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandInvalid.execute(new ModelManagerStubWithEmptyProfile()));

        // Valid focus area, but not for the current course
        EditCommand editCommandValidForWrongCourse = new EditCommand(null, null, 0,
                "f/Financial Analytics");
        // Unit Test
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandValidForWrongCourse.execute(new ModelStubWithEmptyProfile()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_INVALID_COURSE_FOCUS_AREA, () ->
                editCommandValidForWrongCourse.execute(new ModelManagerStubWithEmptyProfile()));
    }

    @Test
    public void execute_validProfileFields_success() {
        Model model = new ModelStubWithEmptyProfile();
        Model modelManager = new ModelManagerStubWithEmptyProfile();

        try {
            // Valid name
            Name name = new Name(VALID_NAME_AMY);
            EditCommand editName = new EditCommand(name, null, 0, null);
            // Unit Test
            assertEquals(editName.execute(new ModelStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS, name));
            // Integration Test
            assertEquals(editName.execute(new ModelManagerStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS, name));

            // Valid course
            CourseName courseName = new CourseName(VALID_COURSE_AMY);
            EditCommand editCourse = new EditCommand(null, courseName, 0, null);
            // Unit Test
            assertEquals(editCourse.execute(new ModelStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    model.getFirstProfile().getName()));
            // Integration Test
            assertEquals(editCourse.execute(new ModelManagerStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    modelManager.getFirstProfile().getName()));

            // Valid current semester
            int semester = new Year(VALID_SEMESTER_AMY).getSemester();
            EditCommand editSemester = new EditCommand(null, null, semester, null);
            // Unit Test
            assertEquals(editSemester.execute(new ModelStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    model.getFirstProfile().getName()));
            // Integration Test
            assertEquals(editSemester.execute(new ModelManagerStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    modelManager.getFirstProfile().getName()));

            // Valid focus area
            EditCommand editFocusArea = new EditCommand(null, null, 0,
                    VALID_FOCUS_AREA_AMY);
            // Unit Test
            assertEquals(editFocusArea.execute(new ModelStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    model.getFirstProfile().getName()));
            // Integration Test
            assertEquals(editFocusArea.execute(new ModelManagerStubWithEmptyProfile())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_PROFILE_SUCCESS,
                    modelManager.getFirstProfile().getName()));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_validModuleFields_success() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);

        try {
            // Valid semester
            int semester = new Year(VALID_SEMESTER_AMY).getSemester();
            EditCommand editSemester = new EditCommand(moduleCodeAmy, semester, null, null,
                    null, null);
            // Unit Test
            assertEquals(editSemester.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
            // Integration Test
            assertEquals(editSemester.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));

            // Valid grade
            EditCommand editGrade = new EditCommand(moduleCodeAmy, 0, VALID_GRADE_AMY, null,
                    null, null);
            // Unit Test
            assertEquals(editGrade.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
            //Integration Test
            assertEquals(editGrade.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));

            // Valid new task
            // NOTE: Necessary to make editTaskCopy for integration test because executing EditCommand in this case
            // would cause the task name of editTask to be modified. Reusing editTask would result in an error
            // because the task name has been changed.
            EditCommand editTask = new EditCommand(moduleCodeAmy, 0, null, VALID_TASK_AMY,
                    VALID_NEW_TASK_AMY, null);
            EditCommand editTaskCopy = new EditCommand(moduleCodeAmy, 0, null, VALID_TASK_AMY,
                    VALID_NEW_TASK_AMY, null);
            // Unit Test
            assertEquals(editTask.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
            // Integration Test
            assertEquals(editTaskCopy.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));

            // Valid new deadline
            EditCommand editDeadline = new EditCommand(moduleCodeAmy, 0, null, VALID_TASK_AMY,
                    null, VALID_DEADLINE_AMY);
            // Unit Test
            assertEquals(editDeadline.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
            // Integration Test
            assertEquals(editDeadline.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
        } catch (CommandException e) {
            fail();
        }
    }

    // Editing grade of future semesters
    @Test
    public void execute_addGradeToFutureSemester_success() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODCODE_AMY);
        EditCommand editCommand = new EditCommand(moduleCodeAmy, 0, VALID_GRADE_AMY, null,
                null, null);

        try {
            // Unit Test
            assertEquals(editCommand.execute(new ModelStubWithNonEmptyProfileModule())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
            // Integration Test
            assertEquals(editCommand.execute(new ModelManagerStubWithNonEmptyProfileModule())
                    .getFeedbackToUser(), String.format(MESSAGE_EDIT_MODULE_SUCCESS, moduleCodeAmy));
        } catch (CommandException e) {
            fail();
        }


    }
}
