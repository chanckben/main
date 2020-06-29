package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.course.CourseRequirement;

//@@author jadetayy
/**
 * An UI component that displays information of a {@code CourseRequirement}.
 */
public class CourseRequirementCard extends UiPart<Region> {

    private static final String FXML = "CourseRequirementCard.fxml";

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
    private Label requirement;
    @FXML
    private Label modularCredits;
    @FXML
    private Label requirementInfo;
    @FXML
    private Label modules;


    public CourseRequirementCard(CourseRequirement courseRequirement) {
        super(FXML);
        requirement.setText(courseRequirement.getRequirementName()
                + " (" + courseRequirement.getModularCredits().toString() + " MCs)");
        requirementInfo.setText("Requirements: " + courseRequirement.getRequirementInfo());
        modules.setText("\nModules: \n" + courseRequirement.getModules());
    }

}
