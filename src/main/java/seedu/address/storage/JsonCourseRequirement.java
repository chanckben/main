package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.ModuleCode;

//@@author wanxuanong
/**
 * Jackson-friendly version of {@link CourseRequirement}.
 */
class JsonCourseRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Requirement's %s field is missing!";

    private final String requirementName;
    private final List<String> modules;
    private final String modularCredits;
    private final List<String> requirementInfo;

    @JsonCreator
    public JsonCourseRequirement(@JsonProperty("requirementName") String requirementName,
                               @JsonProperty("modules") List<String> modules,
                               @JsonProperty("modularCredits") String modularCredits,
                                 @JsonProperty("requirementInfo") List<String> requirementInfo) {
        this.requirementName = requirementName;
        this.modules = modules;
        this.modularCredits = modularCredits;
        this.requirementInfo = requirementInfo;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code CourseRequirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public CourseRequirement toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (requirementName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Requirement Name"));
        } else if (modules == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module"));
        } else if (modularCredits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Modular Credits"));
        }

        List<ModuleCode> modelModuleCodes = new ArrayList<>();
        modules.forEach(module -> modelModuleCodes.add(new ModuleCode(module)));
        final ModularCredits modelModuleCredit = new ModularCredits(modularCredits);

        // Important to note requirementInfo may be null

        return new CourseRequirement(requirementName, modelModuleCodes, modelModuleCredit, requirementInfo);
    }

}

