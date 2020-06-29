package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;

import java.util.ArrayList;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;

//@@author gyant6
/**
 * Creates a new CourseList object which contains Module objects.
 */
public class CourseList {

    private ArrayList<Course> courseList = new ArrayList<>();

    public CourseList() {}

    /**
     * Adds a course to the course list.
     */
    public void addCourse(Course course) {
        courseList.add(course);
    }

    public Course getCourse(CourseName courseName) throws ParseException {
        requireNonNull(courseName);
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        throw new ParseException(MESSAGE_INVALID_COURSE);
    }

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

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
}
