package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_NEW_TASK_OR_DEADLINE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_OLD_TASK;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOCUS_AREA_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FOCUS_AREA_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_TASK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.module.personal.Grade;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;

//@@author joycelynteo
public class EditCommandParserTest {

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_allProfileFieldsPresent_success() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // All profile fields present
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY + FOCUS_AREA_DESC_AMY,
                new EditCommand(name, courseName, semester, VALID_FOCUS_AREA_AMY));
    }

    @Test
    public void parse_multipleProfileFieldsPresent_failure() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // Multiple names
        assertParseFailure(parser, NAME_DESC_BOB + NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, "Error: you can only specify one name!");

        // Multiple courses
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_BOB + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, "Error: you can only specify one course!");

        // Multiple semesters
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_BOB + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, "Error: you can only specify one semester!");

        // Multiple focus areas
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY + FOCUS_AREA_DESC_BOB
                + FOCUS_AREA_DESC_AMY, "Error: you can only specify one focus area!");
    }

    @Test
    public void parse_allModuleFieldsPresent_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // All module fields present
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY + TASK_DESC_AMY
                + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY, new EditCommand(moduleCode, semester, VALID_GRADE_AMY,
                VALID_TASK_AMY, VALID_NEW_TASK_AMY, VALID_DEADLINE_AMY));
    }

    @Test
    public void parse_multipleModuleFieldsPresent_failure() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // Multiple module codes
        assertParseFailure(parser, MODCODE_DESC_BOB + MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY
                + TASK_DESC_AMY + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY,
                "Error: you can only edit one module at a time!");

        // Multiple semesters
        assertParseFailure(parser, MODCODE_DESC_AMY + SEMESTER_DESC_BOB + SEMESTER_DESC_AMY + GRADE_DESC_AMY
                + TASK_DESC_AMY + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY,
                "Error: you can only specify one semester!");

        // Multiple grades
        assertParseFailure(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_BOB + GRADE_DESC_AMY
                + TASK_DESC_AMY + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY,
                "Error: you can only specify one grade for each module!");

        // Multiple old tasks
        assertParseFailure(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY + TASK_DESC_BOB
                + TASK_DESC_AMY + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY,
                "Error: you can only edit one task at a time!");

        // Multiple new tasks
        assertParseFailure(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY + TASK_DESC_AMY
                + NEW_TASK_DESC_BOB + NEW_TASK_DESC_AMY + DEADLINE_DESC_AMY,
                "Error: you can only specify one description for each task!");

        // Multiple new deadlines
        assertParseFailure(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY + TASK_DESC_AMY
                + NEW_TASK_DESC_AMY + DEADLINE_DESC_BOB + DEADLINE_DESC_AMY,
                "Error: you can only specify one deadline for each task!");
    }

    @Test
    public void parse_optionalProfileFieldMissing_success() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile profile = new Profile(name, courseName, semester, focusArea);

        // Only name field present
        assertParseSuccess(parser, NAME_DESC_AMY,
                new EditCommand(name, null, 0, null));

        // Only course field present
        assertParseSuccess(parser, COURSE_DESC_AMY,
                new EditCommand(null, courseName, 0, null));

        // Only semester field present
        assertParseSuccess(parser, SEMESTER_DESC_AMY,
                new EditCommand(null, null, semester, null));

        // Only focus area field present
        assertParseSuccess(parser, FOCUS_AREA_DESC_AMY,
                new EditCommand(null, null, 0, VALID_FOCUS_AREA_AMY));
    }

    @Test
    public void parse_optionalModuleFieldMissing_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODCODE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // Only ModuleCode and semester present
        assertParseSuccess(parser, MODCODE_DESC_AMY + SEMESTER_DESC_AMY,
                new EditCommand(moduleCode, semester, null, null, null, null));

        // Only ModuleCode and Grade present
        assertParseSuccess(parser, MODCODE_DESC_AMY + GRADE_DESC_AMY,
                new EditCommand(moduleCode, 0, VALID_GRADE_AMY, null, null, null));

        // Only ModuleCode, OldTask and NewTask Present
        assertParseSuccess(parser, MODCODE_DESC_AMY + TASK_DESC_AMY + NEW_TASK_DESC_AMY,
                new EditCommand(moduleCode, 0, null, VALID_TASK_AMY, VALID_NEW_TASK_AMY, null));

        // Only ModuleCode, OldTask and NewDeadline Present
        assertParseSuccess(parser, MODCODE_DESC_AMY + TASK_DESC_AMY + DEADLINE_DESC_AMY,
                new EditCommand(moduleCode, 0, null, VALID_TASK_AMY, null, VALID_DEADLINE_AMY));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // No fields present
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Only ModuleCode present
        assertParseFailure(parser, MODCODE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Only ModuleCode and old task Present
        assertParseFailure(parser, MODCODE_DESC_AMY + TASK_DESC_AMY, MESSAGE_MISSING_NEW_TASK_OR_DEADLINE);

        // Only ModuleCode and new task Present
        assertParseFailure(parser, MODCODE_DESC_AMY + NEW_TASK_DESC_AMY, MESSAGE_MISSING_OLD_TASK);

        // Only ModuleCode and new deadline Present
        assertParseFailure(parser, MODCODE_DESC_AMY + DEADLINE_DESC_AMY, MESSAGE_MISSING_OLD_TASK);
    }

    @Test
    public void parse_invalidProfileValue_failure() {
        // Invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Invalid course
        assertParseFailure(parser, INVALID_COURSE_DESC, MESSAGE_INVALID_COURSE);

        // Invalid current semester
        assertParseFailure(parser, INVALID_SEMESTER_DESC, Year.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidModuleValue_failure() {
        // Invalid module code
        assertParseFailure(parser, INVALID_MODCODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // Invalid module semester
        assertParseFailure(parser, MODCODE_DESC_AMY + INVALID_SEMESTER_DESC, Year.MESSAGE_CONSTRAINTS);

        // Invalid grade
        assertParseFailure(parser, MODCODE_DESC_AMY + INVALID_GRADE_DESC, Grade.MESSAGE_CONSTRAINTS);

        // Invalid deadline
        assertParseFailure(parser, MODCODE_DESC_AMY + TASK_DESC_AMY + INVALID_DEADLINE_DESC,
                Deadline.MESSAGE_CONSTRAINTS);
    }
}
