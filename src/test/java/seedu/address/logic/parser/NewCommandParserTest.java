package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOCUS_AREA_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FOCUS_AREA_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOCUS_AREA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NewCommand;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;

//@@author joycelynteo
public class NewCommandParserTest {

    private NewCommandParser parser = new NewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile profile = new Profile(name, courseName, semester, focusArea);

        // All fields present
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY + FOCUS_AREA_DESC_AMY,
                new NewCommand(profile));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, new NewCommand(profile));

        // multiple courses - last course accepted
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_BOB + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, new NewCommand(profile));

        // multiple semester - last semester accepted
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_BOB + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, new NewCommand(profile));

        // multiple focus area - last focus area accepted
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY + FOCUS_AREA_DESC_BOB
                + FOCUS_AREA_DESC_AMY, new NewCommand(profile));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();

        // No focus area
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY,
                new NewCommand(new Profile(name, courseName, semester, null)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE);

        // Missing name prefix
        assertParseFailure(parser, VALID_NAME_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY, expectedMessage);

        // Missing course prefix
        assertParseFailure(parser, NAME_DESC_AMY + VALID_COURSE_AMY + SEMESTER_DESC_AMY, expectedMessage);

        // Missing semester prefix
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_AMY + VALID_SEMESTER_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // Invalid course
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_COURSE_DESC + SEMESTER_DESC_AMY
                + FOCUS_AREA_DESC_AMY, MESSAGE_INVALID_COURSE);

        // Invalid semester
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_AMY + INVALID_SEMESTER_DESC
                + FOCUS_AREA_DESC_AMY, Year.MESSAGE_CONSTRAINTS);

        // Invalid focus area
        assertParseFailure(parser, NAME_DESC_AMY + COURSE_DESC_AMY + SEMESTER_DESC_AMY
                + INVALID_FOCUS_AREA_DESC, MESSAGE_INVALID_COURSE_FOCUS_AREA);

    }

}
