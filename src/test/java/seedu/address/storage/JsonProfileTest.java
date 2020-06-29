package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
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
import seedu.address.model.module.personal.Grade;
import seedu.address.model.module.personal.ModuleGrade;
import seedu.address.model.module.personal.ModuleStatus;
import seedu.address.model.module.personal.Personal;
import seedu.address.model.module.personal.Status;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

//@@author chanckben
public class JsonProfileTest {

    // JsonProfile variable declarations
    public static final String INVALID_NAME = "John&";
    public static final String INVALID_CURRENT_SEMESTER = "a";

    public static final String VALID_NAME = "John";
    public static final String VALID_COURSENAME = "Computer Science";
    public static final String VALID_SPECIALISATION = "Computer Security";
    public static final String VALID_CURRENT_SEMESTER = "4";
    public static final List<JsonSemesterRecord> VALID_RECORDS = new ArrayList<>();
    public static final Profile VALID_PROFILE = new Profile(new Name(VALID_NAME), new CourseName(VALID_COURSENAME),
            Integer.parseInt(VALID_CURRENT_SEMESTER), new FocusArea(VALID_SPECIALISATION));

    // JsonSemesterRecord variable declarations
    public static final String INVALID_SEMESTER = "A";

    public static final List<JsonPersonalModule> VALID_MODULES = new ArrayList<>();

    // JsonPersonalModule variable declarations
    private static final String INVALID_STATUS = "status";
    private static final String INVALID_GRADE = "abc";

    private static final String VALID_MODULE_CODE = "CS2030";
    private static final String VALID_TITLE = "Programming Methodology II";
    private static final String VALID_DESC = "Some description";
    private static final String VALID_MODULAR_CREDITS = "4";
    private static final String VALID_PREREQUISITE = "Some prerequisite";
    private static final String VALID_PRECLUSION = "Some preclusion";
    private static final List<JsonSemesterData> VALID_SEMESTER_DATA = new ArrayList<>();
    private static final JsonPrereqTreeNode VALID_PREREQ_TREE = new JsonPrereqTreeNode("CS1010");
    private static final String VALID_STATUS = ModuleStatus.COMPLETED.name();
    private static final String VALID_GRADE = ModuleGrade.A_MINUS.toString();
    private static final List<JsonDeadline> VALID_DEADLINES = new ArrayList<>();
    private static final Module VALID_MODULE = new Module(new ModuleCode(VALID_MODULE_CODE), new Title(VALID_TITLE),
            new Prereqs(VALID_PREREQUISITE), new Preclusions(VALID_PRECLUSION),
            new ModularCredits(VALID_MODULAR_CREDITS), new Description(VALID_DESC),
            new SemesterData(VALID_SEMESTER_DATA.stream()
                    .map(JsonSemesterData::toString)
                    .collect(Collectors.toList())),
            new PrereqTreeNode(new ModuleCode(VALID_MODULE_CODE)));

    // JsonDeadline variable declarations
    private static final String INVALID_DATE = "2020-01-32";
    private static final String INVALID_TIME = "25:00";

    private static final String VALID_DESCRIPTION = "ABC";
    private static final String VALID_DATE = "2020-01-31";
    private static final String VALID_TIME = "23:59";
    private static final Deadline VALID_DEADLINE;

    static {
        Deadline tempDeadline;
        LocalDate date = LocalDate.parse(VALID_DATE);
        LocalTime time = LocalTime.parse(VALID_TIME, DateTimeFormatter.ofPattern("HH:mm"));
        tempDeadline = new Deadline(VALID_MODULE_CODE, VALID_DESCRIPTION, date, time);

        VALID_DEADLINE = tempDeadline;
    }

    // JsonProfile tests

