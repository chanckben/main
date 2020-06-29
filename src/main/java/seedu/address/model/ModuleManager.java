package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

//@@author gyant6
/**
 * Represents the in-memory model of the module list data.
 */
public class ModuleManager {

    private ModuleList moduleList;

    public ModuleManager(ModuleList moduleList) {
        requireNonNull(moduleList);

        this.moduleList = moduleList;
    }

    public ModuleManager() {
        this(new ModuleList());
    }

    public boolean hasModule(ModuleCode moduleCode) {
        return moduleList.hasModuleWithModuleCode(moduleCode);
    }

    public boolean hasModules(List<ModuleCode> moduleCodes) {
        return moduleCodes.stream().allMatch(this::hasModule);
    }

    public Module getModule(ModuleCode moduleCode) {
        return moduleList.getModuleWithModuleCode(moduleCode);
    }

    public List<Module> getModules(List<ModuleCode> moduleCodes) {
        return moduleCodes.stream().map(this::getModule).collect(Collectors.toList());
    }

    public ModuleList getModuleList() {
        return moduleList;
    }
}
