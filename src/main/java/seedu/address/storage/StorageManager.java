package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CourseList;
import seedu.address.model.ModuleList;
import seedu.address.model.ProfileList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

//@@author chanckben

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProfileListStorage profileListStorage;
    private ModuleListStorage moduleListStorage;
    private CourseListStorage courseListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(ProfileListStorage profileListStorage, ModuleListStorage moduleListStorage,
                          CourseListStorage courseListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.profileListStorage = profileListStorage;
        this.moduleListStorage = moduleListStorage;
        this.courseListStorage = courseListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ ProfileList methods ==============================

    @Override
    public Path getProfileListFilePath() {
        return profileListStorage.getProfileListFilePath();
    }

    @Override
    public Optional<ProfileList> readProfileList() throws DataConversionException, IOException {
        return readProfileList(profileListStorage.getProfileListFilePath());
    }

    @Override
    public Optional<ProfileList> readProfileList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return profileListStorage.readProfileList(filePath);
    }

    @Override
    public void saveProfileList(ProfileList profileList) throws IOException {
        saveProfileList(profileList, profileListStorage.getProfileListFilePath());
    }

    @Override
    public void saveProfileList(ProfileList profileList, Path filePath) throws IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        profileListStorage.saveProfileList(profileList, filePath);
    }

    // ================ ModuleList methods ==============================

    @Override
    public String getModuleListFilePath() {
        return moduleListStorage.getModuleListFilePath();
    }

    @Override
    public Optional<ModuleList> readModuleList() throws DataConversionException {
        return readModuleList(moduleListStorage.getModuleListFilePath());
    }

    @Override
    public Optional<ModuleList> readModuleList(String filePath) throws DataConversionException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduleListStorage.readModuleList(filePath);
    }

    // ================ CourseList methods ==============================

    @Override
    public String getCourseListFilePath() {
        return courseListStorage.getCourseListFilePath();
    }

    @Override
    public Optional<CourseList> readCourseList() throws DataConversionException {
        return readCourseList(courseListStorage.getCourseListFilePath());
    }

    @Override
    public Optional<CourseList> readCourseList(String filePath) throws DataConversionException {
        logger.fine("Attempting to read data from file: " + filePath);
        return courseListStorage.readCourseList(filePath);
    }

}
