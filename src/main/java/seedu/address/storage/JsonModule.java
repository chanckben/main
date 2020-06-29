package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Description;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Preclusions;
import seedu.address.model.module.PrereqTreeNode;
import seedu.address.model.module.Prereqs;
import seedu.address.model.module.SemesterData;
import seedu.address.model.module.Title;

//@@author chanckben
/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String title;
    private final String description;
    private final String moduleCredit;
    private final String prerequisite;
    private final String preclusion;
    private final List<JsonSemesterData> semesterData;
    private final JsonPrereqTreeNode prereqTree;

    @JsonCreator
    public JsonModule(@JsonProperty("moduleCode") String moduleCode,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("moduleCredit") String moduleCredit,
            @JsonProperty("prerequisite") String prerequisite,
            @JsonProperty("preclusion") String preclusion,
            @JsonProperty("semesterData") List<JsonSemesterData> semesterData,
            @JsonProperty("prereqTree") JsonPrereqTreeNode prereqTree) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleCredit = moduleCredit;
        this.prerequisite = prerequisite;
        this.preclusion = preclusion;
        this.semesterData = semesterData;
        this.prereqTree = prereqTree;
    }

    /**
     * Converts this Jackson-friendly module object into a {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the module.
     */
    public Module toModelType() throws IllegalValueException {
        // Handle uninitialised attributes
        // Note that some fields such as prerequisite and preclusion are optional fields and are thus omitted
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        } else if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        } else if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        } else if (moduleCredit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModularCredits.class.getSimpleName()));
        } else if (semesterData == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SemesterData.class.getSimpleName()));
        }


        // Handle invalid values
        if (!ModuleCode.isValidCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        } else if (!ModularCredits.isValidCredits(moduleCredit)) {
            throw new IllegalValueException(ModularCredits.MESSAGE_CONSTRAINTS);
        } /*else if (!SemesterData.isValid(semesterData)) {
            throw new IllegalValueException(SemesterData.MESSAGE_CONSTRAINTS);
        } */

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);
        final Title modelTitle = new Title(title);
        final Description modelDescription = new Description(description);
        final ModularCredits modelModuleCredit = new ModularCredits(moduleCredit);
        final Prereqs modelPrerequisite = new Prereqs(prerequisite);
        final Preclusions modelPreclusion = new Preclusions(preclusion);
        List<String> semesters = new ArrayList<>();
        semesterData.forEach(semData -> semesters.add(semData.getSemester()));
        final SemesterData modelSemesterData = new SemesterData(semesters);
        final PrereqTreeNode modelPrereqTreeNode;
        if (prereqTree == null) {
            modelPrereqTreeNode = null;
        } else {
            modelPrereqTreeNode = prereqTree.toModelType();
        }

        return new Module(modelModuleCode, modelTitle, modelPrerequisite, modelPreclusion,
                modelModuleCredit, modelDescription, modelSemesterData, modelPrereqTreeNode);
    }

}

/**
 * Jackson-friendly version of {@link SemesterData}.
 */
class JsonSemesterData {
    private String semester;

    @JsonCreator
    public JsonSemesterData(@JsonProperty("semester") String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }
}

