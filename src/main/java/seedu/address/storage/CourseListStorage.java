package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CourseList;

/**
 * Represents a storage for {@link seedu.address.model.CourseList}.
 */
public interface CourseListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getCourseListFilePath();

    /**
     * Returns CourseList data as a {@link CourseList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CourseList> readCourseList() throws DataConversionException;

    /**
     * @see #getCourseListFilePath()
     */
    Optional<CourseList> readCourseList(String filePath) throws DataConversionException;
}
