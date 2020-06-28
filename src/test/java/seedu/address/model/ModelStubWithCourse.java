package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.AcceptedCourses;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseFocusArea;

/**
 * Stub for Model with a course in courseList and a focus area
 */
public class ModelStubWithCourse extends ModelStubEmpty {
    public ModelStubWithCourse() {
        List<CourseFocusArea> courseFocusAreaList = new ArrayList<>();
        courseFocusAreaList.add(
                new CourseFocusArea("Computer Security", new ArrayList<>(), new ArrayList<>()));
        this.courseList.add(
                new Course(AcceptedCourses.COMPUTER_SCIENCE.getName(), new ArrayList<>(), courseFocusAreaList));
    }

    @Override
    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        requireNonNull(focusAreaName);
        for (Course course : courseList) {
            try {
                CourseFocusArea focusArea = course.getCourseFocusArea(focusAreaName);
                return focusArea;
            } catch (ParseException e) {
                continue;
            }
        }

        throw new ParseException(MESSAGE_INVALID_COURSE_FOCUS_AREA);
    }

    @Override
    public ModuleList getModuleList() {
        ModuleList moduleList = new ModuleList();
        this.moduleList.forEach(moduleList::addModule);
        return moduleList;
    }
}
