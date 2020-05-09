package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.*;

//@@author chanckben

/**
 * API of the Storage component
 */
public interface Storage extends ProfileListStorage, ModuleListStorage, CourseListStorage, UserPrefsStorage {
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getProfileListFilePath();

    @Override
    Optional<ProfileList> readProfileList() throws DataConversionException, IOException;

    @Override
    void saveProfileList(ProfileList profileList) throws IOException;

    @Override
    String getModuleListFilePath();

    @Override
    Optional<ModuleList> readModuleList() throws DataConversionException, IOException;

    @Override
    String getCourseListFilePath();

    @Override
    Optional<CourseList> readCourseList() throws DataConversionException, IOException;
}
