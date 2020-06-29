package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ProfileManager;
import seedu.address.model.module.personal.Deadline;

//@@author jadetayy
/**
 * Panel containing the list of deadlines.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private static ProfileManager pm;
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);

    @FXML
    private ListView<Deadline> deadlineListView;

    public DeadlineListPanel(ObservableList<Deadline> deadlineList, ProfileManager pm) {
        super(FXML);
        this.pm = pm;
        deadlineListView.setItems(deadlineList); //sets an observable list
        deadlineListView.setCellFactory(listView -> new DeadlineListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class DeadlineListViewCell extends ListCell<Deadline> {
        @Override
        protected void updateItem(Deadline deadline, boolean empty) {
            super.updateItem(deadline, empty);

            if (empty || deadline == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeadlineCard(deadline, pm.getFirstProfile().getCurModules()).getRoot());
            }
        }
    }

}
