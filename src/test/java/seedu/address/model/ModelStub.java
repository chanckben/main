package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getProfileListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProfileListFilePath(Path profileListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProfileList(ProfileList profileList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ProfileList getProfileList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteProfile(Profile target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addProfile(Profile profile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProfile(Profile target, Profile editedProfile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Deadline> getSortedDeadlineList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Profile> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasProfile(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Profile getProfile(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasOneProfile() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Profile getFirstProfile() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDeadline(Deadline deadline) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDeadline(Deadline deadline) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void replaceDeadline(Deadline oldDeadline, Deadline newDeadline) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearDeadlineList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void loadDeadlines() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModuleDeadlines(ModuleCode mc) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Object> getDisplayedView() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedView(ObservableList<Module> toDisplay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedView(Profile toDisplay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedView(Module toDisplay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedView(Course toDisplay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedView(CourseFocusArea toDisplay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setNewDeadlineList(Profile editedProfile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModuleFromDeadlineList(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ProfileManager getProfileManager() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModules(List<ModuleCode> moduleCodes) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Module> getModules(List<ModuleCode> moduleCodes) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ModuleList getModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Course getCourse(CourseName courseName) throws ParseException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        throw new AssertionError("This method should not be called.");
    }
}
