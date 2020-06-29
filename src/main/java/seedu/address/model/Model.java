package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Profile> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    // ================ UserPrefs methods ==============================

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ================ ProfileManager methods ==============================

    /**
     * Returns the user prefs' address book file path.
     */
    Path getProfileListFilePath();
    //Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setProfileListFilePath(Path profileListFilePath);
    //void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setProfileList(ProfileList profileList);
    //void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ProfileList getProfileList();
    //ReadOnlyAddressBook getAddressBook();

    /**
     * Deletes the given profile.
     * The profile must exist in the address book.
     */
    void deleteProfile(Profile target);

    /**
     * Adds the given profile.
     * {@code profile} must not already exist in the address book.
     */
    void addProfile(Profile profile);

    /**
     * Replaces the given profile {@code target} with {@code editedProfile}.
     * {@code target} must exist in the address book.
     * The profile identity of {@code editedProfile} must not be the same as another existing profile
     * in the address book.
     */
    void setProfile(Profile target, Profile editedProfile);

    /** Returns an unmodifiable view of the filtered profile list */
    ObservableList<Profile> getFilteredPersonList();

    ObservableList<Deadline> getSortedDeadlineList();

    /**
     * Updates the filter of the filtered profile list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Profile> predicate);

    boolean hasProfile(Name name);

    Profile getProfile(Name name);

    boolean hasOneProfile();

    Profile getFirstProfile();

    void addDeadline(Deadline deadline);

    void deleteDeadline(Deadline deadline);

    void replaceDeadline(Deadline oldDeadline, Deadline newDeadline);

    void clearDeadlineList();

    void loadDeadlines();

    void deleteModuleDeadlines(ModuleCode mc);

    Optional<Object> getDisplayedView();

    void setDisplayedView(ObservableList<Module> toDisplay);

    void setDisplayedView(Profile toDisplay);

    void setDisplayedView(Module toDisplay);

    void setDisplayedView(Course toDisplay);

    void setDisplayedView(CourseFocusArea toDisplay);

    void setNewDeadlineList(Profile editedProfile);

    void deleteModuleFromDeadlineList(ModuleCode moduleCode);

    ProfileManager getProfileManager();

    // ================ ModuleManager methods ==============================

    boolean hasModule(ModuleCode moduleCode);

    boolean hasModules(List<ModuleCode> moduleCodes);

    Module getModule(ModuleCode moduleCode);

    List<Module> getModules(List<ModuleCode> moduleCodes);

    ModuleList getModuleList();

    // ================ CourseManager methods ==============================

    Course getCourse(CourseName courseName) throws ParseException;

    CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException;
}
