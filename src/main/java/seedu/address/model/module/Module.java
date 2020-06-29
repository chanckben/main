package seedu.address.model.module;

import java.util.List;

import seedu.address.model.module.personal.Deadline;
import seedu.address.model.module.personal.DeadlineList;
import seedu.address.model.module.personal.Personal;

//@@author gyant6
/**
 * Represents a Module in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    private final ModuleCode moduleCode;
    private final Title title;
    private final Prereqs prereqs;
    private final Preclusions preclusions;
    private final ModularCredits modularCredits;
    private final Description description;
    private final SemesterData semesterData;
    private final PrereqTreeNode prereqTreeNode;

    private Personal personal;
    private int tag; //for colour changing in Deadline panel

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Title title, Prereqs prereqs, Preclusions preclusions,
                  ModularCredits modularCredits, Description description, SemesterData semesterData,
                  PrereqTreeNode prereqTreeNode) {
        // requireAllNonNull() // to be implemented
        this.moduleCode = moduleCode;
        this.title = title;
        this.prereqs = prereqs;
        this.preclusions = preclusions;
        this.modularCredits = modularCredits;
        this.description = description;
        this.semesterData = semesterData;
        this.prereqTreeNode = prereqTreeNode;

        this.personal = new Personal();
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Personal getPersonal() {
        return personal;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Title getTitle() {
        return title;
    }

    public Prereqs getPrereqs() {
        return prereqs;
    }

    public Preclusions getPreclusions() {
        return preclusions;
    }

    public ModularCredits getModularCredits() {
        return modularCredits;
    }

    public Description getDescription() {
        return description;
    }

    public SemesterData getSemesterData() {
        return semesterData;
    }

    public PrereqTreeNode getPrereqTreeNode() {
        return prereqTreeNode;
    }

    public String getStatus() {
        return personal.getStatus();
    }

    public boolean hasGrade() {
        return personal.hasGrade();
    }

    public String getGrade() {
        return personal.getGrade();
    }

    public void deleteGrade() {
        personal.deleteGrade();
    }

    public DeadlineList getDeadlineList() {
        return personal.getDeadlineList();
    }

    public List<Deadline> getDeadlines() {
        return personal.getDeadlines();
    }

    public boolean hasDeadline(Deadline deadline) {
        return personal.hasDeadline(deadline);
    }

    public void deleteDeadline(Deadline deadline) {
        personal.deleteDeadline(deadline);
    }

    public void setTag(int id) {
        this.tag = id;
    } //if tag != null means module is taken before

    public int getTag() {
        return this.tag;
    }

    // To think of whether Personal stuff should have getters and setters here, since it will be largely repetition

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getModuleCode());
        builder.append(" ");
        builder.append(getTitle());
        builder.append("\nPrerequisite: ");
        builder.append(getPrereqs());
        builder.append("\nPreclusions: ");
        builder.append(getPreclusions());
        builder.append("\nModular Credits (MCs): ");
        builder.append(getModularCredits());
        builder.append("\nDescription: ");
        builder.append(getDescription());
        builder.append("\nSemesters Offered: ");
        builder.append(getSemesterData());
        builder.append("\n");
        //builder.append(getAcadYear());

        return builder.toString();
    }

    /**
     * Checks for same module code
     * TODO: Check semester too
     */
    public boolean isSameModule(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && moduleCode.equals(((Module) other).moduleCode)); // state check
    }
    // && (personal.getSemester() == ((Module) other).getPersonal().getSemester())

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && moduleCode.equals(((Module) other).moduleCode)); // state check
    }
}
