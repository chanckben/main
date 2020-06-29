package seedu.address.model.module;

//@@author gyant6
/**
 * Represents a Module's number of modular credits in the address book.
 * Guarantees:
 */
public class ModularCredits {

    public static final String MESSAGE_CONSTRAINTS =
            "Modular credits should be a positive number less than 100";

    public static final String VALIDATION_REGEX = "[0-9]{1,2}[.]{0,1}[0-9]{0,1}$";

    public final double modularCredits;

    /**
     * Constructs a {@code ModularCredits}.
     *
     * @param modularCredits A valid String Integer of modular credits
     */
    public ModularCredits(String modularCredits) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        double credits = Double.parseDouble(modularCredits);
        this.modularCredits = credits;
    }

    public static boolean isValidCredits(String modularCredits) {
        return modularCredits.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf((int) modularCredits);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModularCredits // instanceof handles nulls
                && modularCredits == (((ModularCredits) other).modularCredits)); // state check
    }

    // methods to be implemented
    // isValidCode()
    // hashCode()
}
