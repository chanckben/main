package seedu.address.storage;

import static seedu.address.storage.JsonModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;

//@@author chanckben
public class JsonModuleTest {
    private static final String INVALID_MODULE_CODE = "CS111";
    private static final String INVALID_MODULAR_CREDITS = "A";

    private static final String VALID_MODULE_CODE = "CS2030";
    private static final String VALID_TITLE = "Programming Methodology II";
    private static final String VALID_DESC = "Some description";
    private static final String VALID_MODULAR_CREDITS = "4";
    private static final String VALID_PREREQUISITE = "Some prerequisite";
    private static final String VALID_PRECLUSION = "Some preclusion";
    private static final List<JsonSemesterData> VALID_SEMESTER_DATA = new ArrayList<>();
    private static final JsonPrereqTreeNode VALID_PREREQ_TREE = new JsonPrereqTreeNode("CS1010");

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonModule module = new JsonModule(INVALID_MODULE_CODE, VALID_TITLE, VALID_DESC, VALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonModule module = new JsonModule(null, VALID_TITLE, VALID_DESC, VALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonModule module = new JsonModule(VALID_MODULE_CODE, null, VALID_DESC, VALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonModule module = new JsonModule(VALID_MODULE_CODE, VALID_TITLE, null, VALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidModularCredits_throwsIllegalValueException() {
        JsonModule module = new JsonModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC, INVALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = ModularCredits.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModularCredits_throwsIllegalValueException() {
        JsonModule module = new JsonModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC, null,
                VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModularCredits.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullSemesterData_throwsIllegalValueException() {
        JsonModule module = new JsonModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC, VALID_MODULAR_CREDITS,
                VALID_PREREQUISITE, VALID_PRECLUSION, null, VALID_PREREQ_TREE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SemesterData.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

}
