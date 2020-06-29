package seedu.address.model.module;

//@@author gyant6
/**
 * Represents a Module's list of prerequisites in the module list.
 */
public class Prereqs {

    public final String prereqs;

    /**
     * Constructs a {@code Prereqs}.
     *
     * @param prereqs A valid string of prerequisites
     */
    public Prereqs(String prereqs) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        //ArrayList<String> prereqList = new ArrayList<>();
        /*
        String[] prereqArray = prereqs.split("");
        for (String prereq : prereqArray) {
            prereqList.add(prereq);
        }
         */
        this.prereqs = prereqs;
    }

    @Override
    public String toString() {
        return prereqs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Prereqs // instanceof handles nulls
                && prereqs.equals(((Prereqs) other).prereqs)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
