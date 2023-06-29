package org.example.view.graphicView;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
import org.example.controller.mainMenuController.gameMenuController.CreateMapMenuController;
import org.example.model.Data;
import org.example.model.Map;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.Tile;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;
import org.example.view.enums.Outputs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class SetTexture extends Application {
    private static TypeOfTile typeOfTile;
    private static BuildingName buildingName;
    private static MilitaryUnitName militaryUnitName;
    private static CatapultName catapultName;
    private int startMapX = 0;
    private int startMapY = 0;
    private Map map = new Map(200);
    private int size = 60;
    private static boolean isClearState = false;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenuApp.map = map;
        AnchorPane anchorPane = new AnchorPane();
        GridPane gridPane = new GridPane();
        setGridPane(gridPane);
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefWidth(600);
        anchorPane2.setPrefHeight(160);
        anchorPane2.setBackground(background);
        addCommon(anchorPane, anchorPane2);
        Button button = new Button("save");
        Button buttonExit = new Button("exit");
        buttonExit.setOnAction(actionEvent -> {
            try {
                new GameSettingMenu().start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setOnAction(actionEvent -> {
            Popup popup = new Popup();
            TextField textField = new TextField();
            textField.setPromptText("map name");
            Button button1 = new Button("cancel");
            button1.setOnAction(actionEvent1 -> popup.hide());
            Button button2 = new Button("save");
            button2.setOnAction(actionEvent1 -> {
                Outputs output = CreateMapMenuController.checkSaveMap(textField.getText());
                if (output.equals(Outputs.SUCCESS)) {
                    try {
                        Data.getStayedLoggedIn().addMap(textField.getText(), map);
                        new GameSettingMenu().start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("save map error");
                    alert.setContentText(output.toString());
                    alert.showAndWait();
                }
            });
            HBox hBox = new HBox(button1, button2);
            hBox.setSpacing(20);
            VBox vBox = new VBox(textField, hBox);
            vBox.setSpacing(20);
            popup.getContent().add(vBox);
            popup.show(Main.stage);
            popup.setY(Main.stage.getHeight() / 2);
            popup.setX(Main.stage.getWidth() / 2);
        });
        VBox vBox = new VBox(button, buttonExit);
        vBox.setSpacing(20);
        HBox hBox = new HBox(anchorPane, new ScrollPane(anchorPane2), vBox);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        SplitPane splitPane = new SplitPane(gridPane, hBox);
        Scene scene = new Scene(splitPane, 1530, 800);
        gridPane.setOnMouseMoved(e -> {
            if (e.getSceneX() < 25 && startMapX > 0)
                startMapX--;
            if (e.getSceneX() > scene.getWidth() - 25 && startMapX < map.getSize() - 12)
                startMapX++;
            if (e.getSceneY() < 25 && startMapY > 0)
                startMapY--;
            if (e.getSceneY() > gridPane.getHeight() - 25 && startMapY < map.getSize() - 4)
                startMapY++;
            setGridPane(gridPane);
        });
        stage.setScene(scene);
        splitPane.setOrientation(Orientation.VERTICAL);
        stage.show();
    }

    private void setGridPane(GridPane gridPane) {
        for (int i = 0; i < 25; i++)
            for (int j = 0; j < 10; j++) {
                ImageView imageView = new ImageView(new Image(map.getTile(i + startMapX, j + startMapY).getTypeOfTile().getPictureAddress()));
                imageView.setFitWidth(size);
                imageView.setFitHeight(size);
                imageView.setOnMouseClicked(mouseEvent -> {
                    for (Node child : gridPane.getChildren()) {
                        if (child instanceof ImageView && ((ImageView) child).getFitHeight() == size &&
                                child.getLayoutX() < mouseEvent.getScreenX() && mouseEvent.getScreenX() < child.getLayoutX() + size &&
                                child.getLayoutY() < mouseEvent.getScreenY() && mouseEvent.getSceneY() < child.getLayoutY() + size) {
                            Tile tile = map.getTile(GridPane.getColumnIndex(child) + startMapX,
                                    GridPane.getRowIndex(child) + startMapY);
                            if (tile.getBuilding() == null) {
                                if (typeOfTile != null)
                                    tile.setTypeOfTile(typeOfTile);
                                if (buildingName != null)
                                    BuildingMenuController.putBuilding(buildingName, GridPane.getColumnIndex(child) + startMapX,
                                            GridPane.getRowIndex(child) + startMapY, null);
                                if (militaryUnitName != null)
                                    new MilitaryUnit(tile, null, militaryUnitName, GridPane.getColumnIndex(child) + startMapX,
                                            GridPane.getRowIndex(child) + startMapY);
                                if (catapultName != null)
                                    new Catapult(tile, null, GridPane.getColumnIndex(child) + startMapX,
                                            GridPane.getRowIndex(child) + startMapY, catapultName);
                            } else if (isClearState) {
                                Building building = tile.getBuilding();
                                for (int x = building.getBeginX(); x <= building.getEndX(); x++)
                                    for (int y = building.getBeginY(); y <= building.getEndY(); y++) {
                                        map.getTileWhitXAndY(x, y).setBuilding(null);
                                    }
                            }
                        }
                    }
                    setGridPane(gridPane);
                });
                gridPane.add(imageView, i, j);
                if (map.getTile(i + startMapX, j + startMapY).getBuilding() != null) {
                    Tile tile = map.getTile(i + startMapX, j + startMapY);
                    Building building = tile.getBuilding();
                    ImageView buildingImageView = new ImageView(new Image(building.getBuildingName().getPictureAddress()));
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
                    buildingImageView.setOnMouseClicked(mouseEvent -> {
                        for (int x = building.getBeginX(); x <= building.getEndX(); x++)
                            for (int y = building.getBeginY(); y <= building.getEndY(); y++) {
                                map.getTileWhitXAndY(x, y).setBuilding(null);
                            }
                        setGridPane(gridPane);
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

    //TODO set address for texture قشنگ کردن دکمه ها
    public void resetAllNull() {
        buildingName = null;
        militaryUnitName = null;
        catapultName = null;
        typeOfTile = null;
    }

    public void addCommon(AnchorPane anchorPane, AnchorPane anchorPane2) throws FileNotFoundException {
        Button groundButton = createButton("ground", 20, 20);
        groundButton.setOnAction(actionEvent -> {
            try {
                setGround(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button waterButton = createButton("water", 80, 20);
        waterButton.setOnAction(actionEvent -> {
            try {
                setWater(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button treeButton = createButton("tree", 20, 60);
        treeButton.setOnAction(actionEvent -> {
            try {
                dropTree(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button rockButton = createButton("rock", 80, 60);
        rockButton.setOnAction(actionEvent -> {
            try {
                dropRock(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button unitButton = createButton("unit", 20, 100);
        unitButton.setOnAction(actionEvent -> {
            try {
                openUnitMenu(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button buildingButton = createButton("building", 80, 100);
        buildingButton.setOnAction(actionEvent -> {
            try {
                openBuildingMenu(anchorPane2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button clearButton = createButton("clear", 50, 130);
        clearButton.setOnAction(actionEvent -> {
            clear();
        });

        anchorPane.getChildren().addAll(groundButton, waterButton, treeButton,
                rockButton, unitButton, buildingButton, clearButton);
    }

    public Button createButton(String name, int x, int y) {
        Button button = new Button(name);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(60);
        return button;
    }

    public void setGround(AnchorPane anchorPane) throws FileNotFoundException {
        removeAdditional(anchorPane);

        Rectangle normalGround = createRectangleForGround(TypeOfTile.NORMAL_GROUND.getPictureAddress(), 100, 30);
        normalGround.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.NORMAL_GROUND;
        });

        Rectangle gravelGround = createRectangleForGround(TypeOfTile.GRAVEL_GROUND.getPictureAddress(), 200, 30);
        gravelGround.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.GRAVEL_GROUND;
        });

        Rectangle stoneMine = createRectangleForGround(TypeOfTile.STONE_MINE.getPictureAddress(), 300, 30);
        stoneMine.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.STONE_MINE;
        });

        Rectangle ironMine = createRectangleForGround(TypeOfTile.IRON_MINE.getPictureAddress(), 400, 30);
        ironMine.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.IRON_MINE;
        });

        Rectangle grassLand = createRectangleForGround(TypeOfTile.GRASSLAND.getPictureAddress(), 500, 30);
        grassLand.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.GRASSLAND;
        });

        Rectangle meadow = createRectangleForGround(TypeOfTile.MEADOW.getPictureAddress(), 150, 90);
        meadow.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.MEADOW;
        });

        Rectangle fullMeadow = createRectangleForGround(TypeOfTile.FULL_MEADOW.getPictureAddress(), 250, 90);
        fullMeadow.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.FULL_MEADOW;
        });

        Rectangle oilGround = createRectangleForGround(TypeOfTile.OIL_GROUND.getPictureAddress(), 350, 90);
        oilGround.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.OIL_GROUND;
        });

        Rectangle plain = createRectangleForGround(TypeOfTile.PLAIN.getPictureAddress(), 450, 90);
        plain.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.PLAIN;
        });

        anchorPane.getChildren().addAll(normalGround, gravelGround, stoneMine, ironMine, grassLand, meadow, fullMeadow, oilGround, plain);
    }

    public Rectangle createRectangleForGround(String inputStream, int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(50);
        rectangle.setWidth(50);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        rectangle.setFill(new ImagePattern(new Image(inputStream)));
        return rectangle;
    }

    public void dropRock(AnchorPane anchorPane) throws FileNotFoundException {
        removeAdditional(anchorPane);
        Rectangle wRockRectangle = createRectangleForRock(TypeOfTile.W_ROCK.getPictureAddress(), 150, 50);
        wRockRectangle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.W_ROCK;
        });
        Rectangle eRockRectangle = createRectangleForRock(TypeOfTile.E_ROCK.getPictureAddress(), 250, 50);
        eRockRectangle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.E_ROCK;
        });
        Rectangle nRockRectangle = createRectangleForRock(TypeOfTile.N_ROCK.getPictureAddress(), 350, 50);
        nRockRectangle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.N_ROCK;
        });
        Rectangle sRockRectangle = createRectangleForRock(TypeOfTile.S_ROCK.getPictureAddress(), 450, 50);
        sRockRectangle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.S_ROCK;
        });

        anchorPane.getChildren().addAll(wRockRectangle, eRockRectangle, nRockRectangle, sRockRectangle);
    }

    public Rectangle createRectangleForRock(String inputStream, int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(60);
        rectangle.setWidth(60);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        rectangle.setFill(new ImagePattern(new Image(inputStream)));
        return rectangle;
    }

    public void dropTree(AnchorPane anchorPane) throws FileNotFoundException {
        removeAdditional(anchorPane);
        Rectangle desertShrub = createRectangleForGround(BuildingName.desertShrub.getPictureAddress(), 50, 50);
        desertShrub.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.desertShrub;
        });

        Rectangle cherryPalm = createRectangleForGround(BuildingName.cherryPalm.getPictureAddress(), 150, 50);
        cherryPalm.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.cherryPalm;
        });

        Rectangle oliveTree = createRectangleForGround(BuildingName.oliveTree.getPictureAddress(), 250, 50);
        oliveTree.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.oliveTree;
        });

        Rectangle coconutPalm = createRectangleForGround(BuildingName.coconutPalm.getPictureAddress(), 350, 50);
        coconutPalm.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.coconutPalm;
        });

        Rectangle palmTree = createRectangleForGround(BuildingName.palmTree.getPictureAddress(), 450, 50);
        palmTree.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.palmTree;
        });

        anchorPane.getChildren().addAll(desertShrub, cherryPalm, oliveTree, coconutPalm, palmTree);
    }

    public void setWater(AnchorPane anchorPane) throws FileNotFoundException {
        removeAdditional(anchorPane);
        Rectangle shallowWater = createRectangleForGround(TypeOfTile.SHALLOW_WATER.getPictureAddress(), 50, 50);
        shallowWater.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.SHALLOW_WATER;
        });

        Rectangle river = createRectangleForGround(TypeOfTile.RIVER.getPictureAddress(), 130, 50);
        river.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.RIVER;
        });

        Rectangle smallPond = createRectangleForGround(TypeOfTile.SMALL_POND.getPictureAddress(), 210, 50);
        smallPond.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.SMALL_POND;
        });

        Rectangle bigPond = createRectangleForGround(TypeOfTile.BIG_POND.getPictureAddress(), 290, 50);
        bigPond.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.BIG_POND;
        });

        Rectangle beach = createRectangleForGround(TypeOfTile.BEACH.getPictureAddress(), 370, 50);
        beach.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.BEACH;
        });

        Rectangle sea = createRectangleForGround(TypeOfTile.SEA.getPictureAddress(), 450, 50);
        sea.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            typeOfTile = TypeOfTile.SEA;
        });
        anchorPane.getChildren().addAll(shallowWater, river, smallPond, bigPond, beach, sea);
    }

    public void removeAdditional(AnchorPane anchorPane) {
        anchorPane.getChildren().clear();
    }

    public void openUnitMenu(AnchorPane anchorPane) throws FileNotFoundException {
        anchorPane.getChildren().clear();
        ArrayList<MilitaryUnitName> militaryUnitNames = new ArrayList<>(Arrays.asList(MilitaryUnitName.values()));
        militaryUnitNames.remove(0);
        ArrayList<CatapultName> catapultNames = new ArrayList<>(Arrays.asList(CatapultName.values()));
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 3; j++)
                createCircle(anchorPane, i * 100 + 50, j * 100 + 50, militaryUnitNames.get(i * 3 + j).getPictureAddress());

        for (int i = 0; i < 6; i++)
            createCircleForCatapult(anchorPane, i * 100 + 50, 350, catapultNames.get(i).getPictureAddress());

    }

    public void createCircle(AnchorPane anchorPane, int x, int y, String pictureAddress) throws FileNotFoundException {
        Circle circle = new Circle(x, y, 50);
        circle.setStyle("-fx-background-color: #1ea1ff");
        circle.setFill(new ImagePattern(new Image(pictureAddress)));
        circle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            militaryUnitName = MilitaryUnitName.getUnitByPictureAddress(pictureAddress);
        });
        anchorPane.getChildren().add(circle);
    }

    public void createCircleForCatapult(AnchorPane anchorPane, int x, int y, String pictureAddress) throws FileNotFoundException {
        Circle circle = new Circle(x, y, 50);
        circle.setStyle("-fx-background-color: #1ea1ff");
        circle.setFill(new ImagePattern(new Image(pictureAddress)));
        circle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            catapultName = CatapultName.getCatapultByPictureAddress(pictureAddress);
        });
        anchorPane.getChildren().add(circle);
    }

    public void openBuildingMenu(AnchorPane anchorPane) throws FileNotFoundException {
        anchorPane.getChildren().clear();
        ArrayList<BuildingName> buildingNames = new ArrayList<>(Arrays.asList(BuildingName.values()));
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++)
                createCircleForBuilding(anchorPane, i * 80 + 40, j * 80 + 40,
                        buildingNames.get(i * 5 + j).getPictureAddress());
    }

    public void createCircleForBuilding(AnchorPane anchorPane, int x, int y, String pictureAddress) throws FileNotFoundException {
        Circle circle = new Circle(x, y, 50);
        circle.setStyle("-fx-background-color: #1ea1ff");
        circle.setFill(new ImagePattern(new Image(pictureAddress)));
        circle.setOnMouseClicked(mouseEvent -> {
            resetAllNull();
            buildingName = BuildingName.getByAddress(pictureAddress);
        });
        anchorPane.getChildren().add(circle);
    }

    public void clear() {
        isClearState = !isClearState;
    }

}
