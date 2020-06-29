package seedu.address.model.course;

//@@author joycelynteo
/**
 * Represents a FocusArea's name
 * Guarantees:
 */
public class FocusArea {

    public final String specialisationName;

    /**
     * Constructs a {@code FocusArea}.
     *
     * @param specialisationName A valid specialisation.
     */
    public FocusArea(String specialisationName) {
        String formattedSpecialisationName = format(specialisationName);
        this.specialisationName = formattedSpecialisationName;
    }

    /**
     * Returns formatted specialisation
     */
    public String format(String specialisationName) {
        specialisationName = specialisationName.trim();
        specialisationName = specialisationName.toUpperCase();
        return specialisationName;
    }

    // TODO: Check valid specialisation
    public static boolean isValid(CourseName courseName, String specialisationName) {
        return AcceptedFocusArea.isValid(courseName, specialisationName);
    }

    @Override
    public String toString() {
        if (specialisationName.isEmpty()) {
            return "";
        } else {
            return specialisationName;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FocusArea // instanceof handles nulls
                && specialisationName.equals(((FocusArea) other).specialisationName)); // state check
    }

}
