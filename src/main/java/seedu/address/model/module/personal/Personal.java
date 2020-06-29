package seedu.address.model.module.personal;

import java.util.List;

//@@author chanckben
/**
 * A wrapper class to contain attributes of a Module specific to a particular student.
 */
public class Personal {

    private Status status;
    private Grade grade;
    private DeadlineList deadlineList;

    public Personal() {
        status = new Status();
        grade = new Grade();
        deadlineList = new DeadlineList();
    }

    // Currently unusable because all the fields are private.
    // TODO: Link the parsed input to the input of Personal, then call the respective functions within each class.

    public void setGrade(String grade) {
        this.grade.setGrade(grade);
    }

    public void setStatus(String status) {
        if (status.toLowerCase().equals("completed")) {
            this.status.setCompleted();
        } else if (status.replaceAll("_", " ").toLowerCase().equals("in progress")) {
            this.status.setInProgress();
        } else {
            this.status.setPlanning();
        }
    }

    public void addDeadline(Deadline deadline) {
        this.deadlineList.addDeadline(deadline);
    }

    public boolean hasDeadline(Deadline deadline) {
        return this.deadlineList.hasDeadline(deadline);
    }

    public void deleteDeadline(Deadline deadline) {
        this.deadlineList.deleteDeadline(deadline);
    }

    public String getStatus() {
        return this.status.getStatus();
    }

    public boolean hasGrade() {
        return this.grade.getGrade() != null;
    }

    public String getGrade() {
        return this.grade.getGrade();
    }

    public void deleteGrade() {
        this.grade.deleteGrade();
    }

    public DeadlineList getDeadlineList() {
        return this.deadlineList;
    }

    public List<Deadline> getDeadlines() {
        return this.deadlineList.getList();
    }
}
