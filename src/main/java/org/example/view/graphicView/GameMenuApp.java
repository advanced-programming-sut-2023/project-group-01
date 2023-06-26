package org.example.view.graphicView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.controller.NextTurn;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.controller.mainMenuController.gameMenuController.MilitaryMenuController;
import org.example.model.*;
import org.example.model.building.Building;
import org.example.model.building.FirstProducer;
import org.example.model.building.SecondProducer;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.enums.EmpireBuilding;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.Engineer;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.model.unit.enums.MilitaryUnitState;
import org.example.view.animations.ZoomAnimation;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.BuildingMenu;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.*;
import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;
import static org.example.view.mainMenu.gameMenu.GameMenu.setThisEmpire;

public class GameMenuApp extends Application {
    public static AnchorPane anchorPaneInSplitPan;
    public AnchorPane anchorPaneMain;
    public static GridPane gridPane;

    private String copyBuilding;
    private static boolean isInMenu = false;

    public static void setIsInMenu(boolean isInMenu) {
        GameMenuApp.isInMenu = isInMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        new MilitaryUnit(map.getTileWhitXAndY(3, 3), GameMenu.getEmpires().get(0),
                MilitaryUnitName.ARCHER_BOW, 3, 3);
//        new MilitaryUnit(map.getTileWhitXAndY(5, 5), GameMenu.getEmpires().get(1),
//                MilitaryUnitName.ARCHER, 5, 5);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/Map.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        anchorPaneMain = anchorPane;
        createPane(anchorPane);
        Scene scene = new Scene(anchorPane);
        gridPane.setOnMouseMoved(e -> {
            if (popup != null && popup.isShowing()) popup.hide();
            if (e.getSceneX() < 25 && startMapX > 0)
                startMapX--;
            if (e.getSceneX() > scene.getWidth() - 25 && startMapX < map.getSize() - 12)
                startMapX++;
            if (e.getSceneY() < 25 && startMapY > 0)
                startMapY--;
            if (e.getSceneY() > gridPane.getHeight() - 25 && startMapY < map.getSize() - 4)
                startMapY++;
            createPane(anchorPaneMain);
        });
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.LEFT) && startMapX > 0)
                startMapX--;
            if (e.getCode().equals(KeyCode.RIGHT) && startMapX < map.getSize() - 12)
                startMapX++;
            if (e.getCode().equals(KeyCode.UP) && startMapY > 0)
                startMapY--;
            if (e.getCode().equals(KeyCode.DOWN) && startMapY < map.getSize() - 4)
                startMapY++;
//            if (e.getCode().equals(KeyCode.X))

            if (e.getCode().equals(KeyCode.ENTER))
                nextTurn.nextTurn();
            if (!selectedImageViews.isEmpty()) {
                switch (e.getCode()) {
                    case M -> moveShortcut();
                    case A -> attackShortcut();
                    case P -> patrolShortcut();
                    case D -> defendShortcut();
                    case O -> offenciveShortcut();
                    case S -> standShortCut();
                    case T -> tunnelShortcut();
                    case U -> pourOil();
                    case TAB -> disbandShortCut();
                    case B -> buildEngine();
                    case BACK_SPACE -> setIsInMenu(true);
                }
            } else createPane(anchorPane);
        });
