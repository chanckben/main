package seedu.address.model.course;

import java.util.List;

import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.ModuleCode;


//@@author wanxuanong
/**
 * Represents a Course's requirement.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseRequirement {

    private final String requirementName;
    private final ModularCredits modularCredits;
    private final List<ModuleCode> modules;
    private final List<String> requirementInfo;

    /**
     * Every field must be present and not null.
     */
    public CourseRequirement(String requirementName, List<ModuleCode> modules, ModularCredits modularCredits,
                             List<String> requirementInfo) {
        this.requirementName = requirementName;
        this.modules = modules;
        this.modularCredits = modularCredits;
        this.requirementInfo = requirementInfo;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public String getModules() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (ModuleCode mc : modules) {
            sb.append(i + ". ");
            sb.append(mc.toString() + "\n");
            i++;
        }
        return sb.toString();
    }

    public ModularCredits getModularCredits() {
        return modularCredits;
    }

    public String getRequirementInfo() {
        StringBuilder sb = new StringBuilder();
        if (requirementInfo != null) {
            sb.append("\n");
            for (String str : requirementInfo) {
                sb.append("\u2022 ");
                sb.append(str);
            }
        } else {
            sb.append("-");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        double integerCredits = Double.valueOf(modularCredits.toString());

        output.append(requirementName + " ");
        output.append("(" + (int) integerCredits + " MCs):\n");

        if (requirementInfo != null) {
            for (String info : requirementInfo) {
                output.append("\u2022 ");
                output.append(info + "\n");
            }
        }

        for (ModuleCode moduleCode : modules) {
            output.append(moduleCode);
            output.append("\n");
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseRequirement // instanceof handles nulls
                && requirementName.equals(((CourseRequirement) other).requirementName)); // state check
    }
}
