package org.example.view.graphicView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.model.building.Building;
import org.example.model.building.Gatehouse;
import org.example.model.building.enums.MaterialType;
import org.example.view.enums.Outputs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RepairMenu extends Application {

    private static Building currentBuilding;
    private static Button open;
    private static Button close;
    private static ProgressBar progressBar;

    @Override
    public void start(Stage stage) throws Exception {
    }

    public static void setCurrentBuilding(Building currentBuilding) {
        RepairMenu.currentBuilding = currentBuilding;
    }

    public void repairTowers(Pane buildingPane) throws FileNotFoundException {
        Text text = new Text(currentBuilding.getBuildingName().getName());
        text.setStyle("-fx-font: 20 arial;");
        text.setLayoutX(250);
        text.setLayoutY(110);
        AnchorPane anchorPane = commonRepair(text);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\toweri.png");
        ImageView imageView = new ImageView(new Image(inputStream));
        imageView.setLayoutX(20);
        imageView.setLayoutY(15);
        anchorPane.getChildren().add(imageView);
        buildingPane.getChildren().clear();
        buildingPane.getChildren().addAll(anchorPane.getChildren());
    }

    public void repairGateHouse(Stage stage) throws FileNotFoundException {
        Text text = new Text("Gatehouse");
        text.setStyle("-fx-font: 30 arial;");
        text.setLayoutX(40);
        text.setLayoutY(40);
        AnchorPane anchorPane = commonRepair(text);
        Image openImage;
        Image closeImage;
        if (((Gatehouse) currentBuilding).getOpen()) {
            openImage = new Image(new FileInputStream("src\\main\\resources\\Images\\open2.png"));
            closeImage = new Image(new FileInputStream("src\\main\\resources\\Images\\close1.png"));
        } else {
            openImage = new Image(new FileInputStream("src\\main\\resources\\Images\\open1.png"));
            closeImage = new Image(new FileInputStream("src\\main\\resources\\Images\\close2.png"));
        }
        open = new Button();
        close = new Button();
        BackgroundImage openBackground = new BackgroundImage(openImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage closeBackground = new BackgroundImage(closeImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        open.setBackground(new Background(openBackground));
        close.setBackground(new Background(closeBackground));
        open.setMinSize(50, 50);
        open.setMaxSize(50, 50);
        close.setMinSize(50, 50);
        close.setMaxSize(50, 50);
        open.setLayoutX(250);
        open.setLayoutY(70);
        close.setLayoutX(300);
        close.setLayoutY(70);

        EventHandler<ActionEvent> eventOpen = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    open();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        open.setOnAction(eventOpen);
        EventHandler<ActionEvent> eventClose = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        close.setOnAction(eventClose);
        anchorPane.getChildren().addAll(open, close);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void close() throws FileNotFoundException {
        ((Gatehouse) currentBuilding).setClosed();
        Image c = new Image(new FileInputStream("src\\main\\resources\\Images\\close2.png"));
        Image o = new Image(new FileInputStream("src\\main\\resources\\Images\\open1.png"));
        close.setBackground(new Background(new BackgroundImage(c, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        open.setBackground(new Background(new BackgroundImage(o, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

    public void open() throws FileNotFoundException {
        if (!BuildingMenuController.findNearEnemy(currentBuilding.getBeginX(), currentBuilding.getBeginY())) {
            ((Gatehouse) currentBuilding).setOpened();
            //TODO set close gateHouse in map
            Image c = new Image(new FileInputStream("src\\main\\resources\\Images\\close1.png"));
            Image o = new Image(new FileInputStream("src\\main\\resources\\Images\\open2.png"));
            close.setBackground(new Background(new BackgroundImage(c, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            open.setBackground(new Background(new BackgroundImage(o, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You can not open gatehouse when enemy is near", ButtonType.CLOSE);
            alert.setTitle("Failure in opening gatehouse");
            //TODO set the picture of building
            alert.setGraphic(new ImageView(new Image(MaterialType.STONE.getPictureAddress().toExternalForm())));
            alert.setHeaderText("Enemy is near");
        }
    }

    public AnchorPane commonRepair(Text text) throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        FileInputStream input = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(input);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        RepairMenu.progressBar = new ProgressBar();
        double hp = currentBuilding.getBuildingName().getHitPoint();
        double maxHp = currentBuilding.getBuildingName().getMaxHitPoint();
        progressBar.setProgress(hp / maxHp);
        progressBar.setLayoutX(520);
        progressBar.setLayoutY(15);
        progressBar.setPrefWidth(60);
        progressBar.setPrefHeight(30);
        progressBar.setStyle("-fx-accent: #3dff2d");
        //TODO set currentBuilding.getHitPoint() / currentBuilding.getMaxHitPoint()
        Text txt = new Text(currentBuilding.getBuildingName().getHitPoint() + " / " + currentBuilding.getBuildingName().getMaxHitPoint());
        txt.setLayoutX(525);
        txt.setLayoutY(60);
        Button button = new Button("repair");
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setLayoutX(460);
        button.setLayoutY(15);
        button.setPrefHeight(30);
        button.setPrefWidth(50);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                repair();
            }
        };
        button.setOnAction(event);
        anchorPane.getChildren().addAll(txt, text, progressBar, button);

        return anchorPane;
    }

    public static void repair() {
        Outputs outputs = BuildingMenuController.repair(currentBuilding);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputs.toString(), ButtonType.CLOSE);
        alert.setTitle("repair");
        //TODO set the picture of building
        alert.setGraphic(new ImageView(new Image(MaterialType.STONE.getPictureAddress().toExternalForm())));
        if (outputs.equals(Outputs.SUCCESSFUL_REPAIR))
            alert.setHeaderText("Successful repair");
        else
            alert.setHeaderText("Failure in repair");
        alert.showAndWait();
    }

}
