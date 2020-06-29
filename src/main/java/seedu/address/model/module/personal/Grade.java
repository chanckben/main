package seedu.address.model.module.personal;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@@author chanckben
/**
 * Stores the grade of a profile's Module object.
 */
public class Grade {

    public static final String MESSAGE_CONSTRAINTS = "Module's grade field should contain only one of these values: "
            + Stream.of(ModuleGrade.values()).map(ModuleGrade::toString).collect(Collectors.toList()).toString();

    private ModuleGrade myGrade;

    public String getGrade() {
        if (myGrade != null) {
            return myGrade.toString();
        }
        return null;
    }

    public void setGrade(String grade) {
        for (ModuleGrade g: ModuleGrade.values()) {
            if (g.toString().equals(grade)) {
                this.myGrade = g;
                return;
            }
        }
        throw new NoSuchElementException("Grade " + grade + " does not exist");
    }

    /**
     * Sets the grade to null, effectively deleting it.
     */
    public void deleteGrade() {
        if (myGrade == null) {
            throw new NoSuchElementException();
        }
        myGrade = null;
    }

    public static boolean isValidGrade(String grade) {
        return Stream.of(ModuleGrade.values()).map(ModuleGrade::toString).anyMatch(x -> x.equals(grade));
    }
}
