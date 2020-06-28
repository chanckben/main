package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.DuplicatePersonException;

//@@author joycelynteo
/**
 * Creates a new profile.
 */
public class NewCommand extends Command {

    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new profile. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COURSE_NAME + "COURSE "
            + PREFIX_YEAR + "YEAR.SEMESTER" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_COURSE_NAME + "Computer Science "
            + PREFIX_YEAR + "2.2";

    public static final String MESSAGE_SUCCESS = "New profile created: %1$s";
    public static final String MESSAGE_DUPLICATE_PROFILE = "Error: Profile with name %1$s exists. "
            + "You can only have one profile.";

    private final Profile toAdd;

    public NewCommand(Profile profile) {
        requireNonNull(profile);
        toAdd = profile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.getProfileList().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PROFILE,
                    model.getFirstProfile().getName()));
        }

        try {
            model.addProfile(toAdd);
        } catch (DuplicatePersonException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PROFILE, toAdd.getName()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewCommand // instanceof handles nulls
                && toAdd.equals(((NewCommand) other).toAdd));
    }


}
