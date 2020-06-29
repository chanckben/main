package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_MODULES_DELETE_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;

//@@author chanckben

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE, PREFIX_TASK, PREFIX_GRADE);

        Name name;
        //ModuleCode moduleCode;
        Deadline task;

        // Delete profile
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            String strName = argMultimap.getValue(PREFIX_NAME).get();
            name = ParserUtil.parseName(strName);
            return new DeleteCommand(name);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
            Collection<String> strModuleCodes = argMultimap.getAllValues(PREFIX_MODULE)
                    .stream()
                    .map(x->x.trim().toUpperCase())
                    .collect(Collectors.toList());
            List<ModuleCode> moduleCodes = ParserUtil.parseModuleCodes(strModuleCodes);

            // Delete task
            if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                // Deleting task will only occur for one module
                if (moduleCodes.size() > 1) {
                    throw new ParseException(MESSAGE_MULTIPLE_MODULES_DELETE_TASK);
                }
                String strModuleCode = strModuleCodes.iterator().next();

                List<String> strTaskDescriptions = argMultimap.getAllValues(PREFIX_TASK)
                        .stream()
                        .map(x->x.trim())
                        .collect(Collectors.toList());

                ArrayList<Deadline> deadlines = new ArrayList<>();
                for (String taskDescription : strTaskDescriptions) {
                    deadlines.add(new Deadline(strModuleCode, taskDescription));
                }
                return new DeleteCommand(moduleCodes, deadlines);
            }

            // Delete grade
            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                String grade = argMultimap.getValue(PREFIX_GRADE).get();
                return new DeleteCommand(moduleCodes, grade);
            }

            // Delete module
            return new DeleteCommand(moduleCodes);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
