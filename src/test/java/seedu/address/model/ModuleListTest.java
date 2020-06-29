//package seedu.address.model;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.module.Description;
//import seedu.address.model.module.ModularCredits;
//import seedu.address.model.module.Module;
//import seedu.address.model.module.ModuleCode;
//import seedu.address.model.module.Preclusions;
//import seedu.address.model.module.PrereqTreeNode;
//import seedu.address.model.module.Prereqs;
//import seedu.address.model.module.SemesterData;
//import seedu.address.model.module.Title;
//
////@@author jadetayy
//
//public class ModuleListTest extends ModuleList {
//
//    private ModuleList moduleList = new ModuleList();
//
//    private Module module = new Module(new ModuleCode("CS2103T"), new Title(""), new Prereqs(""), new Preclusions(""),
//            new ModularCredits("4"), new Description(""), new SemesterData(new ArrayList<>()),
//            new PrereqTreeNode());
//    private ModuleCode moduleCode = new ModuleCode("CS2103T");
//
//
//    @Test
//    public void addModule_validModule_returnsTrue() {
//        moduleList.addModule(module);
//        assertTrue(moduleList.getModuleWithModuleCode(moduleCode).equals(module));
//    }
//
//    @Test
//    public void addModule_nullModule_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> moduleList.addModule(null));
//    }
//
//    @Test
//    public void hasModule_moduleInModuleList_returnsTrue() {
//        moduleList.addModule(module);
//        assertTrue(moduleList.hasModule(module));
//    }
//
//    @Test
//    public void hasModule_moduleNotInModuleList_returnsFalse() {
//        assertFalse(moduleList.hasModule(module));
//    }
//
//    @Test
//    public void hasModule_nullModule_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> moduleList.hasModule(null));
//    }
//
//    @Test
//    public void hasModule_moduleDeletedFromModuleList_returnsFalse() throws ParseException {
//        try {
//            moduleList.addModule(module);
//            moduleList.removeModuleWithModuleCode(moduleCode);
//            assertFalse(moduleList.hasModule(module));
//        } catch (ParseException e) {
//            throw e;
//        }
//    }
//
//    @Test
//    public void hasModuleWithModuleCode_moduleCodeInModuleCodeList_returnsTrue() {
//        moduleList.addModule(module);
//        assertTrue(moduleList.hasModuleWithModuleCode(moduleCode));
//    }
//
//    @Test
//    public void hasModuleWithModuleCode_moduleCodeNotInModuleCodeList_returnsFalse() {
//        ModuleCode moduleCode = new ModuleCode("CS1231");
//        moduleList.addModule(module);
//        assertFalse(moduleList.hasModuleWithModuleCode(moduleCode));
//    }
//
//    @Test
//    public void hasModuleWithModuleCode_nullModuleCode_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> moduleList.hasModuleWithModuleCode(null));
//    }
//
//    @Test
//    public void removeModuleWithModuleCode_moduleCodeInModuleList_returnsTrue() throws ParseException {
//        try {
//            moduleList.addModule(module);
//            moduleList.removeModuleWithModuleCode(moduleCode);
//            assertFalse(moduleList.hasModuleWithModuleCode(moduleCode)); //false because module not in list anymore
//        } catch (ParseException e) {
//            throw e;
//        }
//    }
//
//    @Test
//    public void removeModuleWithModuleCode_moduleCodeNotInModuleList_throwsParseException() throws ParseException {
//        assertThrows(ParseException.class, () -> moduleList.removeModuleWithModuleCode(moduleCode));
//    }
//
//    @Test
//    public void removeModuleWithModuleCode_nullModuleCode_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> moduleList.removeModuleWithModuleCode(null));
//    }
//
//}
