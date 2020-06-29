package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.DateTimeException;
import seedu.address.model.module.personal.Deadline;

//@@author joycelynteo

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException, DateTimeException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_YEAR, PREFIX_GRADE,
                        PREFIX_TASK, PREFIX_DEADLINE);

        // To check if Module argument exists since it is compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Check there is only 1 year tag
        if (arePrefixesPresent(argMultimap, PREFIX_YEAR) && argMultimap.getAllValues(PREFIX_YEAR).size() > 1) {
            throw new ParseException("You can only choose to add a module/task to one semester!");
        }

        // Create module object
        Collection<String> strModuleCodes = argMultimap.getAllValues(PREFIX_MODULE)
                .stream()
                .map(x->x.trim().toUpperCase())
                .collect(Collectors.toList());
        List<ModuleCode> moduleCodes = ParserUtil.parseModuleCodes(strModuleCodes);

        if (moduleCodes.size() > 1 && arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            throw new ParseException("You can only add a task to one module at once!");
        }

        String grade = null;
        ArrayList<Deadline> deadlineList = new ArrayList<>();

        int intSemester = 0;

        if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
            intSemester = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()).getSemester();
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
            grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get().toUpperCase());
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_TASK) && arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
            throw new ParseException("Please provide a task name with the tag t/");
        }
        if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
            if (arePrefixesPresent(argMultimap, PREFIX_YEAR)) {
                throw new ParseException("Please omit the year tag when adding a task!");
            }
            Object[] oneModuleCodeList = moduleCodes.toArray();
            String moduleCode = oneModuleCodeList[0].toString();

            List<String> strTaskDescriptions = argMultimap.getAllValues(PREFIX_TASK)
                    .stream()
                    .map(x->x.trim())
                    .collect(Collectors.toList());

            List<String> strDeadlines = null;
            if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                strDeadlines = argMultimap.getAllValues(PREFIX_DEADLINE)
                        .stream()
                        .map(x->x.trim())
                        .collect(Collectors.toList());

                if (strDeadlines.size() != strTaskDescriptions.size()) {
                    throw new ParseException("Please provide a deadline for each task!");
                }
            }

            if (strDeadlines != null) {
                // Every task has a deadline
                for (int i = 0; i < strDeadlines.size(); i++) {
                    String taskDescription = strTaskDescriptions.get(i);
                    String dateTimeString = strDeadlines.get(i);

                    if (!dateTimeString.equals("")) {
                        // Task provided without date AND time
                        String[] dateTime = ParserUtil.parseDeadline(dateTimeString);
                        String dateString = dateTime[0];
                        String timeString = dateTime[1];

                        LocalDate date = LocalDate.parse(dateString);
                        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
                        deadlineList.add(new Deadline(moduleCode, taskDescription, date, time));
                    } else {
                        deadlineList.add(new Deadline(moduleCode, taskDescription));
                    }
                }
            } else {
                // Every task does not have a deadline
                for (String taskDescription : strTaskDescriptions) {
                    deadlineList.add(new Deadline(moduleCode, taskDescription));
                }
            }
        }

        return new AddCommand(moduleCodes, intSemester, grade, deadlineList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
