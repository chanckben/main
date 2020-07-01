package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.profile.Profile;

//@@author jadetayy
/**
 * Panel containing the overview of a Profile.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Profile> profile;
    @FXML
    private Label cap;
    @FXML
    private ScrollPane modulesPanel;
    @FXML
    private ListView<Module> moduleSemesterPanel;


    public OverviewPanel(ObservableList<Profile> profileList) throws ParseException {
        super(FXML);


        profile.setItems(profileList);
        profile.setCellFactory(listView -> new ProfileListViewCell());

        Profile profile = profileList.get(0);

        cap.setText("Current CAP: \n" + profile.getCap());


        //Modules panel
        HBox modPane = new HBox();

        for (Integer i : profile.getAllModules().keySet()) {

            if (!profile.getAllModules().get(i).getModuleList().isEmpty()) {
                HBox temp = new HBox();
                temp.setAlignment(Pos.CENTER);
                temp.setMaxWidth(40);
                temp.setBackground(new Background(
                        new BackgroundFill(Color.valueOf("#383838"), CornerRadii.EMPTY, Insets.EMPTY)));
                String yearSem;
                if (i == 1) {
                    yearSem = "Year 1 Semester 1";
                } else if (i == 2) {
                    yearSem = "Year 1 Semester 2";
                } else if (i == 3) {
                    yearSem = "Year 2 Semester 1";
                } else if (i == 4) {
                    yearSem = "Year 2 Semester 2";
                } else if (i == 5) {
                    yearSem = "Year 3 Semester 1";
                } else if (i == 6) {
                    yearSem = "Year 3 Semester 2";
                } else if (i == 7) {
                    yearSem = "Year 4 Semester 1";
                } else if (i == 8) {
                    yearSem = "Year 4 Semester 2";
                } else if (i == 9) {
                    yearSem = "Year 5 Semester 1";
                } else if (i == 10) {
                    yearSem = "Year 5 Semester 2";
                } else if (i == 11) {
                    yearSem = "Year 6 Semester 1";
                } else if (i == 12) {
                    yearSem = "Year 6 Semester 2";
                } else if (i == 13) {
                    yearSem = "Year 7 Semester 1";
                } else if (i == 14) {
                    yearSem = "Year 7 Semester 2";
                } else if (i == 15) {
                    yearSem = "Year 8 Semester 1";
                } else if (i == 16) {
                    yearSem = "Year 8 Semester 2";
                } else if (i == 17) {
                    yearSem = "Year 9 Semester 1";
                } else {
                    yearSem = "Year 9 Semester 2";
                }
                Label listTitle = new Label(yearSem.toUpperCase());


                listTitle.setRotate(-90);
                listTitle.setWrapText(true);
                listTitle.setStyle("-fx-font-family: \"Segoe Pro Display\"; -fx-font-weight: bold; "
                        + "-fx-font-size: 15; -fx-text-fill: #f7c4bb;");

                temp.getChildren().addAll(new Group(listTitle));

                ListView<Module> moduleSemPanel = new ListView<>();
                moduleSemPanel.setItems(profile.getModules(i).getModuleList());
                moduleSemPanel.setCellFactory(listView -> new ModuleListViewCell());

                modPane.setBackground(new Background(
                        new BackgroundFill(Color.valueOf("#383838"), CornerRadii.EMPTY, Insets.EMPTY)));
                modPane.setSpacing(5);
                modPane.getChildren().addAll(temp, moduleSemPanel);
            }

        }

        modulesPanel.setFitToHeight(true);
        modulesPanel.setContent(modPane);

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OverviewModuleCard(module).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Profile} using a {@code DeadlineCard}.
     */
    class ProfileListViewCell extends ListCell<Profile> {
        @Override
        protected void updateItem(Profile profile, boolean empty) {
            super.updateItem(profile, empty);
            if (empty || profile == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProfileCard(profile).getRoot());
            }
        }
    }

}
