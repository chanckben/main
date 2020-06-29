package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.util.TreeMap;

import seedu.address.model.ModuleList;
import seedu.address.model.module.ModularCredits;
import seedu.address.model.module.Module;

//@@author gyant6
/**
 * Creates a Cumulative Average Point (CAP) class
 */
public class Cap {

    private Double cap;
    private Double totalWeightage;
    private Double totalCredits;

    public Cap() {
        this.cap = 0.0;
        this.totalWeightage = 0.0;
        this.totalCredits = 0.0;
    }

    /**
     * Uses the latest ModuleList {@code ModuleList} to calculate the CAP
     * CAP is measured using this formula:
     * CAP = Sum (module grade point x modular credits for the module) / Sum (modular credits)
     * @param semModHashMap
     */
    public void updateCap(TreeMap<Integer, ModuleList> semModHashMap) {
        requireNonNull(semModHashMap);

        totalWeightage = 0.0;
        totalCredits = 0.0;

        for (ModuleList semesterList: semModHashMap.values()) {
            for (Module module: semesterList) {
                if (module.getGrade() != null) {
                    // Convert letter to double
                    String letterGrade = module.getGrade();
                    if (letterGradeNotApplicable(letterGrade)) {
                        continue; // skip this for loop
                    }
                    Double numGrade = convertLetterGradeToNum(letterGrade);

                    // Convert ModularCredits to Double
                    ModularCredits modularCredits = module.getModularCredits();
                    Double numCredits = Double.valueOf(modularCredits.toString());
                    totalCredits += numCredits;

                    Double weightage = numGrade * numCredits;
                    totalWeightage += weightage;
                }
            }
        }

        cap = totalWeightage / totalCredits;
    }

    /**
     * Checks if a grade should be counted towards CAP.
     * @param letterGrade
     * @return Returns true if grade is one of {S, U, CS, CU"}, false otherwise
     */
    private boolean letterGradeNotApplicable(String letterGrade) {
        requireNonNull(letterGrade);

        switch(letterGrade) {
        case "S":
        case "U":
        case "CS":
        case "CU":
            return true;
        default:
            return false;
        }
    }

    /**
     * Converts a letter grade {@code letterGrade} to a Double object
     * @param letterGrade String representation of a grade
     * @return Returns the Double value of a grade
     */
    private Double convertLetterGradeToNum(String letterGrade) {
        requireNonNull(letterGrade);

        switch(letterGrade) {
        case "A+":
        case "A":
            return 5.0;
        case "A-":
            return 4.5;
        case "B+":
            return 4.0;
        case "B":
            return 3.5;
        case "B-":
            return 3.0;
        case "C+":
            return 2.5;
        case "C":
            return 2.0;
        case "D+":
            return 1.5;
        case "D":
            return 1.0;
        default: // "F"
            return 0.0;
        }
    }

    @Override
    public String toString() {
        if (totalWeightage == 0) {
            return "No grades added";
        }
        DecimalFormat df = new DecimalFormat("#.##"); // Convert cap to 2 d.p.
        return df.format(cap);
    }
}
