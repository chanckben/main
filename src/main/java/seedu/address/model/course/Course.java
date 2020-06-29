package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE_FOCUS_AREA;

import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author gyant6
/**
 * Represents a profile's course in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    private final String courseName;
    private final List<CourseRequirement> requirements;
    private final List<CourseFocusArea> focusAreas;

    public Course(String courseName, List<CourseRequirement> requirements,
                  List<CourseFocusArea> focusAreas) {
        this.courseName = courseName.toUpperCase();
        this.requirements = requirements;
        this.focusAreas = focusAreas;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(courseName);

        if (!requirements.isEmpty()) {
            for (CourseRequirement requirement : requirements) {
                output.append("\n");
                output.append(requirement);
            }
        }

        if (!focusAreas.isEmpty()) {
            output.append("\nFocus Areas:");
            for (CourseFocusArea focusArea : focusAreas) {
                output.append("\n");
                output.append(focusArea.getFocusAreaName());
            }
        }

        output.append("\n");
        return output.toString();
    }

    public CourseName getCourseName() throws ParseException {
        return new CourseName(courseName);
    }

    public List<CourseRequirement> getCourseRequirement() {
        return this.requirements;
    }

    public List<CourseFocusArea> getCourseFocusArea() {
        return this.focusAreas;
    }

    public CourseFocusArea getCourseFocusArea(String focusAreaName) throws ParseException {
        requireNonNull(focusAreaName);
        for (CourseFocusArea courseFocusArea : focusAreas) {
            if (courseFocusArea.getFocusAreaName().trim().toUpperCase().equals(focusAreaName.trim().toUpperCase())) {
                return courseFocusArea;
            }
        }

        throw new ParseException(MESSAGE_INVALID_COURSE_FOCUS_AREA);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Course // instanceof handles nulls
                && courseName.equals(((Course) other).courseName)); // state check
    }
}
