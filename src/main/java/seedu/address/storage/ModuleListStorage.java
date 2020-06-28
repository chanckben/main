package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModuleList;

/**
 * Represents a storage for {@link seedu.address.model.ModuleList}.
 */
public interface ModuleListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getModuleListFilePath();

    /**
     * Returns ModuleList data as a {@link ModuleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ModuleList> readModuleList() throws DataConversionException;

    /**
     * @see #getModuleListFilePath()
     */
    Optional<ModuleList> readModuleList(String filePath) throws DataConversionException;

}