//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> createPane(anchorPane)));
//        timeline.setCycleCount(-1);
//        timeline.play();
        stage.setScene(scene);
        stage.show();
    }

    private void buildEngine() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        int numberOfMilitary = 0;
        for (Tile tile : tiles)
            numberOfMilitary += findNumber(tile, MilitaryUnitName.TUNNELER);
        if (numberOfMilitary != 0) {
            Text text = new Text(MilitaryUnitName.TUNNELER.getName() + " number: ");
            text.setFill(Color.WHITE);
            Spinner spinner = new Spinner<>(0, numberOfMilitary, 1);
            HBox hBox = new HBox(text, spinner);
            hBox.setSpacing(20);
            vBox.getChildren().add(hBox);
        }
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("carapult");
        strings.add("trebucher");
        strings.add("siegeTower");
        strings.add("batterningRam");
        choiceBox.setValue("portableShield");
        choiceBox.setItems((ObservableList<String>) strings);
        Button button = new Button("fireBallistra");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Spinner) {
                            int number = 0;
                            ArrayList<Engineer> engineers = new ArrayList<>();
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(MilitaryUnitName.ENGINEER)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        engineers.add((Engineer) person);
                                        number++;
                                    }
                            MilitaryMenuController.build(choiceBox.getValue(), engineers);
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        HBox hBox = new HBox(choiceBox, button);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void standShortCut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Button button = new Button("set state stand");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).getMilitaryUnitName().setState(MilitaryUnitState.DEFENSIVE);
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        vBox.getChildren().add(button);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void disbandShortCut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        int numberOfMilitary = 0;
        for (Tile tile : tiles)
            numberOfMilitary += findNumber(tile, MilitaryUnitName.TUNNELER);
        if (numberOfMilitary != 0) {
            Text text = new Text(MilitaryUnitName.TUNNELER.getName() + " number: ");
            text.setFill(Color.WHITE);
            Spinner spinner = new Spinner<>(0, numberOfMilitary, 1);
            HBox hBox = new HBox(text, spinner);
            hBox.setSpacing(20);
            vBox.getChildren().add(hBox);
        }
        Button button = new Button("disband");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() != 3)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        EmpireBuilding empireBuilding = person.getEmpire().getEmpireBuilding();
                                        MilitaryMenuController.disbandUnit(empireBuilding.getX() - 1,
                                                empireBuilding.getY() + 1, (MilitaryUnit) person);
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        HBox hBox = new HBox(button);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void pourOil() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        int numberOfMilitary = 0;
        for (Tile tile : tiles)
            numberOfMilitary += findNumber(tile, MilitaryUnitName.TUNNELER);
        if (numberOfMilitary != 0) {
            Text text = new Text(MilitaryUnitName.TUNNELER.getName() + " number: ");
            text.setFill(Color.WHITE);
            Spinner spinner = new Spinner<>(0, numberOfMilitary, 1);
            HBox hBox = new HBox(text, spinner);
            hBox.setSpacing(20);
            vBox.getChildren().add(hBox);
        }
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("up");
        strings.add("down");
        strings.add("right");
        strings.add("left");
        choiceBox.setValue("up");
        choiceBox.setItems((ObservableList<String>) strings);
        Button button = new Button("pour oil");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(MilitaryUnitName.TUNNELER)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        MilitaryMenuController.doPourOil((MilitaryUnit) person, choiceBox.getValue());
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        HBox hBox = new HBox(choiceBox, button);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void tunnelShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        int numberOfMilitary = 0;
        for (Tile tile : tiles)
            numberOfMilitary += findNumber(tile, MilitaryUnitName.TUNNELER);
        if (numberOfMilitary != 0) {
            Text text = new Text(MilitaryUnitName.TUNNELER.getName() + " number: ");
            text.setFill(Color.WHITE);
            Spinner spinner = new Spinner<>(0, numberOfMilitary, 1);
            HBox hBox = new HBox(text, spinner);
            hBox.setSpacing(20);
            vBox.getChildren().add(hBox);
        }
        Spinner spinnerTargetX = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("tunnel");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(MilitaryUnitName.TUNNELER)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        MilitaryMenuController.digTunnel((Integer) spinnerTargetX.getValue(),
                                                (Integer) spinnerTargetY.getValue(), (MilitaryUnit) person);
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        setPopup(rectangle, vBox, spinnerTargetX, spinnerTargetY, button);
    }

    private void offenciveShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Button button = new Button("set state offencive");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).getMilitaryUnitName().setState(MilitaryUnitState.OFFENSIVE);
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        vBox.getChildren().add(button);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void defendShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Button button = new Button("set state defend");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() == 2)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).getMilitaryUnitName().setState(MilitaryUnitState.DEFENSIVE);
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        vBox.getChildren().add(button);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void patrolShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Spinner spinnerTargetX1 = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY1 = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetX2 = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY2 = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("patrol");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() != 3)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).setPatrolXY((Integer) spinnerTargetX1.getValue(),
                                                (Integer) spinnerTargetY1.getValue(), (Integer) spinnerTargetX2.getValue(),
                                                (Integer) spinnerTargetY2.getValue());
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        HBox hBox = new HBox(spinnerTargetX1, spinnerTargetY1, spinnerTargetX2, spinnerTargetY2, button);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
//        selectUnitPopup.show();
    }

    private void attackShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Spinner spinnerTargetX = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("attack");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() != 3)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText());
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles)
                                for (People person : tile.getPeople())
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).setXAttack((Integer) spinnerTargetX.getValue());
                                        ((MilitaryUnit) person).setYAttack((Integer) spinnerTargetY.getValue());
                                        number++;
                                    }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        setPopup(rectangle, vBox, spinnerTargetX, spinnerTargetY, button);
    }

    private void setPopup(Rectangle rectangle, VBox vBox, Spinner spinnerTargetX, Spinner spinnerTargetY, Button button) {
        HBox hBox = gethBox(spinnerTargetX, spinnerTargetY, button);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        StackPane stackPane = new StackPane(rectangle, vBox);
        selectUnitPopup = new Popup();
        selectUnitPopup.getContent().add(stackPane);
        rectangle.setFill(Paint.valueOf("#26200354"));
        selectUnitPopup.show(gridPane.getScene().getWindow());
        rectangle.setHeight(selectUnitPopup.getHeight());
        rectangle.setWidth(selectUnitPopup.getWidth());
        selectUnitPopup.setX(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutX());
        selectUnitPopup.setY(selectedImageViews.get(selectedImageViews.size() / 2).getLayoutY());
    }

    private void setSpinersForUnits(ArrayList<Tile> tiles, VBox vBox) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findNumber(tile, militaryUnitName);
            if (number != 0) {
                Text text = new Text(militaryUnitName.getName() + " number: ");
                text.setFill(Color.WHITE);
                Spinner spinner = new Spinner<>(0, number, 1);
                HBox hBox = new HBox(text, spinner);
                hBox.setSpacing(20);
                vBox.getChildren().add(hBox);
            }
        }
        for (CatapultName catapultName : CatapultName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findCatapultNumber(tile, catapultName);
            if (number != 0) {
                Text text = new Text(catapultName.getName() + " number: ");
                text.setFill(Color.WHITE);
                Spinner spinner = new Spinner<>(0, number, 1);
                HBox hBox = new HBox(text, spinner);
                hBox.setSpacing(20);
                vBox.getChildren().add(hBox);
            }
        }
    }

    private static HBox gethBox(Spinner spinnerTargetX, Spinner spinnerTargetY, Button button) {
        HBox hBox = new HBox(spinnerTargetX, spinnerTargetY, button);
        return hBox;
    }

    int startMapX = 0;
    int startMapY = 0;
    double startSelectedTileX = 0;
    double startSelectedTileY = 0;
    public static Map map;
    public static ArrayList<Empire> empires;

    private NextTurn nextTurn;

    public GameMenuApp(Map map, ArrayList<User> players, InitializeMaterial initializeMaterial) {
        GameMenuApp.map = map;
        empires = new ArrayList<>();
        for (int i = 0; i < players.size(); i++)
            empires.add(new Empire(EmpireBuilding.valueOf("EMPIRE_" + (i + 1)), players.get(i)));
        GameMenu.setEmpires(empires);
        GameMenu.setThisEmpire(empires.get(0));
        nextTurn = new NextTurn(this);
        addEmpireBuildingsToMap();
        setMaterialForEmpires(initializeMaterial);
    }

    private void addEmpireBuildingsToMap() {
        for (int i = 1; i <= empires.size(); i++) {
            Building building = new Building(empires.get(i - 1), EmpireBuilding.valueOf("EMPIRE_" + i).getX(), EmpireBuilding.valueOf("EMPIRE_" + i).getY(), BuildingName.EMPIRE_CASTLE);
            GameMenuApp.map.getTile(EmpireBuilding.valueOf("EMPIRE_" + i).getX(), EmpireBuilding.valueOf("EMPIRE_" + i).getY()).setBuilding(building);
            empires.get(i - 1).addToBuildings(building);
            BuildingMenuController.putBuilding(BuildingName.STOCKPILE, EmpireBuilding.valueOf("EMPIRE_" + i).getX() + 1, EmpireBuilding.valueOf("EMPIRE_" + i).getY(), empires.get(i - 1));
        }
    }

    private void setMaterialForEmpires(InitializeMaterial initializeMaterial) {
        for (int i = 0; i < empires.size(); i++) {
            InitializeMaterial.setSources(empires.get(i), initializeMaterial);
        }
    }

    private final int size = 60;

    public void createPane(AnchorPane anchorPane) {
        HBox hBox = new HBox();
        try {
            Pane buildingPane = FXMLLoader.load(BuildingMenu.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml"));
            hBox.getChildren().add(buildingPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Node child : anchorPane.getChildren())
            if (child instanceof SplitPane) {
                for (Node node : ((SplitPane) child).getItems())
                    if (node instanceof GridPane)
                        gridPane = (GridPane) node;
                    else anchorPaneInSplitPan = (AnchorPane) node;
            }
        if (anchorPaneInSplitPan.getChildren().isEmpty() || isInMenu) {
            anchorPaneInSplitPan.getChildren().clear();
            anchorPaneInSplitPan.getChildren().add(hBox);
            isInMenu = false;
        }
        setManImage((HBox) anchorPaneInSplitPan.getChildren().get(0));
        setMiniMap((HBox) anchorPaneInSplitPan.getChildren().get(0));
        gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    Popup popup = new Popup();
                    Button copy = new Button("copy");
                    copy.setOnAction(e -> {
                        for (Node child : GameMenuApp.gridPane.getChildren()) {
                            if (child instanceof ImageView && ((ImageView) child).getFitHeight() == size &&
                                    child.getLayoutX() < mouseEvent.getScreenX() && mouseEvent.getScreenX() < child.getLayoutX() + size &&
                                    child.getLayoutY() < mouseEvent.getScreenY() && mouseEvent.getSceneY() < child.getLayoutY() + size) {
                                if (map.getTileWhitXAndY(GridPane.getColumnIndex(child) + startMapX,
                                        GridPane.getRowIndex(child) + startMapY).getBuilding() != null &&
                                        (BuildingName.getByAddres(((ImageView) child).getImage().getUrl()) != null ||
                                                ((ImageView) child).getImage().getUrl() == null)) {
                                    copyBuilding = map.getTileWhitXAndY(GridPane.getColumnIndex(child) + startMapX,
                                                    GridPane.getRowIndex(child) + startMapY).getBuilding().
                                            getBuildingName().getName();
                                    Clipboard clipboard = Clipboard.getSystemClipboard();
                                    ClipboardContent content = new ClipboardContent();
                                    content.putImage(new Image(map.getTileWhitXAndY(GridPane.getColumnIndex(child) + startMapX,
                                                    GridPane.getRowIndex(child) + startMapY).getBuilding().
                                            getBuildingName().getPictureAddress()));
                                    content.putUrl(map.getTileWhitXAndY(GridPane.getColumnIndex(child) + startMapX,
                                                    GridPane.getRowIndex(child) + startMapY).getBuilding().
                                            getBuildingName().getPictureAddress());
                                    clipboard.setContent(content);
                                    break;
                                }
                            }
                        }
                        popup.hide();
                    });
                    Button paste = new Button("paste");
                    paste.setOnAction(e -> {
                        if (BuildingName.getByAddres(Clipboard.getSystemClipboard().getUrl()) != null) {
                            for (Node child : GameMenuApp.gridPane.getChildren()) {
                                if (child instanceof ImageView && ((ImageView) child).getFitHeight() == size &&
                                        child.getLayoutX() < mouseEvent.getScreenX() && mouseEvent.getScreenX() < child.getLayoutX() + size &&
                                        child.getLayoutY() < mouseEvent.getScreenY() && mouseEvent.getSceneY() < child.getLayoutY() + size) {
                                    BuildingMenuController.dropBuilding(String.valueOf(GridPane.getColumnIndex(child) + startMapX),
                                            String.valueOf(GridPane.getRowIndex(child) + startMapY),
                                            BuildingName.getByAddres(Clipboard.getSystemClipboard().getUrl()).getName());
                                    break;
                                }
                            }
                        }
                        popup.hide();
                    });
                    popup.getContent().add(new VBox(copy, paste));
                    popup.setX(mouseEvent.getScreenX());
                    popup.setY(mouseEvent.getScreenY());
                    popup.show(Main.stage);
                } else if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    ArrayList<Region> regions = new ArrayList<>();
                    if (popup != null && popup.isShowing()) popup.hide();
                    for (Node child : gridPane.getChildren())
                        if (child instanceof Region) regions.add((Region) child);
                    if (popup != null && popup.isShowing()) popup.hide();
                    gridPane.getChildren().removeAll(regions);
                    startSelectedTileX = mouseEvent.getX();
                    startSelectedTileY = mouseEvent.getY();
                }
            }
        });
        ZoomAnimation zoomAnimation = new ZoomAnimation();
        gridPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double zoomFactor = 1.5;
                if (scrollEvent.getDeltaY() <= 0) {
                    zoomFactor = 1 / zoomFactor;
                }
                zoomAnimation.zoom(gridPane, zoomFactor, scrollEvent.getSceneX(), scrollEvent.getSceneY());
            }
        });
        setGridPane(gridPane, map);
    }

    private void setMiniMap(HBox hBox) {
        GridPane gridPane1 = new GridPane();
        gridPane1.setMaxWidth(160);
        gridPane1.setMaxHeight(160);
        gridPane1.setHgap(0);
        gridPane1.setVgap(0);
        for (int i = 0; i < 40; i++)
            for (int j = 0; j < 35; j++) {
                ImageView imageView = new ImageView(new Image(map.getTile(i + startMapX, j + startMapY).getTypeOfTile().getPictureAddress()));
                imageView.setFitWidth(4);
                imageView.setFitHeight(4);
                gridPane1.add(imageView, i, j);
                if (map.getTile(i + startMapX, j + startMapY).getBuilding() != null) {
                    Tile tile = map.getTile(i + startMapX, j + startMapY);
                    Building building = tile.getBuilding();
                    ImageView buildingImageView = new ImageView(new Image(building.getBuildingName().getPictureAddress()));
                    buildingImageView.setFitWidth(2);
                    buildingImageView.setFitHeight(2);
                    WritableImage writableImage = new WritableImage(new Image(building.getBuildingName().getPictureAddress()).
                            getPixelReader(),
                            (int) (buildingImageView.getImage().getWidth() *
                                    (startMapX + i - building.getBeginX()) / building.getBuildingName().getSize()),
                            (int) (buildingImageView.getImage().getHeight() * (startMapY + j - building.getBeginY()) /
                                    building.getBuildingName().getSize()), (int) buildingImageView.getFitWidth(),
                            (int) buildingImageView.getFitHeight());
                    buildingImageView.setImage(writableImage);
                    gridPane1.add(buildingImageView, i, j);
                }
                for (People person : map.getTile(i + startMapX, j + startMapY).getPeople())
                    if (person instanceof MilitaryUnit) {
                        ImageView imageView1 = new ImageView(
                                new Image(((MilitaryUnit) person).getMilitaryUnitName().getPictureAddress()));
                        imageView1.setFitWidth(1.5);
                        imageView1.setFitHeight(1.5);
                        gridPane1.add(imageView1, i, j);
                    }
            }
        if (hBox.getChildren().size() == 3)
            hBox.getChildren().remove(2);
        hBox.getChildren().add(2, gridPane1);
    }

    private void setManImage(HBox hBox) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(160);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src\\main\\resources\\Images\\popularityPhoto.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        anchorPane.setOnMouseClicked(mouseEvent -> {
            try {//TODO check
                new Rates().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        man(anchorPane);
        if (hBox.getChildren().size() == 3)
            hBox.getChildren().remove(1);
        hBox.getChildren().add(1, anchorPane);
    }

    public HBox createHbox() {
        return null;
    }

    ArrayList<ImageView> selectedImageViews = new ArrayList<>();

    public void setGridPane(GridPane gridPane, Map map) {
        gridPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) return;
                Rectangle rectangle = new Rectangle(min(startSelectedTileX, mouseEvent.getX()),
                        min(startSelectedTileY, mouseEvent.getY()),
                        abs(mouseEvent.getX() - startSelectedTileX),
                        abs(mouseEvent.getY() - startSelectedTileY));
                gridPane.getChildren().add(rectangle);
                selectedImageViews.clear();
                for (Node child : gridPane.getChildren()) {
                    if (child instanceof ImageView &&
                            child.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                        selectedImageViews.add((ImageView) child);
                    }
                }
                for (ImageView imageView : selectedImageViews) {
                    Region region = new Region();
                    region.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(0), new Insets(0))));
                    region.setOpacity(0.2);
                    gridPane.add(region, GridPane.getColumnIndex(imageView), GridPane.getRowIndex(imageView));
                }
                showDetailOfTiles(selectedImageViews);
                gridPane.getChildren().remove(rectangle);
            }
        });
        gridPane.getChildren().clear();
        for (int i = 0; i < 25; i++)
            for (int j = 0; j < 10; j++) {
                ImageView imageView = new ImageView(new Image(map.getTile(i + startMapX, j + startMapY).getTypeOfTile().getPictureAddress()));
                imageView.setFitWidth(size);
                imageView.setFitHeight(size);
                imageView.setOnDragDropped(dragEvent -> {
                    for (Node child : GameMenuApp.gridPane.getChildren()) {
                        if (child instanceof ImageView && ((ImageView) child).getFitHeight() == size &&
                                child.getLayoutX() < dragEvent.getScreenX() && dragEvent.getScreenX() < child.getLayoutX() + size &&
                                child.getLayoutY() < dragEvent.getScreenY() && dragEvent.getSceneY() < child.getLayoutY() + size) {
                            Outputs outputs = BuildingMenuController.dropBuilding(String.valueOf(GridPane.getColumnIndex(child)),
                                    String.valueOf(GridPane.getRowIndex(child)), BuildingName.getByAddres(BuildingMenuApp.
                                            getPictureAddress()).getName());
                            createPane(anchorPaneMain);
                            if (!outputs.equals(Outputs.SUCCESS)) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("put building error");
                                alert.setContentText(outputs.toString());
                                alert.showAndWait();
                            }
                            break;
                        }
                    }
                });
                imageView.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if (dragEvent.getDragboard().hasImage())
                            dragEvent.acceptTransferModes(TransferMode.ANY);
                    }
                });
                AtomicReference<Timeline> timeline = new AtomicReference<>(new Timeline());
                showDetailCheck(imageView, timeline, imageView);
                gridPane.add(imageView, i, j);
                if (map.getTile(i + startMapX, j + startMapY).getBuilding() != null) {
                    Tile tile = map.getTile(i + startMapX, j + startMapY);
                    Building building = tile.getBuilding();
                    ImageView buildingImageView = new ImageView(new Image(building.getBuildingName().getPictureAddress()));
                    showDetailCheck(buildingImageView, timeline, buildingImageView);
                    buildingImageView.setFitWidth(size);
                    buildingImageView.setFitHeight(size);
                    WritableImage writableImage = new WritableImage(new Image(building.getBuildingName().getPictureAddress()).
                            getPixelReader(),
                            (int) (buildingImageView.getImage().getWidth() *
                                    (startMapX + i - building.getBeginX()) / building.getBuildingName().getSize()),
                            (int) (buildingImageView.getImage().getHeight() * (startMapY + j - building.getBeginY()) /
                                    building.getBuildingName().getSize()), (int) buildingImageView.getFitWidth(),
                            (int) buildingImageView.getFitHeight());
                    buildingImageView.setImage(writableImage);
                    buildingImageView.setOnMouseClicked(e -> {
                        BuildingMenuApp buildingMenuApp = new BuildingMenuApp();
                        buildingMenuApp.setCurrentBuilding(building);

                        for (Node child : GameMenuApp.anchorPaneInSplitPan.getChildren()) {
                            if (child instanceof HBox) {
                                for (Node node : ((HBox) child).getChildren()) {
                                    if (node instanceof AnchorPane) {
                                        BuildingMenuApp.setBuildingPane((Pane) node);
                                        break;
                                    }
                                }
                            }
                        }
                        try {
                            buildingMenuApp.enterSelectedBuildingMenu();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    gridPane.add(buildingImageView, i, j);
                }
                for (People person : map.getTile(i + startMapX, j + startMapY).getPeople())
                    if (person instanceof MilitaryUnit) {
                        ImageView imageView1 = new ImageView(
                                new Image(((MilitaryUnit) person).getMilitaryUnitName().getPictureAddress()));
                        imageView1.setFitWidth(30);
                        imageView1.setFitHeight(30);
                        gridPane.add(imageView1, i, j);
                    }
            }
    }

    public void man(AnchorPane anchorPane) {
        int popularity = getThisEmpire().getPopularity();
        int population = getThisEmpire().getPopulation();
        int maxPopulation = getThisEmpire().getMaxPopulation();
        double gold = getThisEmpire().getGold();

        Text popularityText = new Text("" + popularity);
        popularityText.setStyle("-fx-font: 10 arial");
        if (popularity > size) popularityText.setFill(Color.GREEN);
        else if (popularity > 30) popularityText.setFill(Color.YELLOW);
        else popularityText.setFill(Color.RED);
        popularityText.setLayoutX(30);
        popularityText.setLayoutY(102);

        Text goldText = new Text(gold + "");
        goldText.setStyle("-fx-font: 10 arial");
        goldText.setFill(Color.GREEN);
        goldText.setLayoutX(30);
        goldText.setLayoutY(115);

        Text populationText = new Text(population + "\\" + maxPopulation);
        populationText.setStyle("-fx-font: 10 arial");
        populationText.setFill(Color.GREEN);
        populationText.setLayoutX(30);
        populationText.setLayoutY(130);

        anchorPane.getChildren().addAll(popularityText, goldText, populationText);
    }

    private void showDetailCheck(ImageView imageView, AtomicReference<Timeline> timeline, ImageView buildingImageView) {
        buildingImageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                timeline.set(new Timeline(new KeyFrame(Duration.seconds(3), actionEvent ->
                        showDetailOfTile(imageView))));
                timeline.get().setCycleCount(1);
                timeline.get().play();
            } else {
                timeline.get().stop();
            }
        });
    }

    private void showDetailOfTiles(ArrayList<ImageView> imageViews) {
        VBox vBox = new VBox();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : imageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        vBox.setSpacing(10);
        showUnits(tiles, vBox);
        showRates(tiles, vBox);
        Popup popup = new Popup();
        this.popup = popup;
        popup.getScene().setFill(Color.GOLD);
        popup.getContent().add(vBox);
        popup.show(gridPane.getScene().getWindow());
        if (imageViews.isEmpty()) return;
        popup.setX(imageViews.get(imageViews.size() / 2).getLayoutX());
        if (imageViews.get(imageViews.size() / 2).getLayoutY() - vBox.getHeight() < 0)
            popup.setY(imageViews.get(imageViews.size() / 2).getLayoutY() + vBox.getHeight());
        else popup.setY(imageViews.get(imageViews.size() / 2).getLayoutY() - vBox.getHeight());
    }

    private void showRates(ArrayList<Tile> tiles, VBox vBox) {
        int maxRate = 0;
        int minRate = 100;
        double sumOfRate = 0;
        int number = 0;
        for (Tile tile : tiles) {
            Building building;
            if ((building = tile.getBuilding()) != null) {
                if (building instanceof FirstProducer) {
                    number++;
                    sumOfRate += ((FirstProducer) building).getRate();
                    maxRate = max(maxRate, ((FirstProducer) building).getRate());
                    minRate = min(maxRate, ((FirstProducer) building).getRate());
                }
                if (building instanceof SecondProducer) {
                    number++;
                    sumOfRate += ((SecondProducer) building).getRate();
                    maxRate = max(maxRate, ((SecondProducer) building).getRate());
                    minRate = min(maxRate, ((SecondProducer) building).getRate());
                }
            }
        }
        if (number == 0) minRate = 0;
        vBox.getChildren().add(new Text("The min rate in tiles is " + minRate));
        vBox.getChildren().add(new Text("The max rate in tiles is " + maxRate));
        vBox.getChildren().add(new Text("The average of rate in tiles is " + sumOfRate / number));
    }

    private Popup popup;

    private void showDetailOfTile(ImageView imageView) {
        int row = GridPane.getRowIndex(imageView);
        int column = GridPane.getColumnIndex(imageView);
        VBox vBox = new VBox();
        Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
        vBox.getChildren().add(new Text("The type of tile is " + tile.getTypeOfTile()));
        vBox.setSpacing(10);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        showUnits(tiles, vBox);
        if (tile.getBuilding() != null)
            vBox.getChildren().add(new Text("The building " + tile.getBuilding().getBuildingName().name()));
        Popup popup = new Popup();
        this.popup = popup;
        popup.getScene().setFill(Color.GOLD);
        popup.getContent().add(vBox);
        popup.show(Main.stage);
        popup.setX(imageView.getLayoutX());
        popup.setY(imageView.getLayoutY());
    }

    private void showUnits(ArrayList<Tile> tiles, VBox vBox) {
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findNumber(tile, militaryUnitName);
            if (number != 0)
                vBox.getChildren().add(new Text(militaryUnitName.getName() + ": " + number));
        }
        for (CatapultName catapultName : CatapultName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findCatapultNumber(tile, catapultName);
            if (number != 0)
                vBox.getChildren().add(new Text(catapultName.getName() + ": " + number));
        }
    }

    private int findCatapultNumber(Tile tile, CatapultName catapultName) {
        int number = 0;
        for (People person : tile.getPeople())
            if (person instanceof Catapult && ((Catapult) person).getCatapultName().equals(catapultName))
                number++;
        return number;
    }

    private int findNumber(Tile tile, MilitaryUnitName militaryUnitName) {
        int number = 0;
        for (People person : tile.getPeople())
            if (person instanceof MilitaryUnit && !(person instanceof Catapult) &&
                    ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName))
                number++;
        return number;
    }

    public void graphicalMoveTroop(Tile moving, Empire empire) {
        //TODO animation of the troops
    }

    public ArrayList<MilitaryUnit> troopsOfTheEmpire(Tile tile, Empire empire) {
        ArrayList<MilitaryUnit> units = new ArrayList<>();
        for (People person : tile.getPeople())
            if (person instanceof MilitaryUnit)
                units.add((MilitaryUnit) person);
        return units;
    }


    public void moveShortcut() {
        popup.hide();
        Rectangle rectangle = new Rectangle();
        ArrayList<Tile> tiles = new ArrayList<>();
        for (ImageView imageView : selectedImageViews) {
            int row = GridPane.getRowIndex(imageView);
            int column = GridPane.getColumnIndex(imageView);
            Tile tile = map.getTileWhitXAndY(column + startMapX, row + startMapY);
            tiles.add(tile);
        }
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        setSpinersForUnits(tiles, vBox);
        Spinner spinnerTargetX = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("move");
        button.setOnAction(e -> {
            for (Node child : vBox.getChildren()) {
                MilitaryUnitName militaryUnitName = null;
                if (child instanceof HBox && ((HBox) child).getChildren().size() != 3)
                    outer:for (Node node : ((HBox) child).getChildren()) {
                        if (node instanceof Text) {
                            militaryUnitName = MilitaryUnitName.getMilitaryUnitWhitName(((Text) node).getText().replace(" number: ", ""));
                        }
                        if (node instanceof Spinner) {
                            int number = 0;
                            for (Tile tile : tiles) {
                                for (People person : tile.getPeople()) {
                                    if (person instanceof MilitaryUnit &&
                                            ((MilitaryUnit) person).getMilitaryUnitName().equals(militaryUnitName)) {
                                        if (number == (Integer) ((Spinner) node).getValue()) break outer;
                                        ((MilitaryUnit) person).setDest((Integer) spinnerTargetX.getValue(),
                                                (Integer) spinnerTargetY.getValue());
                                    }
                                }
                            }
                        }
                    }
            }
            selectUnitPopup.hide();
        });
        setPopup(rectangle, vBox, spinnerTargetX, spinnerTargetY, button);
    }

    private Popup selectUnitPopup;

    public void selectUnitsInATile() {

    }

    public void selectAGroupOfTiles() {

    }
}
