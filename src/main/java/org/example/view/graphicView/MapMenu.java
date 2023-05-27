package org.example.view.graphicView;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MapMenu extends Application {


    @Override
    public void start(Stage stage) throws Exception {

    }

    @FXML
    public void initialize() {

    }

    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            node.setScaleX(node.getScaleX() * zoomFactor);
            node.setScaleY(node.getScaleY() * zoomFactor);
        });
    }

    public void hover(Stage stage) {
        StackPane notedPane = new StackPane();
        notedPane.setPrefSize(20, 20);
        notedPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        notedPane.setStyle("-fx-background-color: purple;");

        StackPane rootPane = new StackPane(notedPane);
        rootPane.setPrefSize(400, 400);
        StackPane.setAlignment(notedPane, Pos.BOTTOM_CENTER);

        stage.setScene(new Scene(rootPane));
        stage.show();
        Stage stickyNotesStage = new Stage();
        stickyNotesStage.initOwner(stage);
        stickyNotesStage.initStyle(StageStyle.UNDECORATED);
        StackPane stickyNotesPane = new StackPane();
        stickyNotesPane.setPrefSize(200, 200);
        stickyNotesPane.setStyle("-fx-background-color: yellow;");
        stickyNotesStage.setScene(new Scene(stickyNotesPane));

        notedPane.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                stickyNotesStage.show();
            } else {
                stickyNotesStage.hide();
            }
        });
    }
}
