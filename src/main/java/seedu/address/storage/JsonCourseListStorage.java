package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CourseList;

//@@author gyant6
/**
 * A class to access CourseList data stored as a json file on the hard disk.
 */
public class JsonCourseListStorage implements CourseListStorage {

    private String filePath;

    public JsonCourseListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCourseListFilePath() {
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
    public Optional<CourseList> readCourseList(String filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCourseList> jsonCourseList = JsonUtil.readJsonFileStream(
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
