package org.example.view.graphicView;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.mainMenuController.gameMenuController.BuildingMenuController;
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
        AnchorPane anchorPane = new AnchorPane();
        GridPane gridPane = new GridPane();
        setGridPane(gridPane);
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        addCommon(anchorPane);
        Button button = new Button("save");
        HBox hBox = new HBox(anchorPane, button);
        button.setOnAction(actionEvent -> {
            try {
                Data.getStayedLoggedIn().addMap(map);
                new GameSettingMenu().start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        SplitPane splitPane = new SplitPane(gridPane, hBox);
        Scene scene = new Scene(splitPane);
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

    public void addCommon(AnchorPane anchorPane) throws FileNotFoundException {
        Button groundButton = createButton("ground", 20, 20);
        groundButton.setOnAction(actionEvent -> {
            try {
                setGround(anchorPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button waterButton = createButton("water", 80, 20);
        waterButton.setOnAction(actionEvent -> {
            try {
                setWater(anchorPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button treeButton = createButton("tree", 20, 60);
        treeButton.setOnAction(actionEvent -> {
            try {
                dropTree(anchorPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button rockButton = createButton("rock", 80, 60);
        rockButton.setOnAction(actionEvent -> {
            try {
                dropRock(anchorPane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button unitButton = createButton("unit", 20, 100);
        unitButton.setOnAction(actionEvent -> {
            try {
                openUnitMenu();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button buildingButton = createButton("building", 80, 100);
        buildingButton.setOnAction(actionEvent -> {
            try {
                openBuildingMenu();
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
        ArrayList<Node> nodes = new ArrayList<>();
        int i = 7;
        while (i < anchorPane.getChildren().size()) {
            nodes.add(anchorPane.getChildren().get(i));
            i++;
        }
        anchorPane.getChildren().removeAll(nodes);
    }

    public void openUnitMenu() throws FileNotFoundException {
        ArrayList<MilitaryUnitName> militaryUnitNames = new ArrayList<>(Arrays.asList(MilitaryUnitName.values()));
        militaryUnitNames.remove(0);
        ArrayList<CatapultName> catapultNames = new ArrayList<>(Arrays.asList(CatapultName.values()));

        AnchorPane anchorPane = createAnchorPane();
        //TODO set pictures
        //TODO shop menu has some bugs احتمالا مال اف ایکس ام ال هست
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 3; j++)
                createCircle(anchorPane, i * 100 + 150, j * 100 + 150, militaryUnitNames.get(i * 6 + j).getPictureAddress());

        for (int i = 0; i < 6; i++)
            createCircleForCatapult(anchorPane, i * 100 + 150, 450, catapultNames.get(i).getPictureAddress());

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

    public void openBuildingMenu() throws FileNotFoundException {
        ArrayList<BuildingName> buildingNames = new ArrayList<>(Arrays.asList(BuildingName.values()));
        AnchorPane anchorPane = createAnchorPane();
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++)
                createCircleForBuilding(anchorPane, i * 80 + 40, j * 80 + 40,
                        buildingNames.get(i * 9 + j).getPictureAddress());
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

    public AnchorPane createAnchorPane() throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(600);
        anchorPane.setPrefWidth(800);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\mb.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        return anchorPane;
    }

    public void clear() {
        isClearState = !isClearState;
    }

}
