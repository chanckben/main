package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseName;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.personal.Deadline;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;

/**
 * Stub for ModelManager
 */
public class ModelManagerStub extends ModelManager {
    protected ObservableList<Profile> profileList = FXCollections.observableArrayList();
    protected ObservableList<Deadline> deadlineList;
    protected FilteredList<Profile> filteredProfiles;
    protected List<Module> moduleList;
    protected List<Course> courseList;

    public ModelManagerStub(List<Profile> profileList, List<Module> moduleList, List<Course> courseList) {
        requireNonNull(profileList);
        requireNonNull(moduleList);
        requireNonNull(courseList);

        this.profileList.setAll(profileList);
        filteredProfiles = new FilteredList<>(this.profileList);
        deadlineList = FXCollections.observableArrayList();
        this.moduleList = moduleList;
        this.courseList = courseList;
    }

    public ModelManagerStub() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void addProfile(Profile profile) {
        this.profileList.add(profile);
    }

    @Override
    public ProfileList getProfileList() {
        ProfileList profileList = new ProfileList();
        profileList.setProfiles(this.profileList);
        return profileList;
    }

    @Override
    public boolean hasProfile(Name name) {
        requireNonNull(name);
        return this.profileList.stream().map(Profile::getName).anyMatch(x->x.equals(name));
    }

    @Override
    public Profile getFirstProfile() {
        return this.profileList.get(0);
    }

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        return filteredProfiles;
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

    @Override
    public Course getCourse(CourseName courseName) throws ParseException {
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        throw new ParseException("Course does not exist");
    }

    @Override
    public void setDisplayedView(Profile toDisplay) {
    }
}
