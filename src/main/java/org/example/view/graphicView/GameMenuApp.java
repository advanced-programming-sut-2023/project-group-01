package org.example.view.graphicView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.controller.NextTurn;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.model.Data;
import org.example.model.Empire;
import org.example.model.Map;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.FirstProducer;
import org.example.model.building.SecondProducer;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.enums.EmpireBuilding;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
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
import static org.example.view.mainMenu.gameMenu.GameMenu.*;

public class GameMenuApp extends Application {
    public static AnchorPane anchorPaneInSplitPan;
    public AnchorPane anchorPaneMain;
    public static GridPane gridPane;
    private static boolean isOnDestroyState = false;
    private static Building buildingForUndo;
    private static boolean optionOpen = false;

    @Override
    public void start(Stage stage) throws Exception {
//        Building building = new Building(null, 5, 5, BuildingName.INN);
//        for (int i = 5; i < 8; i++)
//            for (int j = 5; j < 8; j++)
//                map.getTileWhitXAndY(i, j).setBuilding(building);
        Empire empire = new Empire(EmpireBuilding.EMPIRE_1, Data.getStayedLoggedIn());
        Empire empire2 = new Empire(EmpireBuilding.EMPIRE_2, Data.findUserWithUsername("morteza"));
        GameMenu.getEmpires().add(empire);
        GameMenu.getEmpires().add(empire2);
        GameMenu.setThisEmpire(GameMenu.getEmpires().get(0));
        new MilitaryUnit(map.getTileWhitXAndY(3, 3), GameMenu.getThisEmpire(),
                MilitaryUnitName.ARCHER, 3, 3);
        new MilitaryUnit(map.getTileWhitXAndY(5, 5), GameMenu.getEmpires().get(1),
                MilitaryUnitName.ARCHER, 5, 5);
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
            if (e.getCode().equals(KeyCode.X))

//            if (e.getCode().equals(KeyCode.ENTER)) {
//                nextTurn.nextTurn();
//            }
                if (!selectedImageViews.isEmpty()) {
                    switch (e.getCode()) {
                        case M -> moveShortcut();
                        case A -> attackShortcut();
//                    case P -> patrolShortcut();
                    }
                } else createPane(anchorPane);
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), actionEvent -> createPane(anchorPane)));
        timeline.setCycleCount(-1);
        timeline.play();
        stage.setScene(scene);
        stage.show();
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
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findNumber(tile, militaryUnitName);
            if (number != 0) {
                Text text = new Text(militaryUnitName.getName() + " number: ");
                text.setFill(Color.WHITE);
                Spinner spinner = new Spinner<>(1, number, 1);
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
                Spinner spinner = new Spinner<>(1, number, 1);
                HBox hBox = new HBox(text, spinner);
                hBox.setSpacing(20);
                vBox.getChildren().add(hBox);
            }
        }
        Spinner spinnerTargetX = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("attack");
        button.setOnAction(e -> {
            for (People person : tiles.get(0).getPeople()) {
                if (person instanceof MilitaryUnit) {
                    ((MilitaryUnit) person).setXAttack((Integer) spinnerTargetX.getValue());
                    ((MilitaryUnit) person).setYAttack((Integer) spinnerTargetY.getValue());
                }
            }
            selectUnitPopup.hide();
            NextTurn nextTurn = new NextTurn(this);
            nextTurn.nextTurn();
        });
        HBox hBox = new HBox(spinnerTargetX, spinnerTargetY, button);
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

    boolean select = false;
    int startMapX = 0;
    int startMapY = 0;
    double startSelectedTileX = 0;
    double startSelectedTileY = 0;
    public static Map map;

    public GameMenuApp(Map map) {
        GameMenuApp.map = map;
    }

    public void createPane(AnchorPane anchorPane) {

//        Pane pane = new Pane(gridPane1);
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
        if (anchorPaneInSplitPan.getChildren().size() == 0)
            anchorPaneInSplitPan.getChildren().add(hBox);

        setManImage(hBox);
        //TODO set this
        VBox vBox = createOptions();
        hBox.getChildren().add(vBox);

//        setMiniMap(hBox);
//        hBox.getChildren().add(gridPane1);

        //        FXMLLoader fxmlLoader1 = new FXMLLoader(Main.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml"));
//        try {
//  //          AnchorPane anchorPane1 = fxmlLoader1.load();
//    ///        anchorPane1.getChildren().add(pane);
//        this.anchorPaneInSplitPan.getChildren().add(gridPane1);
//        gridPane1.setMaxHeight(anchorPaneInSplitPan.getHeight());
//        gridPane1.setMaxWidth(anchorPaneInSplitPan.getWidth());
//        gridPane1.setLayoutY(anchorPaneInSplitPan.getWidth() / 2);
//        gridPane1.setLayoutX(anchorPaneInSplitPan.getHeight() / 2);
//        anchorPaneInSplitPan.getChildren().add(gridPane1);
//        pane.setLayoutX(400);
//        pane.setLayoutY(300);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Region> regions = new ArrayList<>();
                if (popup != null && popup.isShowing()) popup.hide();
                for (Node child : gridPane.getChildren())
                    if (child instanceof Region) regions.add((Region) child);
                if (popup != null && popup.isShowing()) popup.hide();
                gridPane.getChildren().removeAll(regions);
                startSelectedTileX = mouseEvent.getX();
                startSelectedTileY = mouseEvent.getY();
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
        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 70; j++) {
                ImageView imageView = new ImageView(new Image(map.getTile(i + startMapX, j + startMapY).getTypeOfTile().getPictureAddress()));
                imageView.setFitWidth(2);
                imageView.setFitHeight(2);
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
//        gridPane1.setScaleX(0.1);
        //      gridPane1.setScaleY(0.1);
        hBox.getChildren().add(gridPane1);
    }

    private VBox createOptions() {
        int size = 15;
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Circle option = new Circle(size);
        setPicture(option, "src\\main\\resources\\Images\\options\\setting.png");
        option.setOnMouseClicked(mouseEvent -> {
            if (!optionOpen) {
                optionOpen = true;
                option();
            }
        });

        Circle briefing = new Circle(size);
        setPicture(briefing, "src\\main\\resources\\Images\\options\\info.png");
        briefing.setOnMouseClicked(mouseEvent -> {
            //TODO
            try {
                briefing();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
//            zxfgkulkgdfhgjghjhjkhjlkjljkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
        });

        Circle delete = new Circle(size);
        setPicture(delete, "src\\main\\resources\\Images\\options\\destroy.jpg");
        delete.setOnMouseClicked(mouseEvent -> {
            isOnDestroyState = !isOnDestroyState;
            //TODO change mouse cursor
        });//TODO وقتی میزنه روش دوباره بزنه دیستروی میره

        Circle undo = new Circle(size);
        setPicture(undo, "src\\main\\resources\\Images\\options\\undo.png");
        undo.setOnMouseClicked(mouseEvent -> {
            //TODO check
            if (buildingForUndo != null) {
                Outputs outputs = BuildingMenuController.destroyBuilding(buildingForUndo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, outputs.toString(), ButtonType.CLOSE);
                alert.setTitle("destroying");
                alert.setGraphic(new ImageView(new Image(buildingForUndo.getBuildingName().getPictureAddress())));
                if (outputs.equals(Outputs.SUCCESSFUL_DESTROY_BUILDING))
                    alert.setHeaderText("Successful destroy");
                else
                    alert.setHeaderText("Failure in destroying");
                alert.showAndWait();
            }
            //reload map
        });

        vBox.getChildren().addAll(option, briefing, delete, undo);
        return vBox;
    }

    private void briefing() throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(668);
        anchorPane.setPrefHeight(500);

        VBox vBox = new VBox();
        vBox.setPrefHeight(400);
        vBox.setPrefWidth(200);
        vBox.setLayoutX(400);
        vBox.setLayoutY(100);
        vBox.setSpacing(20);

        Image image = new Image(new FileInputStream("src\\main\\resources\\Images\\options\\mission.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);

        Text txt = new Text("You should defeat this empire to win : ");
        txt.setFill(Color.RED);
        txt.setStyle("-fx-font: 15 arial;");
        vBox.getChildren().add(txt);
        int counter = 1;
        for (Empire empire : getEmpires()) {
            if (!empire.equals(getThisEmpire())) {
                Text text = new Text("        " + counter + "." + empire.getPlayer().getNickname());
                text.setFill(Color.RED);
                text.setStyle("-fx-font: 15 arial;");
                counter++;
                vBox.getChildren().add(text);
            }
        }
        anchorPane.getChildren().add(vBox);
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

//    sghfgjhkjgfdxzfxhcgjkjljhkjgfdsasadfghjhkljjheasrytuyiuoiyuytreaa
    private void option() {
        //TODO close stages
        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setPrefHeight(500);
        vBox.setPrefWidth(500);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        Button resume = createButton("resume");
        resume.setOnAction(actionEvent -> {
            optionOpen = false;
            stage.close();
        });

        Button quit = createButton("quit");
        quit.setOnAction(actionEvent -> {
            Main main = new Main();
            try {
                main.start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            optionOpen = false;
        });

        Button exit = createButton("exit");
        exit.setOnAction(actionEvent -> {
            Main.stage.close();
            stage.close();
            optionOpen = false;
            //TODO check
        });

        vBox.getChildren().addAll(resume, quit, exit);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public Button createButton(String name) {
        Button button = new Button(name);
        button.setPrefWidth(300);
        return button;
    }

    private void setPicture(Circle circle, String pictureAddress) {
        try {
            circle.setFill(new ImagePattern(new Image(
                    new FileInputStream(pictureAddress))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        hBox.getChildren().add(anchorPane);
    }

    public HBox createHbox() {
        return null;
    }

    ArrayList<ImageView> selectedImageViews = new ArrayList<>();

    public void setGridPane(GridPane gridPane, Map map) {
        gridPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
        for (int i = 0; i < 75; i++)
            for (int j = 0; j < 30; j++) {
                ImageView imageView = new ImageView(new Image(map.getTile(i + startMapX, j + startMapY).getTypeOfTile().getPictureAddress()));
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                imageView.setOnDragDropped(dragEvent -> {
                    Image img = dragEvent.getDragboard().getImage();
                    for (Node child : GameMenuApp.gridPane.getChildren()) {
                        if (child instanceof ImageView && ((ImageView) child).getFitHeight() == 20 &&
                                child.getLayoutX() < dragEvent.getScreenX() && dragEvent.getScreenX() < child.getLayoutX() + 20 &&
                                child.getLayoutY() < dragEvent.getScreenY() && dragEvent.getSceneY() < child.getLayoutY() + 20) {

                            ((ImageView) child).setImage(img);
                            GameMenuApp.map.getTile(GridPane.getColumnIndex(child), GridPane.getRowIndex(child)).setBuilding(new Building
                                    (null, GridPane.getColumnIndex(child), GridPane.getRowIndex(child), BuildingName.INN));
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
                    buildingImageView.setFitWidth(20);
                    buildingImageView.setFitHeight(20);
                    WritableImage writableImage = new WritableImage(new Image(building.getBuildingName().getPictureAddress()).
                            getPixelReader(),
                            (int) (buildingImageView.getImage().getWidth() *
                                    (startMapX + i - building.getBeginX()) / building.getBuildingName().getSize()),
                            (int) (buildingImageView.getImage().getHeight() * (startMapY + j - building.getBeginY()) /
                                    building.getBuildingName().getSize()), (int) buildingImageView.getFitWidth(),
                            (int) buildingImageView.getFitHeight());
                    buildingImageView.setImage(writableImage);
                    gridPane.add(buildingImageView, i, j);
                }
                for (People person : map.getTile(i + startMapX, j + startMapY).getPeople())
                    if (person instanceof MilitaryUnit) {
                        ImageView imageView1 = new ImageView(
                                new Image(((MilitaryUnit) person).getMilitaryUnitName().getPictureAddress()));
                        imageView1.setFitWidth(15);
                        imageView1.setFitHeight(15);
                        gridPane.add(imageView1, i, j);
                    }
            }
    }

    public void man(AnchorPane anchorPane) {
        Empire empire = new Empire(null, null);
        setThisEmpire(empire);
        int popularity = getThisEmpire().getPopularity();
        int population = getThisEmpire().getPopulation();
        int maxPopulation = getThisEmpire().getMaxPopulation();
        double gold = getThisEmpire().getGold();

        Text popularityText = new Text("" + popularity);
        popularityText.setStyle("-fx-font: 10 arial");
        if (popularity > 60) popularityText.setFill(Color.GREEN);
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
        for (MilitaryUnitName militaryUnitName : MilitaryUnitName.values()) {
            int number = 0;
            for (Tile tile : tiles)
                number += findNumber(tile, militaryUnitName);
            if (number != 0) {
                Text text = new Text(militaryUnitName.getName() + " number: ");
                text.setFill(Color.WHITE);
                Spinner spinner = new Spinner<>(1, number, 1);
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
                Spinner spinner = new Spinner<>(1, number, 1);
                HBox hBox = new HBox(text, spinner);
                hBox.setSpacing(20);
                vBox.getChildren().add(hBox);
            }
        }
        Spinner spinnerTargetX = new Spinner<>(0, map.getSize(), 0);
        Spinner spinnerTargetY = new Spinner<>(0, map.getSize(), 0);
        Button button = new Button("move");
        button.setOnAction(e -> {
            for (People person : tiles.get(0).getPeople()) {
                if (person instanceof MilitaryUnit) {
                    ((MilitaryUnit) person).setDest((Integer) spinnerTargetX.getValue(), (Integer) spinnerTargetY.getValue());
                }
            }
            selectUnitPopup.hide();
            NextTurn nextTurn = new NextTurn(this);
            nextTurn.nextTurn();
        });
        HBox hBox = new HBox(spinnerTargetX, spinnerTargetY, button);
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

    private Popup selectUnitPopup;

    public void selectUnitsInATile() {

    }

    public void selectAGroupOfTiles() {

    }
}
