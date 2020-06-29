package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;

//@@author gyant6
/**
 * Represents the in-memory model of the course list data.
 */
public class CourseManager {

    private CourseList courseList;

    public CourseManager(CourseList courseList) {
        requireNonNull(courseList);
        this.courseList = courseList;
    }

    public CourseManager() {
        this(new CourseList());
    }

    public Course getCourse(CourseName courseName) throws ParseException {
        return courseList.getCourse(courseName);
    }

    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        return courseList.getCourseFocusArea(focusAreaName);
    }
    /*
    public static CourseList getCourseList() {
        return null;
    }
     */
}
