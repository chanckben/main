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
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseFocusArea;
import seedu.address.model.course.CourseRequirement;

//@@author jadetayy
/**
 * Panel containing information related to Course.
 */
public class CoursePanel extends UiPart<Region> {
    private static final String FXML = "CoursePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CoursePanel.class);

    @FXML
    private Label courseName;
    @FXML
    private ListView<CourseRequirement> requirements;
    @FXML
    private ListView<CourseFocusArea> focusAreas;

    public CoursePanel(Course course) throws ParseException {
        super(FXML);

        courseName.setText(course.getCourseName().courseName);

        requirements.setItems(FXCollections.observableArrayList(course.getCourseRequirement()));
        requirements.setCellFactory(listView -> new CoursePanel.CourseReqViewCell());


        focusAreas.setItems(FXCollections.observableArrayList(course.getCourseFocusArea()));
        focusAreas.setCellFactory(listView -> new CoursePanel.CourseFocusAreaCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CourseRequirement}
     * using a {@code CourseRequirement}.
     */
    class CourseReqViewCell extends ListCell<CourseRequirement> {
        @Override
        protected void updateItem(CourseRequirement courseRequirement, boolean empty) {
            super.updateItem(courseRequirement, empty);

            if (empty || courseRequirement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CourseRequirementCard(courseRequirement).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CourseFocusArea}
     * using a {@code CourseFocusAreaCard}.
     */
    class CourseFocusAreaCell extends ListCell<CourseFocusArea> {
        @Override
        protected void updateItem(CourseFocusArea courseFocusArea, boolean empty) {
            super.updateItem(courseFocusArea, empty);

            if (empty || courseFocusArea == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CourseFocusAreaCard(courseFocusArea).getRoot());
            }
        }
    }
}
