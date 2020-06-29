package seedu.address.logic;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ProfileList;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.module.exceptions.DateTimeException;
import seedu.address.model.module.personal.Deadline;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, DateTimeException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ProfileList getProfileList();
    //ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Profile> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Deadline> getFilteredDeadlineList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getProfileListFilePath();
    //Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    Optional<Object> getDisplayedView();

    ProfileManager getProfileManager();

}
