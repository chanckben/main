package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;

//@@author jadetayy

public class CourseListTest extends CourseList {

    private CourseList courseList = new CourseList();
    private List<CourseFocusArea> courseFocusAreaList = new ArrayList<>();
    private CourseFocusArea csFocusArea = new CourseFocusArea("Software Engineering",
            new ArrayList<>(), new ArrayList<>());
    private Course course = new Course("Computer Science", new ArrayList<>(), courseFocusAreaList);


    @Test
    public void addCourse_validCourse_returnsTrue() throws ParseException {
        courseList.addCourse(course);
        assertTrue(courseList.getCourse(course.getCourseName()).equals(course));
        assert courseList.getCourseList().size() == 1;
    }

    @Test
    public void getCourse_invalidCourse_throwsParseException() throws ParseException {
        CourseName courseName = new CourseName("Biological Science");
        courseList.addCourse(course);
        assertThrows(ParseException.class, () -> courseList.getCourse(courseName));
    }

    @Test
    public void getCourseFocusArea_validFocusArea_returnsTrue() throws ParseException {
        courseList.addCourse(course);
        courseFocusAreaList.add(csFocusArea);
        assertTrue(courseList.getCourseFocusArea("Software Engineering")
                .equals(course.getCourseFocusArea("Software Engineering")));
    }

    @Test
    public void getCourseFocusArea_invalidFocusArea_returnsFalse() throws ParseException {
        courseList.addCourse(course);
        assertThrows(ParseException.class, () -> courseList.getCourseFocusArea("Financial Analytics"));

    }
}
