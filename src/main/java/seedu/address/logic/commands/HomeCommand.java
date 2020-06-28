package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.profile.Profile;

//@@author jadetayy
/**
 * Lists all persons in the address book to the user.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE = "";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.getDisplayedView().isEmpty()) {
            return new CommandResult(MESSAGE, false, false, true);
        }

        model.setDisplayedView((Profile) null);
        return new CommandResult(MESSAGE, false, false, true);
    }
}
