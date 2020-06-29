package seedu.address.model.module;

//@@author gyant6
/**
 * Represents a Module's title in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)} // to be implemented
public class Title {

    public final String title;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid module title.
     */
    public Title(String title) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

    // methods to be implemented
    // isValidTitle()
    // hashCode()
}
