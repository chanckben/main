package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.module.Module;

//@@author jadetayy
/**
 * Panel containing information related to CourseFocusArea.
 */
public class FocusAreaPanel extends UiPart<Region> {
    private static final String FXML = "FocusAreaPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FocusAreaPanel.class);

    @FXML
    private Label focusArea;
    @FXML
    private ListView<Module> primaries;
    @FXML
    private ListView<Module> electives;

    public FocusAreaPanel(CourseFocusArea courseFocusArea) throws ParseException {
        super(FXML);

        focusArea.setText(courseFocusArea.getFocusAreaName().toUpperCase());

        primaries.setItems(FXCollections.observableArrayList(courseFocusArea.getPrim()));
        primaries.setCellFactory(listView -> new ModuleListViewCell());

        electives.setItems(FXCollections.observableArrayList(courseFocusArea.getElec()));
        electives.setCellFactory(listView -> new ModuleListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(module).getRoot());
            }
        }
    }


}
