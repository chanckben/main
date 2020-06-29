package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CourseList;
import seedu.address.model.course.Course;

//@@author gyant6
/**
 * An Immutable ModuleList that is serializable to JSON format.
 */
public class JsonSerializableCourseList {
    public static final String MESSAGE_DUPLICATE_MODULE = "Persons list contains duplicate module(s).";

    private final List<JsonCourse> courses = new ArrayList<>();

    @JsonCreator
    public JsonSerializableCourseList(@JsonProperty("courses") List<JsonCourse> courses) {
        this.courses.addAll(courses);
    }

    /**
     * Converts this course list into a {@code CourseList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CourseList toModelType() throws IllegalValueException {
        CourseList courseList = new CourseList();

        for (JsonCourse jsonCourse : courses) {
            Course modelCourse = jsonCourse.toModelType();
            courseList.addCourse(modelCourse);
        }
        return courseList;
    }
}
