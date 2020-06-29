package seedu.address.model.module.personal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

//@@author chanckben
/**
 * Keeps track of the current status of the module.
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Module's status field should contain only one of these values: "
            + Stream.of(ModuleStatus.values()).map(ModuleStatus::name).collect(Collectors.toList()).toString();

    private ModuleStatus status;

    public Status() {
        this.status = ModuleStatus.PLANNING;
    }

    public String getStatus() {
        return status.name();
    }

    public boolean isPlanning() {
        return this.status.equals(ModuleStatus.PLANNING);
    }

    public boolean isTaking() {
        return this.status.equals(ModuleStatus.IN_PROGRESS);
    }

    public boolean hasCompleted() {
        return this.status.equals(ModuleStatus.COMPLETED);
    }

    public void setPlanning() {
        this.status = ModuleStatus.PLANNING;
    }

    public void setInProgress() {
        this.status = ModuleStatus.IN_PROGRESS;
    }

    public void setCompleted() {
        this.status = ModuleStatus.COMPLETED;
    }

    /**
     * Returns true if the status is valid as defined in ModuleStatus.
     */
    public static boolean isValidStatus(String status) {
        for (ModuleStatus s: ModuleStatus.values()) {
            if (s.toString().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
