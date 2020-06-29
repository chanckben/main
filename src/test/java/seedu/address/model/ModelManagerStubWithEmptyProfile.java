package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.course.AcceptedCourses;
import seedu.address.model.course.AcceptedFocusArea;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FocusArea;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * Stub for ModelManager with an empty profile
 */
public class ModelManagerStubWithEmptyProfile extends ModelManagerStub {
    public ModelManagerStubWithEmptyProfile() {
        // Set ProfileList
        Profile profile = new Profile(new Name("John"), new CourseName(
                AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
        this.profileList.add(profile);
        filteredProfiles = new FilteredList<>(this.profileList);
    }

    @Override
    public boolean hasOneProfile() {
        return true;
    }

    @Override
    public void setProfile(Profile target, Profile editedProfile) {
        requireAllNonNull(target, editedProfile);

        int index = profileList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameProfile(editedProfile) && contains(editedProfile)) {
            throw new DuplicatePersonException();
        }

        profileList.set(index, editedProfile);
    }

    /**
     * Returns true if given Profile is contained in profileList
     */
    public boolean contains(Profile toCheck) {
        requireNonNull(toCheck);
        return profileList.stream().anyMatch(toCheck::isSameProfile);
    }
}
