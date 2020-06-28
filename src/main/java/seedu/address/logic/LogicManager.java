package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ModdyParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ProfileList;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Storage storage;
    private final ModdyParser moddyParser;

    private final Model model;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        moddyParser = new ModdyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, DateTimeException {
        //logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //parse user input from String to a Command
        Command command = moddyParser.parseCommand(commandText);
        //executes the Command and stores the result
        commandResult = command.execute(model);

        try {
            //can assume that previous line of code modifies model in some way
            //since it is being stored here
            //storage.saveAddressBook(model.getAddressBook());
            storage.saveProfileList(model.getProfileList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ProfileList getProfileList() {
        return model.getProfileList();
    }

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getProfileListFilePath() {
        return model.getProfileListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Deadline> getFilteredDeadlineList() {
        if (model.getFilteredPersonList().size() == 1) { //profile exists
            if (model.getFirstProfile().getCurModules() != null) {
                model.loadDeadlines();
            }
        }
        return model.getSortedDeadlineList();
    }

    @Override
    public Optional<Object> getDisplayedView() {
        return model.getDisplayedView();
    }

    @Override
    public ProfileManager getProfileManager() {
        return model.getProfileManager();
    }

}