    @Test
    public void toModelType_validProfile_returnsProfile() throws Exception {
        JsonProfile profile = new JsonProfile(VALID_PROFILE);
        assertEquals(VALID_PROFILE, profile.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(INVALID_NAME, VALID_COURSENAME, VALID_SPECIALISATION,
                VALID_CURRENT_SEMESTER, VALID_RECORDS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(null, VALID_COURSENAME, VALID_SPECIALISATION,
                VALID_CURRENT_SEMESTER, VALID_RECORDS);
        String expectedMessage = String.format(JsonProfile.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    @Test
    public void toModelType_nullCourseName_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(VALID_NAME, null, VALID_SPECIALISATION,
                VALID_CURRENT_SEMESTER, VALID_RECORDS);
        String expectedMessage = String.format(JsonProfile.MISSING_FIELD_MESSAGE_FORMAT,
                CourseName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    @Test
    public void toModelType_invalidCurrentSemester_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(VALID_NAME, VALID_COURSENAME, VALID_SPECIALISATION,
                INVALID_CURRENT_SEMESTER, VALID_RECORDS);
        String expectedMessage = "Semester number should be a positive integer";
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    @Test
    public void toModelType_nullCurrentSemester_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(VALID_NAME, VALID_COURSENAME, VALID_SPECIALISATION,
                null, VALID_RECORDS);
        String expectedMessage = String.format(JsonProfile.MISSING_FIELD_MESSAGE_FORMAT, "current semester");
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    @Test
    public void toModelType_nullRecord_throwsIllegalValueException() {
        JsonProfile profile = new JsonProfile(VALID_NAME, VALID_COURSENAME, VALID_SPECIALISATION,
                VALID_CURRENT_SEMESTER, null);
        String expectedMessage = String.format(JsonProfile.MISSING_FIELD_MESSAGE_FORMAT, "records");
        assertThrows(IllegalValueException.class, expectedMessage, profile::toModelType);
    }

    // JsonSemesterRecord tests

    @Test
    public void toModelType_invalidSemester_throwsIllegalValueException() {
        JsonSemesterRecord profile = new JsonSemesterRecord(INVALID_SEMESTER, VALID_MODULES);
        String expectedMessage = "Semester number should be a positive integer";
        assertThrows(IllegalValueException.class, expectedMessage, profile::getSemester);
    }

    // JsonPersonalModule tests

    @Test
    public void toModelType_validModule_returnsModule() throws Exception {
        Personal personal = new Personal();
        personal.setStatus(VALID_STATUS);
        personal.setGrade(VALID_GRADE);
        VALID_MODULE.setPersonal(personal);
        JsonPersonalModule module = new JsonPersonalModule(VALID_MODULE);
        assertTrue(VALID_MODULE.isSameModule(module.toModelType()));
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonPersonalModule module = new JsonPersonalModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC,
                VALID_MODULAR_CREDITS, VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE,
                INVALID_STATUS, VALID_GRADE, VALID_DEADLINES);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonPersonalModule module = new JsonPersonalModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC,
                VALID_MODULAR_CREDITS, VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE,
                null, VALID_GRADE, VALID_DEADLINES);
        String expectedMessage = String.format(JsonModule.MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        JsonPersonalModule module = new JsonPersonalModule(VALID_MODULE_CODE, VALID_TITLE, VALID_DESC,
                VALID_MODULAR_CREDITS, VALID_PREREQUISITE, VALID_PRECLUSION, VALID_SEMESTER_DATA, VALID_PREREQ_TREE,
                VALID_STATUS, INVALID_GRADE, VALID_DEADLINES);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    // JsonDeadline tests

    @Test
    public void toModelType_validDeadline_returnsDeadline() throws Exception {
        JsonDeadline deadline = new JsonDeadline(VALID_DEADLINE);
        assertEquals(VALID_DEADLINE, deadline.toModelType());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonDeadline deadline = new JsonDeadline(VALID_MODULE_CODE, null, VALID_DATE, VALID_TIME);
        String expectedMessage = String.format(JsonDeadline.MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonDeadline deadline = new JsonDeadline(VALID_MODULE_CODE, VALID_DESCRIPTION, INVALID_DATE, VALID_TIME);
        String expectedMessage = "Deadline's date field should be a valid date in the format YYYY-MM-DD "
                + "and time field should be a valid time in the format HH:mm";
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonDeadline deadline = new JsonDeadline(VALID_MODULE_CODE, VALID_DESCRIPTION, VALID_DATE, INVALID_TIME);
        String expectedMessage = "Deadline's date field should be a valid date in the format YYYY-MM-DD "
                + "and time field should be a valid time in the format HH:mm";
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }
}
