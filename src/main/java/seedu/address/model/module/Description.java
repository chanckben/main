package seedu.address.model.module;

//@@author gyant6
/**
 * Represents a Module's description in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)} // to be implemented
public class Description {

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid module description.
     */
    public Description(String description) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    // methods to be implemented
    // isValidDescription()
    // hashCode()
}
