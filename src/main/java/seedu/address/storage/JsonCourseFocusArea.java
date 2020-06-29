package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.module.ModuleCode;

//@@author wanxuanong
/**
 * Jackson-friendly version of {@link CourseFocusArea}.
 */
class JsonCourseFocusArea {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course's %s field is missing!";

    private final String focusAreaName;
    private final List<String> primaries;
    private final List<String> electives;

    @JsonCreator
    public JsonCourseFocusArea(@JsonProperty("focusAreaName") String focusAreaName,
                      @JsonProperty("primaries") List<String> primaries,
                      @JsonProperty("electives") List<String> electives) {
        this.focusAreaName = focusAreaName;
        this.primaries = primaries;
        this.electives = electives;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code CourseFocusArea} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public CourseFocusArea toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        if (focusAreaName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Focus Area"));
        }

        /*
        else if (primaries == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Area Primary"));
        } else if (electives == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Area Elective"));
        }
         */

        List<ModuleCode> modelPrimaries = new ArrayList<>();
        if (primaries != null) {
            primaries.forEach(modelPrimary -> modelPrimaries.add(new ModuleCode(modelPrimary)));
        }

        List<ModuleCode> modelElectives = new ArrayList<>();
        if (electives != null) {
            electives.forEach(modelElective -> modelElectives.add(new ModuleCode(modelElective)));
        }
        return new CourseFocusArea(focusAreaName, modelPrimaries, modelElectives);
    }

}

