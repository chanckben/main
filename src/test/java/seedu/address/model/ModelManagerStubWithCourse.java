package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.AcceptedCourses;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;

/**
 * Stub for ModelManager containing one course in courseList and one focus area
 */
public class ModelManagerStubWithCourse extends ModelManagerStub {
    public ModelManagerStubWithCourse() {
        List<CourseFocusArea> courseFocusAreaList = new ArrayList<>();
        courseFocusAreaList.add(
                new CourseFocusArea("Computer Security", new ArrayList<>(), new ArrayList<>()));
        this.courseList.add(
                new Course(AcceptedCourses.COMPUTER_SCIENCE.getName(), new ArrayList<>(), courseFocusAreaList));
    }

    @Override
    public Course getCourse(CourseName courseName) throws ParseException {
        for (Course course: courseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        throw new ParseException("Course does not exist");
    }

    @Override
    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        requireNonNull(focusAreaName);
        for (Course course: courseList) {
            try {
                CourseFocusArea focusArea = course.getCourseFocusArea(focusAreaName);
                return focusArea;
            } catch (ParseException e) {
                continue;
            }
        }
        throw new ParseException(MESSAGE_INVALID_COURSE_FOCUS_AREA);
    }

}
