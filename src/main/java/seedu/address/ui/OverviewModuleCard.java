package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

//@@author jadetayy
/**
 * An UI component that displays information of a {@code Module} in Overview Panel.
 */
public class OverviewModuleCard extends UiPart<Region> {

    private static final String FXML = "OverviewModuleCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleName;
    @FXML
    private Label moduleCode;
    @FXML
    private Label grade;


    public OverviewModuleCard(Module module) {
        super(FXML);
        this.module = module;
        moduleCode.setText(module.getModuleCode().toString());
        moduleName.setText(module.getTitle().toString());
        grade.setText("Grade: " + module.getGrade());
        if (module.getGrade() == null) {
            grade.setText("Grade: -");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return module.equals(card.module);
    }
}
