package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.personal.Deadline;

//@@author gyant6

public class ProfileManagerTest {

    private ProfileManager profileManager = new ProfileManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), profileManager.getUserPrefs());
        assertEquals(new GuiSettings(), profileManager.getGuiSettings());
        assertEquals(new ProfileList(), profileManager.getProfileList());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        profileManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, profileManager.getUserPrefs());

        // Modifying userPrefs should not modify profileManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, profileManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        profileManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, profileManager.getGuiSettings());
    }

    @Test
    public void setProfileListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setProfileListFilePath(null));
    }

    @Test
    public void setProfileListFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        profileManager.setProfileListFilePath(path);
        assertEquals(path, profileManager.getProfileListFilePath());
    }

    @Test
    public void hasProfile_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.hasProfile(null));
    }

    @Test
    public void hasProfile_profileNotInProfileList_returnsFalse() {
        assertFalse(profileManager.hasProfile(ALICE.getName()));
    }

    @Test
    public void hasProfile_profileInProfileList_returnsTrue() {
        profileManager.addProfile(ALICE);
        assertTrue(profileManager.hasProfile(ALICE.getName()));
        profileManager.deleteProfile(ALICE);
    }

    @Test
    public void hasProfile_profileDeletedFromProfileList_returnsFalse() {
        profileManager.addProfile(ALICE);
        profileManager.deleteProfile(ALICE);
        assertFalse(profileManager.hasProfile((ALICE.getName())));
    }

    @Test
    public void hasProfile_profileReplacedInProfileList_returnsTrue() {
        profileManager.addProfile(ALICE);
        profileManager.setProfile(ALICE, BENSON);
        assertTrue(profileManager.hasProfile(BENSON.getName()));
        profileManager.deleteProfile(BENSON);
    }


    @Test
    public void hasProfile_profileReplacedInProfileList_returnFalse() {
        profileManager.addProfile(ALICE);
        profileManager.setProfile(ALICE, BENSON);
        assertFalse(profileManager.hasProfile((ALICE.getName())));
        profileManager.deleteProfile(BENSON);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> profileManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasOneProfile_profileInProfileList_returnsTrue() {
        profileManager.addProfile(ALICE);
        assertTrue(profileManager.hasOneProfile());
        profileManager.deleteProfile(ALICE);
    }

    @Test
    public void hasOneProfile_profileNotInProfileList_returnsFalse() {
        assertFalse(profileManager.hasOneProfile());
    }

    @Test
    public void getFirstProfile_profileInProfileList_returnsTrue() {
        profileManager.addProfile(BENSON);
        assertTrue(profileManager.getFirstProfile().equals(BENSON));
        profileManager.deleteProfile(BENSON);
    }

    @Test
    public void getFirstProfile_profileNotInProfileList_returnsFalse() {
        profileManager.addProfile(BENSON);
        assertFalse(profileManager.getFirstProfile().equals(ALICE));
        profileManager.deleteProfile(BENSON);
    }

    @Test
    public void addDeadline_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.addDeadline(null));
    }

    @Test
    public void deleteDeadline_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.deleteDeadline(null));
    }

    @Test
    public void replaceDeadline_nullDeadline_throwsNullPointerException() {
        Deadline deadline = new Deadline("CS1101S", "Dummy description");
        assertThrows(NullPointerException.class, () -> profileManager.replaceDeadline(deadline, null));
        assertThrows(NullPointerException.class, () -> profileManager.replaceDeadline(null, deadline));
        assertThrows(NullPointerException.class, () -> profileManager.replaceDeadline(null, null));
    }

    @Test
    public void clearDeadlineList_listSizeZero_returnsTrue() {
        profileManager.clearDeadlineList();
        assertTrue(profileManager.getSortedDeadlineList().size() == 0);
    }


}
