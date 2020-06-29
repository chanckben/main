package seedu.address.model.module;

//@@author gyant6
/**
 * Represents a Module's code in the address book.
 * Guarantees:
 */
// Guarantees: immutable; is valid as declared in {@link #isValidCode(String)} // to be implemented
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should have 2-4 capital letters at the front, 0-5 capital letters at the back "
            + "and exactly 4 digits in between";

    public static final String VALIDATION_REGEXES = "[A-Z]{2,4}[\\d]{4}[A-Z]{0,5}$";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        // requireAllNonNull() // to be implemented
        // checkArgument() // to be implemented
        this.moduleCode = moduleCode.toUpperCase();
    }

    public static boolean isValidCode(String moduleCode) {
        return moduleCode.matches(VALIDATION_REGEXES);
    }

    /**
     * Removes the trailing letters (suffix) of a module code.
     * Useful in cases when checking if variants of a module (e.g. CS1010S)
     * are used in placed of the original (e.g. CS1010).
     */
    public ModuleCode removeSuffix() {
        String toReturn = "";
        int index = 0;
        char modCodeChar = moduleCode.charAt(0);
        while (!Character.isDigit(modCodeChar)) {
            toReturn += modCodeChar;
            modCodeChar = moduleCode.charAt(++index);
        }
        toReturn += moduleCode.substring(index, index + 4);
        return new ModuleCode(toReturn);
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    // methods to be implemented
    // hashCode()
}
