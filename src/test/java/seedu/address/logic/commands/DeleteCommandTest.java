package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_DEADLINE_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_GRADE_FAILURE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_GRADE_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PROFILE_FAILURE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_NOT_TAKING_MODULE;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManagerStub;
import seedu.address.model.ModelManagerStubWithEmptyProfile;
import seedu.address.model.ModelManagerStubWithNonEmptyProfileModule;
import seedu.address.model.ModelStubEmpty;
import seedu.address.model.ModelStubWithEmptyProfile;
import seedu.address.model.ModelStubWithNonEmptyProfileModule;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.profile.Name;

//@@author wanxuanong

public class DeleteCommandTest {

    // No profile exists, hence profile cannot deleted
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        DeleteCommand deleteCommandName = new DeleteCommand(name);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, name), () ->
                deleteCommandName.execute(new ModelStubEmpty()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, name), () ->
                deleteCommandName.execute(new ModelManagerStub()));
    }

    // No name, user inputs "delete n/"
    // ParserException thrown in DeleteCommandParser
    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(new Name(null)));
    }

    // Invalid name, e.g. show n/Mark
    @Test
    public void execute_invalidName_throwsCommandException() {
        DeleteCommand invalidName = new DeleteCommand(new Name("Mark"));

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, "mark"), () ->
                invalidName.execute(new ModelStubWithEmptyProfile()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_DELETE_PROFILE_FAILURE, "mark"), () ->
                invalidName.execute(new ModelManagerStubWithEmptyProfile()));
    }


    // No module code added, user inputs "delete m/"
    // ParserException thrown in DeleteCommandParser
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new DeleteCommand(Collections.singletonList(new ModuleCode(null))));
    }

    // Module has not been added to profile before

    @Test
    public void execute_moduleNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        DeleteCommand deleteCommand = new DeleteCommand(Collections.singletonList(moduleCode));

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, Arrays.asList(moduleCode)), () ->
                deleteCommand.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, Arrays.asList(moduleCode)), () ->
                deleteCommand.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }


    // Valid module code, different capitalizations
    @Test
    public void execute_validModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        DeleteCommand deleteCommandAllCap = new DeleteCommand(Collections.singletonList(moduleCode));
        DeleteCommand deleteCommandVariety = new DeleteCommand(Collections.singletonList(new ModuleCode("Cs1231")));
        DeleteCommand deleteCommandNoCap = new DeleteCommand(Collections.singletonList(new ModuleCode("cs1231")));

        try {
            // Unit Tests
            assertTrue(deleteCommandAllCap.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandVariety.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandNoCap.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            // Integration Tests
            assertTrue(deleteCommandAllCap.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandVariety.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
            assertTrue(deleteCommandNoCap.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser()
                    .equals(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCode)));
        } catch (CommandException e) {
            fail();
        }
    }

    // All invalid module codes
    @Test
    public void execute_allInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeCs = new ModuleCode("CS1111");
        ModuleCode moduleCodeMa = new ModuleCode("MA2000");
        moduleCodes.add(moduleCodeCs);
        moduleCodes.add(moduleCodeMa);
        DeleteCommand deleteCommandModule = new DeleteCommand(moduleCodes);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                deleteCommandModule.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                deleteCommandModule.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Some invalid module codes
    @Test
    public void execute_someInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        List<ModuleCode> invalidModuleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeCs = new ModuleCode("CS1111");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeCs);
        invalidModuleCodes.add(moduleCodeCs);
        DeleteCommand deleteCommandModule = new DeleteCommand(moduleCodes);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidModuleCodes), () ->
                deleteCommandModule.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidModuleCodes), () ->
                deleteCommandModule.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules, some modules not taking
    @Test
    public void execute_someModulesNotAdded_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeB = new ModuleCode(VALID_MODCODE_BOB);
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeB);
        List<ModuleCode> modulesNotTaking = new ArrayList<>();
        modulesNotTaking.add(moduleCodeB);
        DeleteCommand deleteCommandModules = new DeleteCommand(moduleCodes);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, modulesNotTaking), () ->
                deleteCommandModules.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_NOT_TAKING_MODULE, modulesNotTaking), () ->
                deleteCommandModules.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules deleted
    @Test
    public void execute_multipleModules_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeB = new ModuleCode("CS1101S");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeB);
        DeleteCommand deleteCommandModules = new DeleteCommand(moduleCodes);

        try {
            // Unit Test
            assertEquals(deleteCommandModules.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCodes));
            // Integration Test
            assertEquals(deleteCommandModules.execute(
                    new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }

    }


    // User inputs "delete t/homework" without module tag
    @Test
    public void constructor_noModuleCodeForDeleteTask_throwsNullPointerException() {
        String moduleCode = VALID_MODCODE_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        ArrayList<Deadline> tasks = new ArrayList<>();
        tasks.add(new Deadline(moduleCode, VALID_TASK_AMY, date, time));

        assertThrows(NullPointerException.class, () -> new DeleteCommand(null, tasks));
    }

    // Task to be deleted does not exist

    @Test
    public void execute_taskDoesNotExist_throwsCommandException() {
        String moduleCode = VALID_MODCODE_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        ArrayList<Deadline> tasks = new ArrayList<>();
        tasks.add(new Deadline(moduleCode, "tutorial", date, time));
        DeleteCommand deleteCommandTask =
                new DeleteCommand(Collections.singletonList(new ModuleCode(moduleCode)), tasks);

        String updateMessage = String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, moduleCode);
        String deleteError = String.format("Failed to delete these task(s) as they were not added: %1$s; ", "tutorial");
        updateMessage += "\n" + deleteError;

        try {
            // Unit Test
            assertEquals(deleteCommandTask.execute(
                    new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
            // Integration Test
            assertEquals(deleteCommandTask.execute(
                    new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    // Multiple tasks deleted
    @Test
    public void execute_allTasks_success() {
        String moduleCode = VALID_MODCODE_AMY;
        ArrayList<Deadline> tasks = new ArrayList<>();
        Deadline taskA = new Deadline(moduleCode, VALID_TASK_AMY);
        Deadline taskB = new Deadline(moduleCode, VALID_TASK_BOB);
        tasks.add(taskA);
        tasks.add(taskB);
        DeleteCommand deleteCommandTasks =
                new DeleteCommand(Collections.singletonList(new ModuleCode(moduleCode)), tasks);

        String updateMessage = String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, moduleCode);
        String deleteSuccess = String.format("These task(s) have been deleted: %1$s; %2$s; ",
                VALID_TASK_AMY, VALID_TASK_BOB);
        updateMessage += "\n" + deleteSuccess;

        try {
            // Unit Test
            assertEquals(deleteCommandTasks.execute(
                    new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
            // Integration Test
            assertEquals(deleteCommandTasks.execute(
                    new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }

    }


    // No grade to be deleted
    @Test
    public void execute_gradeNotAdded_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        String grade = VALID_GRADE_AMY;
        DeleteCommand deleteCommandGrade = new DeleteCommand(Collections.singletonList(moduleCode), grade);

        // Unit Test
        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, Arrays.asList(moduleCode)), () ->
                        deleteCommandGrade.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, Arrays.asList(moduleCode)), () ->
                        deleteCommandGrade.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules, some missing grades
    @Test
    public void execute_someGradesNotAdded_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        List<ModuleCode> modulesNoGrades = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        String grade = "";
        ModuleCode moduleCodeCs = new ModuleCode("CS1101S");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeCs);
        modulesNoGrades.add(moduleCodeCs);
        DeleteCommand deleteCommandGrades = new DeleteCommand(moduleCodes, grade);

        // Unit Test
        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, modulesNoGrades), () ->
                        deleteCommandGrades.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class,
                String.format(MESSAGE_DELETE_GRADE_FAILURE, modulesNoGrades), () ->
                        deleteCommandGrades.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules' grades deleted
    @Test
    public void execute_allGrades_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeIs = new ModuleCode("IS1103");
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeIs);
        String grade = "";
        DeleteCommand deleteCommandGrades = new DeleteCommand(moduleCodes, grade);

        try {
            // Unit Test
            assertEquals(deleteCommandGrades.execute(
                    new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_GRADE_SUCCESS, moduleCodes));
            // Integration Test
            assertEquals(deleteCommandGrades.execute(
                    new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_DELETE_GRADE_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }
    }
}

