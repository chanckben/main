package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.personal.Deadline;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ProfileManager.class);

    private final ProfileManager profileManager;
    private final ModuleManager moduleManager;
    private final CourseManager courseManager;
    private final UserPrefs userPrefs;

    public ModelManager(ProfileList profileList, ModuleList moduleList, CourseList courseList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(profileList, moduleList, courseList, userPrefs);

        logger.fine("Initializing with MODdy " + profileList + " and user prefs " + userPrefs);

        profileManager = new ProfileManager(profileList, userPrefs);
        moduleManager = new ModuleManager(moduleList);
        courseManager = new CourseManager(courseList);
        this.userPrefs = new UserPrefs(userPrefs);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // ================ ProfileManager methods ==============================

    @Override
    public Path getProfileListFilePath() {
        return profileManager.getProfileListFilePath();
    }

    @Override
    public void setProfileListFilePath(Path profileListFilePath) {
        profileManager.setProfileListFilePath(profileListFilePath);
    }

    @Override
    public void setProfileList(ProfileList profileList) {
        profileManager.setProfileList(profileList);
    }

    @Override
    public ProfileList getProfileList() {
        return profileManager.getProfileList();
    }

    @Override
    public void deleteProfile(Profile target) {
        profileManager.deleteProfile(target);
    }

    @Override
    public void addProfile(Profile profile) {
        profileManager.addProfile(profile);
    }

    @Override
    public void setProfile(Profile target, Profile editedProfile) {
        profileManager.setProfile(target, editedProfile);
    }

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        return profileManager.getFilteredPersonList();
    }

    @Override
    public ObservableList<Deadline> getSortedDeadlineList() {
        return profileManager.getSortedDeadlineList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Profile> predicate) {
        profileManager.updateFilteredPersonList(predicate);
    }

    @Override
    public boolean hasProfile(Name name) {
        return profileManager.hasProfile(name);
    }

    @Override
    public Profile getProfile(Name name) {
        return profileManager.getProfile(name);
    }

    @Override
    public Profile getFirstProfile() {
        return profileManager.getFirstProfile();
    }

    @Override
    public void addDeadline(Deadline deadline) {
        profileManager.addDeadline(deadline);
    }

    @Override
    public void deleteDeadline(Deadline deadline) {
        profileManager.deleteDeadline(deadline);
    }

    @Override
    public void clearDeadlineList() {
        profileManager.clearDeadlineList();
    }

    @Override
    public void loadDeadlines() {
        profileManager.loadDeadlines();
    }

    @Override
    public void deleteModuleDeadlines(ModuleCode mc) {
        profileManager.deleteModuleDeadlines(mc);
    }

    @Override
    public Optional<Object> getDisplayedView() {
        return profileManager.getDisplayedView();
    }

    @Override
    public void setDisplayedView(ObservableList<Module> toDisplay) {
        profileManager.setDisplayedView(toDisplay);
    }

    @Override
    public void setDisplayedView(Profile toDisplay) {
        profileManager.setDisplayedView(toDisplay);
    }

    @Override
    public void setDisplayedView(Module toDisplay) {
        profileManager.setDisplayedView(toDisplay);
    }

    @Override
    public void setDisplayedView(Course toDisplay) {
        profileManager.setDisplayedView(toDisplay);
    }

    @Override
    public void setDisplayedView(CourseFocusArea toDisplay) {
        profileManager.setDisplayedView(toDisplay);
    }

    @Override
    public void setNewDeadlineList(Profile editedProfile) {
        profileManager.setNewDeadlineList(editedProfile);
    }

    @Override
    public void deleteModuleFromDeadlineList(ModuleCode moduleCode) {
        profileManager.deleteModuleFromDeadlineList(moduleCode);
    }

    // ================ ModuleManager methods ==============================

    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        return moduleManager.hasModule(moduleCode);
    }

    @Override
    public boolean hasModules(List<ModuleCode> moduleCodes) {
        return moduleManager.hasModules(moduleCodes);
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
        return moduleManager.getModule(moduleCode);
    }

    @Override
    public List<Module> getModules(List<ModuleCode> moduleCodes) {
        return moduleManager.getModules(moduleCodes);
    }

    @Override
    public ModuleList getModuleList() {
        return moduleManager.getModuleList();
    }

    // ================ CourseManager methods ==============================

    @Override
    public Course getCourse(CourseName courseName) throws ParseException {
        return courseManager.getCourse(courseName);
    }

    @Override
    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        return courseManager.getCourseFocusArea(focusAreaName);
    }
}
