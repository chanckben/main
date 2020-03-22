package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CourseList;

/**
 * A class to access CourseList data stored as a json file on the hard disk.
 */
public class JsonCourseListStorage {

    private Path filePath;

    public JsonCourseListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Optional<CourseList> readCourseList() throws DataConversionException {
        return readCourseList(filePath);
    }

    /**
     * Similar to {@link #readCourseList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<CourseList> readCourseList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCourseList> jsonCourseList = JsonUtil.readJsonFile(
                filePath, JsonSerializableCourseList.class);
        if (!jsonCourseList.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonCourseList.get().toModelType());
        } catch (IllegalValueException ive) {
            //logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

}