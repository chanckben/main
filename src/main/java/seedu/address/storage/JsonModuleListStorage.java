package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModuleList;

//@@author chanckben
/**
 * A class to access ModuleList data stored as a json file on the hard disk.
 */
public class JsonModuleListStorage implements ModuleListStorage {

    private String filePath;

    public JsonModuleListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getModuleListFilePath() {
        return filePath;
    }

    public Optional<ModuleList> readModuleList() throws DataConversionException {
        return readModuleList(filePath);
    }

    /**
     * Similar to {@link #readModuleList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ModuleList> readModuleList(String filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModuleList> jsonModuleList =
                JsonUtil.readJsonFileStream(filePath, JsonSerializableModuleList.class);

        if (!jsonModuleList.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonModuleList.get().toModelType());
        } catch (IllegalValueException ive) {
            //logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

}
