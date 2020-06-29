package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ModuleList;
import seedu.address.model.module.Module;

//@@author chanckben
/**
 * An Immutable ModuleList that is serializable to JSON format.
 */
public class JsonSerializableModuleList {
    public static final String MESSAGE_DUPLICATE_MODULE = "Persons list contains duplicate module(s).";

    private final List<JsonModule> modules = new ArrayList<>();

    @JsonCreator
    public JsonSerializableModuleList(@JsonProperty("modules") List<JsonModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts this module list into a {@code ModuleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModuleList toModelType() throws IllegalValueException {
        ModuleList moduleList = new ModuleList();
        for (JsonModule jsonModule : modules) {
            Module module = jsonModule.toModelType();
            if (moduleList.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            moduleList.addModule(module);
        }
        return moduleList;
    }
}
