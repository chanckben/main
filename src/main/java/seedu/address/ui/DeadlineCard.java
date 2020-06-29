package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.model.ModuleList;
import seedu.address.model.module.Module;
import seedu.address.model.module.personal.Deadline;

//@@author jadetayy
/**
 * An UI component that displays information of a {@code Profile}.
 */
public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deadline deadline;

    @FXML
    private HBox cardPane;
    @FXML
    private Text module;
    @FXML
    private Label description;
    @FXML
    private Text date;
    @FXML
    private Label time;
    @FXML
    private Pane moduleColour;


    public DeadlineCard(Deadline deadline, ModuleList moduleList) {
        super(FXML);
        this.deadline = deadline;

        //to colourise modules pane
        Module thisMod = null;
        for (Module mod : moduleList) {
            if (deadline.getModuleCode().equals(mod.getModuleCode().toString())) {
                thisMod = mod;
            }
        }

        int tag = thisMod.getTag();
        if (tag == 0) { //up to 8 modules
            moduleColour.setStyle("-fx-background-color: \"84a9ac\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 1) {
            moduleColour.setStyle("-fx-background-color: \"d45d79\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 2) {
            moduleColour.setStyle("-fx-background-color: \"ff9933\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 3) {
            moduleColour.setStyle("-fx-background-color: \"f6d186\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 4) {
            moduleColour.setStyle("-fx-background-color: \"b590ca\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 5) {
            moduleColour.setStyle("-fx-background-color: \"ea9085\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 6) {
            moduleColour.setStyle("-fx-background-color: \"cae8d5\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 7) {
            moduleColour.setStyle("-fx-background-color: \"FFE6EB\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 8) {
            moduleColour.setStyle("-fx-background-color: \"D9FFF8\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 9) {
            moduleColour.setStyle("-fx-background-color: \"FFFFD2\"; -fx-border-color: #3a3a3a;");
        } else if (tag == 10) {
            moduleColour.setStyle("-fx-background-color: \"A9FFC0\"; -fx-border-color: #3a3a3a;");
        }

        //module name
        module.setText(deadline.getModuleCode());
        module.setFill(Color.WHITE);

        description.setText("Task: " + deadline.getDescription());
        if (deadline.getDate() == null) {
            date.setText("Date: -");
            date.setFill(Color.WHITE);
            time.setText("Time: -");
        } else {
            date.setText("Date: " + deadline.getStringDate());
            if (deadline.getTag().equals("RED")) {
                date.setFill(Color.valueOf("F05B5B"));
            } else if (deadline.getTag().equals("YELLOW")) {
                date.setFill(Color.YELLOW);
            } else {
                date.setFill(Color.LAWNGREEN);
            }

            time.setText("Time: " + deadline.getStringTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCard)) {
            return false;
        }

        // state check
        DeadlineCard card = (DeadlineCard) other;
        return deadline.equals(card.deadline);
    }
}
