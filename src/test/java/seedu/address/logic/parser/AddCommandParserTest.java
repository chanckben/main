package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_BOB;
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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.module.personal.Grade;
import seedu.address.model.profile.Year;

//@@author wanxuanong
public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<ModuleCode> moduleCodes = new ArrayList<>();
        ModuleCode moduleCodeA = new ModuleCode(VALID_MODCODE_AMY);
        ModuleCode moduleCodeB = new ModuleCode(VALID_MODCODE_BOB);
        moduleCodes.add(moduleCodeA);
        moduleCodes.add(moduleCodeB);
        int semester = new Year(VALID_SEMESTER_BOB).getSemester();
        String grade = VALID_GRADE_BOB;

        ArrayList<Deadline> deadlines = new ArrayList<>();
        String task = VALID_TASK_BOB;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_BOB);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_BOB, DateTimeFormatter.ofPattern("HH:mm"));
        String taskA = VALID_TASK_AMY;
        LocalDate dateA = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime timeA = LocalTime.parse(VALID_DEADLINE_TIME_BOB, DateTimeFormatter.ofPattern("HH:mm"));
        deadlines.add(new Deadline(VALID_MODCODE_BOB, task, date, time));
        deadlines.add(new Deadline(VALID_MODCODE_BOB, taskA, dateA, timeA));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODCODE_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB,
                new AddCommand(Collections.singletonList(moduleCodeB), semester, grade, deadlines));

        // multiple modules - all modules accepted
        assertParseSuccess(parser, MODCODE_DESC_AMY + MODCODE_DESC_BOB + SEMESTER_DESC_BOB,
                new AddCommand(moduleCodes, semester, grade, deadlines));

        // multiple tasks - all tasks accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + TASK_DESC_AMY
                + TASK_DESC_BOB,
                new AddCommand(Collections.singletonList(moduleCodeB), semester, grade, deadlines));

        // multiple deadlines - all deadlines accepted
        assertParseSuccess(parser, MODCODE_DESC_BOB + TASK_DESC_BOB + DEADLINE_DESC_BOB
                + TASK_DESC_AMY + DEADLINE_DESC_AMY,
                new AddCommand(Collections.singletonList(moduleCodeB), semester, grade, deadlines));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        String grade = VALID_GRADE_AMY;
        String task = VALID_TASK_AMY;
        LocalDate date = LocalDate.parse(VALID_DEADLINE_DATE_AMY);
        LocalTime time = LocalTime.parse(VALID_DEADLINE_TIME_AMY, DateTimeFormatter.ofPattern("HH:mm"));

        ArrayList<Deadline> deadlines = new ArrayList<>();
        deadlines.add(new Deadline(VALID_MODCODE_AMY, task, date, time));

        // No grade
        assertParseSuccess(parser, MODCODE_DESC_AMY + TASK_DESC_AMY + DEADLINE_DESC_AMY,
                new AddCommand(Collections.singletonList(moduleCode), semester, grade, deadlines));

        // No task and deadline
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY,
                new AddCommand(Collections.singletonList(moduleCode), semester, grade, deadlines));

        // No grade, task and deadline
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY,
                new AddCommand(Collections.singletonList(moduleCode), semester, grade, deadlines));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODCODE_BOB + SEMESTER_DESC_BOB, expectedMessage);

        // missing semester prefix
        assertParseFailure(parser, MODCODE_DESC_BOB + VALID_SEMESTER_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODCODE_BOB + VALID_SEMESTER_BOB, expectedMessage);
    }

    @Test
    public void parse_deadlineFieldMissing_failure() {
        String expectedMessage = "Please provide a deadline for each task!";
        // For multiple tasks
        assertParseFailure(parser, MODCODE_DESC_AMY + TASK_DESC_AMY + DEADLINE_DESC_AMY
                + TASK_DESC_BOB, expectedMessage);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODCODE_DESC + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid semester (if error message differs, refer to parseSemester method under ParserUtil.java)
        assertParseFailure(parser, MODCODE_DESC_BOB + INVALID_SEMESTER_DESC + GRADE_DESC_BOB
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, Year.MESSAGE_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + INVALID_GRADE_DESC
                + TASK_DESC_BOB + DEADLINE_DESC_BOB, Grade.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, MODCODE_DESC_BOB + SEMESTER_DESC_BOB + GRADE_DESC_BOB
                + TASK_DESC_BOB + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
