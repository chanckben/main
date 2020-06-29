package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@@author gyant6
/**
 * Represents a Module's semester data in the address book.
 * Guarantees:
 */
public class SemesterData {

    public final List<Integer> semesters;

    /**
     * Constructs a {@code SemesterData}.
     *
     * @param semesters Valid semester numbers
     */
    public SemesterData(List<String> semesters) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.semesters = new ArrayList<>();
        semesters.forEach(sem -> this.semesters.add(Integer.parseInt(sem)));
    }

    public List<String> getSemesters() {
        return this.semesters.stream().map(x->x.toString()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.semesters.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SemesterData // instanceof handles nulls
                && semesters.equals(((SemesterData) other).semesters)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
