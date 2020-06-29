package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Preclusions;
import seedu.address.model.module.PrereqTreeNode;
import seedu.address.model.module.Prereqs;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;

/**
 * Stub for ModelManager containing one module in moduleList
 */
public class ModelManagerStubWithModule extends ModelManagerStub {
    public ModelManagerStubWithModule() {
        Module module = new Module(new ModuleCode("CS1101S"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        this.moduleList.add(module);
    }

    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        for (Module module: moduleList) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
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
}
