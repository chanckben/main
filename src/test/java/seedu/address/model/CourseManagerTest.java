package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOCUS_AREA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.AcceptedCourses;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseName;

//@@author joycelynteo

public class CourseManagerTest {

    private CourseManager courseManager = new CourseManager(new CourseListStub());

    @Test
    public void getCourse_validCourse_success() {
        CourseName courseNameLowerCaps = new CourseName("computer science");
        CourseName courseNameUpperCase = new CourseName("COMPUTER SCIENCE");
        CourseName courseNameMixedCase = new CourseName("cOmPuTeR sCiEnCe");
        Course course = new Course(VALID_COURSE_AMY, new ArrayList<>(), new ArrayList<>());
        try {
            assertEquals(courseManager.getCourse(courseNameLowerCaps), course);
            assertEquals(courseManager.getCourse(courseNameUpperCase), course);
            assertEquals(courseManager.getCourse(courseNameMixedCase), course);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void getCourse_invalidCourse_throwsParseException() {
        CourseName courseName = new CourseName(INVALID_COURSE);
        assertThrows(ParseException.class, () -> courseManager.getCourse(courseName), MESSAGE_INVALID_COURSE);
    }

    @Test
    public void getCourseFocusArea_validFocusArea_success() {
        String focusAreaLowerCase = "software engineering";
        String focusAreaUpperCase = "SOFTWARE ENGINEERING";
        String focusAreaMixedCase = "sOfTwArE eNgInEeRiNg";
        CourseFocusArea courseFocusArea = new CourseFocusArea("Software Engineering", new ArrayList<>(),
                new ArrayList<>());
        try {
            assertEquals(courseManager.getCourseFocusArea(focusAreaLowerCase), courseFocusArea);
            assertEquals(courseManager.getCourseFocusArea(focusAreaUpperCase), courseFocusArea);
            assertEquals(courseManager.getCourseFocusArea(focusAreaMixedCase), courseFocusArea);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void getCourseFocusArea_invalidFocusArea_throwsParseException() {
        String focusArea = INVALID_FOCUS_AREA;
        assertThrows(ParseException.class, () -> courseManager.getCourseFocusArea(focusArea),
                MESSAGE_INVALID_COURSE_FOCUS_AREA);
    }

    private class CourseListStub extends CourseList {
        private List<Course> courseList = new ArrayList<Course>();

        public CourseListStub() {
            List<CourseFocusArea> courseFocusAreaList = new ArrayList<>();
            courseFocusAreaList.add(
                    new CourseFocusArea("Software Engineering", new ArrayList<>(), new ArrayList<>()));
            courseList.add(new Course(AcceptedCourses.COMPUTER_SCIENCE.getName(), new ArrayList<>(),
                    courseFocusAreaList));
        }

        @Override
        public Course getCourse(CourseName courseName) throws ParseException {
            requireNonNull(courseName);
            for (Course course : courseList) {
                if (course.getCourseName().equals(courseName)) {
                    return course;
                }
            }
            throw new ParseException(MESSAGE_INVALID_COURSE);
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
    }
}
