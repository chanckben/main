package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_PROFILE_LIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.commands.AddCommand.MESSAGE_ADD_SUCCESS;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DEADLINE_INVALID_SEMESTER;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_MODULE;
import static seedu.address.logic.commands.AddCommand.MESSAGE_EDIT_SUCCESS;
import static seedu.address.logic.commands.AddCommand.MESSAGE_MODULE_NOT_ADDED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManagerStub;
import seedu.address.model.ModelManagerStubWithNonEmptyProfileModule;
import seedu.address.model.ModelStubWithNonEmptyProfileModule;
import seedu.address.model.profile.Year;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;

//@@author wanxuanong

public class AddCommandTest {

    // No profile exists, hence modules cannot be added
    @Test
    public void execute_noExistingProfile_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommand = new AddCommand(Collections.singletonList(moduleCode), semester, null, deadlines);
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_EMPTY_PROFILE_LIST, () ->
                addCommand.execute(new ModelManagerStub()));
    }

    // No module code added, user inputs "add m/"
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(
                Collections.singletonList(new ModuleCode(null)), 0, null, null));
    }

    // No semester added, user inputs "add m/CS1231 y/"
    @Test
    public void constructor_nullSemester_throwsNullPointerException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        assertThrows(NullPointerException.class, () -> new AddCommand(
                Collections.singletonList(moduleCode), new Year(null).getSemester(), null, null));
    }

    // Invalid module, valid semester, user inputs "add m/123ABC y/2.1"
    @Test
    public void execute_invalidModuleCode_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(INVALID_MODCODE_DESC);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModule = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, Arrays.asList(moduleCode)), () ->
                addCommandModule.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, Arrays.asList(moduleCode)), () ->
                addCommandModule.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }


    // Module has been added before (to a future semester)
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.2").getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModule = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, "CS1101S"), () ->
                addCommandModule.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, "CS1101S"), () ->
                addCommandModule.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules added, some duplicate
    @Test
    public void execute_multipleDuplicateModules_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_AMY));
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        List<ModuleCode> duplicate = new ArrayList<>();
        duplicate.add(new ModuleCode("CS1231"));
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, duplicate), () ->
                addCommandModules.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_MODULE, duplicate), () ->
                addCommandModules.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Valid module code, different capitalizations
    @Test
    public void execute_validModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandAllCap = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);
        AddCommand addCommandVariety = new AddCommand(
                Collections.singletonList(new ModuleCode("Ma1521")), semester, null, deadlines);
        AddCommand addCommandNoCap = new AddCommand(
                Collections.singletonList(new ModuleCode("ma1521")), semester, null, deadlines);

        try {
            // Unit Tests
            assertEquals(addCommandAllCap.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandVariety.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandNoCap.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            // Integration Tests
            assertEquals(addCommandAllCap.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandVariety.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            assertEquals(addCommandNoCap.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    // Multiple modules added, some invalid
    @Test
    public void execute_multipleInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_AMY));
        moduleCodes.add(new ModuleCode("CS1111"));
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        List<ModuleCode> invalidCodes = new ArrayList<>();
        invalidCodes.add(new ModuleCode("CS1111"));
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidCodes), () ->
                addCommandModules.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, invalidCodes), () ->
                addCommandModules.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules added, all invalid
    @Test
    public void execute_multipleAllInvalidModuleCodes_throwsCommandException() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode("IS4000"));
        moduleCodes.add(new ModuleCode("CS1111"));
        moduleCodes.add(new ModuleCode("MA2000"));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        // Unit Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                addCommandModules.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_MODULE, moduleCodes), () ->
                addCommandModules.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Multiple modules added, all valid
    @Test
    public void execute_multipleValidModules_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        moduleCodes.add(new ModuleCode(VALID_MODCODE_BOB));
        moduleCodes.add(new ModuleCode("GER1000"));
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandModules = new AddCommand(moduleCodes, semester, null, deadlines);

        try {
            // Unit Test
            assertEquals(addCommandModules.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCodes));
            // Integration Test
            assertEquals(addCommandModules.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCodes));
        } catch (CommandException e) {
            fail();
        }

    }

    // Valid grade, user inputs "add m/MA1521, y/1.1, g/C+"
    @Test
    public void execute_validGrade_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        String grade = VALID_GRADE_BOB;
        ArrayList<Deadline> deadlines = new ArrayList<>();
        AddCommand addCommandGrade = new AddCommand(
                Collections.singletonList(moduleCode), semester, grade, deadlines);
        try {
            // Unit Test
            assertEquals(addCommandGrade.execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
            // Integration Test
            assertEquals(addCommandGrade.execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(),
                    String.format(MESSAGE_ADD_SUCCESS, moduleCode));
        } catch (CommandException e) {
            fail();
        }
    }

    // Adding task to a past semester
    @Test
    public void execute_addTaskToNonCurrentSemester_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("IS1103");
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        // Unit Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_INVALID_SEMESTER, () ->
                addCommandTask.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_DEADLINE_INVALID_SEMESTER, () ->
                addCommandTask.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Adding task to a non existing module
    @Test
    public void execute_addTaskToNonExistingModule_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_BOB);
        int semester = new Year("1.1").getSemester();
        String task = VALID_TASK_BOB;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_BOB);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_BOB);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_BOB, task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        // Unit Test
        assertThrows(CommandException.class, MESSAGE_MODULE_NOT_ADDED, () ->
                addCommandTask.execute(new ModelStubWithNonEmptyProfileModule()));
        // Integration Test
        assertThrows(CommandException.class, MESSAGE_MODULE_NOT_ADDED, () ->
                addCommandTask.execute(new ModelManagerStubWithNonEmptyProfileModule()));
    }

    // Adding multiple tasks, valid
    @Test
    public void execute_addMultipleTasks_success() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        int semester = new Year("1.1").getSemester();
        String taskA = VALID_TASK_AMY;
        LocalDate dateA = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime timeA = LocalTime.parse(VALID_DEADLINE_TIME_AMY);
        String taskB = VALID_TASK_BOB;
        LocalDate dateB = LocalDate.parse(VALID_DEADLINE_DATE_BOB);
        LocalTime timeB = LocalTime.parse(VALID_DEADLINE_TIME_BOB);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, taskA, dateA, timeA));
        deadlines.add(new Deadline(VALID_MODCODE_AMY, taskB, dateB, timeB));
        AddCommand addCommandTasks = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        String updateMessage = String.format(MESSAGE_EDIT_SUCCESS, moduleCode);
        String addSuccess = String.format("These task(s) have been added: %1$s; %2$s; ", taskA, taskB);
        updateMessage += "\n" + addSuccess;

        try {
            // Unit Test
            assertEquals(addCommandTasks
                    .execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
            // Integration Test
            assertEquals(addCommandTasks
                    .execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    // Duplicate tasks added
    @Test
    public void execute_duplicateTask_error() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year("1.1").getSemester();
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY);

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, task, date, time));
        AddCommand addCommandTask = new AddCommand(
                Collections.singletonList(moduleCode), semester, null, deadlines);

        String updateMessage = String.format(MESSAGE_EDIT_SUCCESS, moduleCode);
        String addError = String.format("Failed to add these duplicate task(s): %1$s; ", task);
        updateMessage += "\n" + addError;

        try {
            // Unit Test
            assertEquals(addCommandTask
                    .execute(new ModelStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
            // Integration Test
            assertEquals(addCommandTask
                    .execute(new ModelManagerStubWithNonEmptyProfileModule()).getFeedbackToUser(), updateMessage);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void equals() {
        LocalDate modelDate = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime modelTime = LocalTime.parse(VALID_DEADLINE_TIME_AMY, DateTimeFormatter.ofPattern("HH:mm"));

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, VALID_TASK_AMY, modelDate, modelTime));
        deadlines.add(new Deadline(VALID_MODCODE_BOB, VALID_TASK_BOB, modelDate, modelTime));

        AddCommand addAmyCommand = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_AMY)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        AddCommand addBobCommand = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_BOB)),
                new Year(VALID_SEMESTER_BOB).getSemester(), VALID_GRADE_BOB, deadlines);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addAmyCommandCopy = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_AMY)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        assertTrue(addAmyCommand.equals(addAmyCommandCopy));

        // same module code but remainder fields differ -> returns true
        AddCommand addBobCommandDiff = new AddCommand(Collections.singletonList(new ModuleCode(VALID_MODCODE_BOB)),
                new Year(VALID_SEMESTER_AMY).getSemester(), VALID_GRADE_AMY, deadlines);
        assertTrue(addBobCommand.equals(addBobCommandDiff));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBobCommand));
    }

}
