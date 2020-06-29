package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_SEMESTER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Year;
import seedu.address.model.course.AcceptedCourses;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.ModuleCode;

//@@author chanckben
public class ShowCommandParserTest {
    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Semester field present
        Year semester = new Year("1.1");
        assertParseSuccess(parser, " " + PREFIX_YEAR + semester, new ShowCommand(semester));

        // Course field present
        CourseName courseName = new CourseName(AcceptedCourses.COMPUTER_SCIENCE.getName());
        assertParseSuccess(parser, " " + PREFIX_COURSE_NAME + courseName, new ShowCommand(courseName));

        // Module field present
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        assertParseSuccess(parser, " " + PREFIX_MODULE + moduleCode, new ShowCommand(moduleCode));

        // Focus area field present
        String focusArea = "Computer Security";
        assertParseSuccess(parser, " " + PREFIX_FOCUS_AREA + focusArea,
                new ShowCommand(focusArea.toUpperCase()));

        // Name field present
        String name = "john";
        assertParseSuccess(parser, " " + PREFIX_NAME + name, new ShowCommand(new Name(name)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid semester
        String semester = "abc";
        assertParseFailure(parser, " " + PREFIX_YEAR + semester, MESSAGE_INVALID_SEMESTER);
        assertParseFailure(parser, " " + PREFIX_YEAR, MESSAGE_MISSING_SEMESTER);

        // Invalid course
        CourseName courseName = new CourseName("course");
        assertParseFailure(parser, " " + PREFIX_COURSE_NAME + courseName, MESSAGE_INVALID_COURSE);
        assertParseFailure(parser, " " + PREFIX_COURSE_NAME, MESSAGE_MISSING_COURSE);

        // Invalid module
        ModuleCode moduleCode = new ModuleCode("1101");
        assertParseFailure(parser, " " + PREFIX_MODULE + moduleCode, String.format(MESSAGE_INVALID_MODULE, "1101"));
        assertParseFailure(parser, " " + PREFIX_MODULE, MESSAGE_MISSING_MODULE);

        // Invalid focus area
        assertParseFailure(parser, " " + PREFIX_FOCUS_AREA, MESSAGE_MISSING_COURSE_FOCUS_AREA);

        // Invalid name
        assertParseFailure(parser, " " + PREFIX_NAME, MESSAGE_MISSING_NAME);
    }
}
