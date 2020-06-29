package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.course.CourseFocusArea;

//@@author jadetayy
/**
 * An UI component that displays information of a {@code CourseRequirement}.
 */
public class CourseFocusAreaCard extends UiPart<Region> {

    private static final String FXML = "CourseFocusAreaCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    @FXML
    private HBox cardPane;
    @FXML
    private Label focusArea;
    @FXML
    private Label primaries;
    @FXML
    private Label electives;


    public CourseFocusAreaCard(CourseFocusArea courseFocusArea) {
        super(FXML);
        if (courseFocusArea.getFocusAreaName() != null) {
            focusArea.setText(courseFocusArea.getFocusAreaName());

        }
        primaries.setText("PRIMARIES: \n" + courseFocusArea.getPrimaries());
        electives.setText("\nELECTIVES: \n" + courseFocusArea.getElectives());
    }

}
