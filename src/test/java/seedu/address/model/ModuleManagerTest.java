package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.profile.Profile;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Preclusions;
import seedu.address.model.module.PrereqTreeNode;
import seedu.address.model.module.Prereqs;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;
import seedu.address.model.module.personal.Deadline;

//@@author jadetayy

public class ModuleManagerTest {

    private ModuleListStub moduleListStub = new ModuleListStub();
    private ModuleManager moduleManager = new ModuleManager(moduleListStub);

    @Test
    public void constructor() {
        assertEquals(moduleListStub, moduleManager.getModuleList());
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleManager.hasModule(null));
    }

    @Test
    public void hasModule_moduleInModuleList_returnsTrue() {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        Module module = new Module(new ModuleCode("CS2103T"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module);
        assertTrue(moduleManager.hasModule(moduleCode));
    }

    @Test
    public void hasModule_modulesNotInModuleList_returnsFalse() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        Module module = new Module(new ModuleCode("CS2103T"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module);
        assertFalse(moduleManager.hasModule(moduleCode));
    }

    @Test
    public void hasModules_moduleCodesInModuleList_returnsTrue() {
        ModuleCode moduleCode1 = new ModuleCode("CS1231");
        ModuleCode moduleCode2 = new ModuleCode("CS1010");
        List<ModuleCode> moduleCodes = Arrays.asList(moduleCode1, moduleCode2);

        Module module1 = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module1);
        Module module2 = new Module(new ModuleCode("CS1010"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module2);

        assertTrue(moduleManager.hasModules(moduleCodes));
    }

    @Test
    public void hasModules_moduleCodesNotInModuleList_returnsFalse() {
        ModuleCode moduleCode1 = new ModuleCode("CS2030");
        ModuleCode moduleCode2 = new ModuleCode("CS2040");
        List<ModuleCode> moduleCodes = Arrays.asList(moduleCode1, moduleCode2);

        Module module1 = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module1);
        Module module2 = new Module(new ModuleCode("CS1010"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module2);
        assertFalse(moduleManager.hasModules(moduleCodes));
    }

    @Test
    public void getModule_moduleInModuleList_returnsTrue() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        Module module = new Module(new ModuleCode("CS1231"), new Title(""), new Prereqs(""), new Preclusions(""),
                new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
                new PrereqTreeNode());
        moduleListStub.addModule(module);
        assertTrue(moduleManager.getModule(moduleCode).equals(module));
    }

    @Test
    public void getModule_moduleNotInModuleList_equalsNull() {
        ModuleCode moduleCode = new ModuleCode("CS1231");
        assertEquals(moduleManager.getModule(moduleCode), null);
    }


    private class ProfileManagerStub extends ProfileManager {
        protected ObservableList<Profile> profileList = FXCollections.observableArrayList();
        protected FilteredList<Profile> filteredProfiles;
        protected ObservableList<Deadline> deadlineList;

        private ProfileManagerStub(List<Profile> profileList) {
            requireNonNull(profileList);
            this.profileList.setAll(profileList);
            filteredProfiles = new FilteredList<>(this.profileList);
        }

        public ProfileManagerStub() {
            this(new ArrayList<>());
        }

        @Override
        public void addProfile(Profile profile) {
            this.profileList.add(profile);
        }

        @Override
        public Profile getFirstProfile() {
            return this.profileList.get(0);
        }

        @Override
        public ObservableList<Profile> getFilteredPersonList() {
            return filteredProfiles;
        }

        public void setPerson(Profile target, Profile editedProfile) {
            requireAllNonNull(target, editedProfile);
            int index = this.profileList.indexOf(target);
            this.profileList.set(index, editedProfile);
        }
    }

    private class ModuleListStub extends ModuleList {

        private ObservableList<Module> moduleList = FXCollections.observableArrayList();
        private ObservableList<ModuleCode> moduleCodes = FXCollections.observableArrayList();

        public ModuleListStub() {
        }

        public void addModule(Module module) {
            moduleList.add(module);
            moduleCodes.add(module.getModuleCode());
        }


        public boolean hasModule(Module module) {
            requireNonNull(module);
            return moduleList.contains(module);
        }

        public boolean hasModuleWithModuleCode(ModuleCode moduleCode) {
            requireNonNull(moduleCode);
            return moduleCodes.contains(moduleCode);
        }

        public Module getModuleWithModuleCode(ModuleCode moduleCode) {
            requireNonNull(moduleCode);

            for (Module mod: moduleList) {
                if (mod.getModuleCode().equals(moduleCode)) {
                    return mod;
                }
            }
            // Code should not reach this line
            // assert false;
            return null;
        }

    }
}
