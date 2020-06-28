package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.AcceptedCourses;
import seedu.address.model.profile.course.AcceptedFocusArea;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.FocusArea;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * Stub for Model with an empty profile
 */
public class ModelStubWithEmptyProfile extends ModelStubEmpty {
    public ModelStubWithEmptyProfile() {
        // Set ProfileList
        ObservableList<Profile> profileList = FXCollections.observableArrayList();
        Profile profile = new Profile(new Name("John"), new CourseName(
                AcceptedCourses.COMPUTER_SCIENCE.getName()), 1,
                new FocusArea(AcceptedFocusArea.COMPUTER_SECURITY.getName()));
        profileList.add(profile);
        this.profileList = profileList;
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

    @Override
    public void updateFilteredPersonList(Predicate<Profile> predicate) {
        requireNonNull(predicate);
        filteredProfiles.setPredicate(predicate);
    }

    @Override
    public void clearDeadlineList() {
        this.deadlineList.clear();
    }

    @Override
    public void setNewDeadlineList(Profile editedProfile) {
        if (editedProfile.getDeadlines() != null) {
            this.deadlineList.addAll(editedProfile.getDeadlines());
        }
    }

    /**
     * Returns true if the list contains an equivalent profile as the given argument.
     */
    public boolean contains(Profile toCheck) {
        requireNonNull(toCheck);
        return profileList.stream().anyMatch(toCheck::isSameProfile);
    }
}
