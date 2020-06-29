package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_NEW_TASK_OR_DEADLINE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_OLD_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DateTimeException;
import seedu.address.model.profile.Name;

//@@author joycelynteo

/**
 * Parses input arguments and creates a new NewCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException, DateTimeException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COURSE_NAME, PREFIX_YEAR,
                        PREFIX_FOCUS_AREA, PREFIX_MODULE, PREFIX_GRADE, PREFIX_TASK, PREFIX_NEW_TASK,
                        PREFIX_DEADLINE);

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) { // EDIT MODULE
            if (!arePrefixesPresent(argMultimap, PREFIX_YEAR) && !arePrefixesPresent(argMultimap, PREFIX_GRADE)
                    && !arePrefixesPresent(argMultimap, PREFIX_TASK)
                    && !arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)
                    && !arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }

            // Make sure there is only one of each field
            if (argMultimap.getAllValues(PREFIX_MODULE).size() > 1) {
                throw new ParseException("Error: you can only edit one module at a time!");
            }
            if (argMultimap.getAllValues(PREFIX_YEAR).size() > 1) {
                throw new ParseException("Error: you can only specify one semester!");
            }
            if (argMultimap.getAllValues(PREFIX_GRADE).size() > 1) {
                throw new ParseException("Error: you can only specify one grade for each module!");
            }
            if (argMultimap.getAllValues(PREFIX_TASK).size() > 1) {
                throw new ParseException("Error: you can only edit one task at a time!");
            }
            if (argMultimap.getAllValues(PREFIX_NEW_TASK).size() > 1) {
                throw new ParseException("Error: you can only specify one description for each task!");
            }
            if (argMultimap.getAllValues(PREFIX_DEADLINE).size() > 1) {
                throw new ParseException("Error: you can only specify one deadline for each task!");
            }

            String moduleCodeString = argMultimap.getValue(PREFIX_MODULE).get();
            moduleCodeString = moduleCodeString.trim();
            moduleCodeString = moduleCodeString.toUpperCase();
            ModuleCode moduleCode = new ModuleCode(moduleCodeString);

            int intSemester = 0;
            String grade = null;
            String oldTask = null;
            String newTask = null;
            String newDeadline = null;

            if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
                intSemester = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()).getSemester();
            }

            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
            }

            // Reject when deadline is given but task name not given
            if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                    && !arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                throw new ParseException(MESSAGE_MISSING_OLD_TASK);
            }

            // Reject when new task is given but task name not given
            if (arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)
                    && !arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                throw new ParseException(MESSAGE_MISSING_OLD_TASK);
            }

            if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                oldTask = argMultimap.getValue(PREFIX_TASK).get().trim().toLowerCase();
                if (!arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                        && !arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)) {
                    throw new ParseException(MESSAGE_MISSING_NEW_TASK_OR_DEADLINE);
                }
                if (arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)) {
                    newTask = argMultimap.getValue(PREFIX_NEW_TASK).get().trim();
                }
                if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                    String[] datetime = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
                    newDeadline = datetime[0] + " " + datetime[1];
                }
            }
            return new EditCommand(moduleCode, intSemester, grade, oldTask, newTask, newDeadline);
        } else { // EDIT PROFILE

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME) && !arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)
                    && !arePrefixesPresent(argMultimap, PREFIX_YEAR)
                    && !arePrefixesPresent(argMultimap, PREFIX_FOCUS_AREA)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }

            // Make sure there is only one of each field
            if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
                throw new ParseException("Error: you can only specify one name!");
            }
            if (argMultimap.getAllValues(PREFIX_COURSE_NAME).size() > 1) {
                throw new ParseException("Error: you can only specify one course!");
            }
            if (argMultimap.getAllValues(PREFIX_YEAR).size() > 1) {
                throw new ParseException("Error: you can only specify one semester!");
            }
            if (argMultimap.getAllValues(PREFIX_FOCUS_AREA).size() > 1) {
                throw new ParseException("Error: you can only specify one focus area!");
            }

            Name name = null;
            CourseName courseName = null;
            int currentSemester = 0;
            String focusArea = null;

            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
                courseName = ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_COURSE_NAME).get().toUpperCase());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
                currentSemester = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get())
                        .getSemester();
            }
            if (arePrefixesPresent(argMultimap, PREFIX_FOCUS_AREA)) {
                focusArea = argMultimap.getValue(PREFIX_FOCUS_AREA).get();
            }
            return new EditCommand(name, courseName, currentSemester, focusArea);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
