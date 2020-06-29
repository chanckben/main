package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.MultipleTagException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Year;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.ModuleCode;

//@@author wanxuanong

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_YEAR, PREFIX_MODULE,
                        PREFIX_FOCUS_AREA, PREFIX_COURSE_NAME);

        // Get Name
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            if (onePrefixPresent(argMultimap, PREFIX_YEAR, PREFIX_MODULE, PREFIX_FOCUS_AREA, PREFIX_COURSE_NAME)) {
                throw new MultipleTagException();
            }
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new ShowCommand(name);
        }

        // Get Semester
        if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            if (onePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_MODULE, PREFIX_FOCUS_AREA, PREFIX_COURSE_NAME)) {
                throw new MultipleTagException();
            }
            Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            return new ShowCommand(year);
        }

        // Get Module
        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            if (onePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_YEAR, PREFIX_FOCUS_AREA, PREFIX_COURSE_NAME)) {
                throw new MultipleTagException();
            }
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new ShowCommand(moduleCode);
        }

        // Get Focus Area
        if (arePrefixesPresent(argMultimap, PREFIX_FOCUS_AREA)) {
            if (onePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_YEAR, PREFIX_MODULE, PREFIX_COURSE_NAME)) {
                throw new MultipleTagException();
            }
            String focusArea = argMultimap.getValue(PREFIX_FOCUS_AREA).get().toUpperCase();
            if (focusArea.isEmpty()) {
                throw new ParseException(MESSAGE_MISSING_COURSE_FOCUS_AREA);
            }
            return new ShowCommand(focusArea); // returns String
        }

        // Get Course
        if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
            if (onePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_YEAR, PREFIX_MODULE, PREFIX_FOCUS_AREA)) {
                throw new MultipleTagException();
            }
            CourseName courseName = ParserUtil.parseCourseName(
                    argMultimap.getValue(PREFIX_COURSE_NAME).get().toUpperCase());
            return new ShowCommand(courseName); // returns CourseName
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean onePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

