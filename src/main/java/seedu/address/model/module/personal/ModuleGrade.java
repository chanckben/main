package seedu.address.model.module.personal;

//@@author chanckben
/**
 * Typical grades in NUS stored as enumerations.
 */
public enum ModuleGrade {
    A_PLUS("A+"),
    A("A"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B("B"),
    B_MINUS("B-"),
    C_PLUS("C+"),
    C("C"),
    D_PLUS("D+"),
    D("D"),
    F("F"),
    S("S"),
    U("U"),
    CS("CS"),
    CU("CU");

    private final String gradeDisplayed;

    ModuleGrade(String gradeDisplayed) {
        this.gradeDisplayed = gradeDisplayed;
    }

    @Override
    public String toString() {
        return this.gradeDisplayed;
    }
}
