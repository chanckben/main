package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.NewCommand.MESSAGE_DUPLICATE_PROFILE;
import static seedu.address.logic.commands.NewCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManagerStub;
import seedu.address.model.ModelStubEmpty;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.Year;

//@@author joycelynteo
public class NewCommandTest {

    // Constructor is null.
    @Test
    public void constructor_nullProfile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NewCommand(null));
    }

    // Profile with same name already exists.
    @Test
    public void execute_duplicateProfile_throwsCommandException() {
        Name name = new Name(VALID_NAME_AMY);
        CourseName courseName = new CourseName(VALID_COURSE_AMY);
        int semester = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusArea = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(name, courseName, semester, focusArea);

        NewCommand newCommandAmy = new NewCommand(amyProfile);

        ModelManagerStub modelManagerStub = new ModelManagerStub();
        modelManagerStub.addProfile(amyProfile);
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_PROFILE, amyProfile.getName()), () ->
                newCommandAmy.execute(modelManagerStub));
    }

    // Another profile already exists.
    @Test
    public void execute_profileExists_throwsCommandException() {
        Name nameAmy = new Name(VALID_NAME_AMY);
        CourseName courseNameAmy = new CourseName(VALID_COURSE_AMY);
        int semesterAmy = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusAreaAmy = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(nameAmy, courseNameAmy, semesterAmy, focusAreaAmy);

        Name nameBob = new Name(VALID_NAME_BOB);
        CourseName courseNameBob = new CourseName(VALID_COURSE_BOB);
        int semesterBob = new Year(VALID_SEMESTER_BOB).getSemester();
        FocusArea focusAreaBob = new FocusArea(VALID_FOCUS_AREA_BOB);
        Profile bobProfile = new Profile(nameBob, courseNameBob, semesterBob, focusAreaBob);

        NewCommand newCommandBob = new NewCommand(bobProfile);

        ModelManagerStub modelManagerStub = new ModelManagerStub();
        modelManagerStub.addProfile(amyProfile);
        assertThrows(CommandException.class, String.format(MESSAGE_DUPLICATE_PROFILE, amyProfile.getName()), () ->
                newCommandBob.execute(modelManagerStub));
    }

    @Test
    public void execute_validFields_success() {
        Name nameAmy = new Name(VALID_NAME_AMY);
        CourseName courseNameAmy = new CourseName(VALID_COURSE_AMY);
        int semesterAmy = new Year(VALID_SEMESTER_AMY).getSemester();
        FocusArea focusAreaAmy = new FocusArea(VALID_FOCUS_AREA_AMY);
        Profile amyProfile = new Profile(nameAmy, courseNameAmy, semesterAmy, focusAreaAmy);

        NewCommand newCommand = new NewCommand(amyProfile);

        try {
            // Unit Test
            assertEquals(newCommand.execute(new ModelStubEmpty()).getFeedbackToUser(),
                    String.format(MESSAGE_SUCCESS, amyProfile));
            // Integration Test
            assertEquals(newCommand.execute(new ModelManagerStub()).getFeedbackToUser(),
                    String.format(MESSAGE_SUCCESS, amyProfile));
        } catch (CommandException e) {
            fail();
        }

    }
}

