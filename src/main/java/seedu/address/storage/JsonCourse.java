package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseRequirement;

//@@author gyant6
/**
 * Jackson-friendly version of {@link JsonCourse}.
 */
class JsonCourse {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String courseName;
    private final List<JsonCourseRequirement> requirements;
    private final List<JsonCourseFocusArea> focusAreas;

    @JsonCreator
    public JsonCourse(@JsonProperty("focusAreaName") String courseName,
                      @JsonProperty("requirements") List <JsonCourseRequirement> requirements,
                      @JsonProperty("focusAreas") List <JsonCourseFocusArea> focusAreas) {
        this.courseName = courseName;
        this.requirements = requirements;
        this.focusAreas = focusAreas;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public Course toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (courseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Course"));
        } else if (requirements == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Requirement"));
        }

        List<CourseRequirement> modelRequirements = new ArrayList<>();
        for (JsonCourseRequirement requirement : requirements) {
            CourseRequirement modelRequirement = requirement.toModelType();
            modelRequirements.add(modelRequirement);
        }

        List<CourseFocusArea> modelFocusAreas = new ArrayList<>();
        if (focusAreas != null) {
            for (JsonCourseFocusArea focusArea : focusAreas) {
                CourseFocusArea modelFocusArea = focusArea.toModelType();
                modelFocusAreas.add(modelFocusArea);
            }
        }

        return new Course(courseName, modelRequirements, modelFocusAreas);
    }
}


