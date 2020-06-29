package seedu.address.model.module.personal;

import java.util.ArrayList;

import seedu.address.model.profile.exceptions.DeadlineNotFoundException;

//@@author wanxuanong
/**
 * DeadlineList contains the list of deadlines and methods for the list.
 */
public class DeadlineList {
    private ArrayList<Deadline> list;

    public DeadlineList() {
        this.list = new ArrayList<Deadline>();
    }

    public DeadlineList(ArrayList<Deadline> list) {
        this.list = list;
    }

    public ArrayList<Deadline> getList() {
        return list;
    }

    /**
     * Returns specific task required.
     *
     * @param index Index of task in the list.
     * @return Required task.
     */
    public Deadline getDeadline(int index) {
        return list.get(index);
    }

    /**
     * Returns number of tasks in list.
     *
     * @return size of list.
     */
    public int getListSize() {
        return list.size();
    }

    public void addDeadline(Deadline deadline) {
        list.add(deadline);
    }

    public boolean hasDeadline(Deadline deadline) {
        return list.contains(deadline);
    }

    /**
     * Removes Deadline {@code Deadline} specified by user
     * @param deadline To be removed
     * @throws DeadlineNotFoundException if the deadline cannot be found
     */
    public void deleteDeadline(Deadline deadline) throws DeadlineNotFoundException {
        if (!list.removeIf(dl->dl.getDescription().toUpperCase().equals(deadline.getDescription().toUpperCase()))) {
            throw new DeadlineNotFoundException();
        }
    }

    public Deadline getTask(String description) {
        Deadline deadline = null;
        for (Deadline d: list) {
            if (d.getDescription().equalsIgnoreCase(description)) {
                deadline = d;
            }
        }
        return deadline;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineList && other.equals(this));
    }
}
