package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

//@@author gyant6
/**
 * Creates a new ModuleList object which contains Module objects.
 */
public class ModuleList implements Iterable<Module> {

    private ObservableList<Module> moduleList = FXCollections.observableArrayList();
    private ObservableList<ModuleCode> moduleCodes = FXCollections.observableArrayList();

    public ModuleList() {}

    /**
     * Adds a module to the module list.
     */
    public void addModule(Module module) {
        moduleList.add(module);
        moduleCodes.add(module.getModuleCode());
    }

    /**
     * Returns true if a module with the same fields as {@code module} exists in the module list.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return moduleList.contains(module);
    }

    /**
     * Returns true if a module with the module code {@code moduleCode} exists in the module list.
     */
    public boolean hasModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return moduleCodes.contains(moduleCode);
    }

    /**
     * Returns the module with module code {@code moduleCode} in the module list, if it exists.
     * @throws ParseException No module in the module list contains {@code moduleCode}.
     */
    public Module getModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);

        for (Module mod: moduleList) {
            if (mod.getModuleCode().equals(moduleCode)) {
                return mod;
            }
        }
        // Code should not reach this line
        assert false;
        return null;
    }

    /**
     * Removes the module with module code {@code moduleCode} in the module list, if it exists.
     * @throws ModuleNotFoundException No module in the module list contains {@code moduleCode}.
     */
    public void removeModuleWithModuleCode(ModuleCode moduleCode) throws ModuleNotFoundException {
        requireNonNull(moduleCode);
        if (!hasModuleWithModuleCode(moduleCode)) {
            throw new ModuleNotFoundException(String.format(MESSAGE_INVALID_MODULE, moduleCode));
        }
        Module modToRemove = getModuleWithModuleCode(moduleCode);
        moduleList.remove(modToRemove);
        moduleCodes.remove(moduleCode);
    }

    public ObservableList<Module> getModuleList() {
        return moduleList;
    }

    public List<ModuleCode> getModuleCodes() {
        return new ArrayList<>(moduleCodes);
    }

    public int size() {
        return moduleList.size();
    }

    public Stream<Module> stream() {
        return moduleList.stream();
    }

    @Override
    public Iterator<Module> iterator() {
        return moduleList.iterator();
    }

    @Override
    public String toString() {
        List<String> strModCodes = new ArrayList<>();
        for (ModuleCode moduleCode: moduleCodes) {
            strModCodes.add(moduleCode.toString());
        }
        return String.join("\n", strModCodes);
    }
}
