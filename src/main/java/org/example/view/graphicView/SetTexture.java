package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.TypeOfTile;
import org.example.model.unit.CatapultName;
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
    private static boolean isClearState = false;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        addCommon(anchorPane);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    //TODO set address for texture قشنگ کردن دکمه ها

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

        Rectangle normalGround = createRectangleForGround(
                new FileInputStream(TypeOfTile.NORMAL_GROUND.getPictureAddress()), 100, 30);
        normalGround.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.NORMAL_GROUND;
        });

        Rectangle gravelGround = createRectangleForGround(
                new FileInputStream(TypeOfTile.GRAVEL_GROUND.getPictureAddress()), 200, 30);
        gravelGround.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.GRAVEL_GROUND;
        });

        Rectangle stoneMine = createRectangleForGround(
                new FileInputStream(TypeOfTile.STONE_MINE.getPictureAddress()), 300, 30);
        stoneMine.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.STONE_MINE;
        });

        Rectangle ironMine = createRectangleForGround(
                new FileInputStream(TypeOfTile.IRON_MINE.getPictureAddress()), 400, 30);
        ironMine.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.IRON_MINE;
        });

        Rectangle grassLand = createRectangleForGround(
                new FileInputStream(TypeOfTile.GRASSLAND.getPictureAddress()), 500, 30);
        grassLand.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.GRASSLAND;
        });

        Rectangle meadow = createRectangleForGround(
                new FileInputStream(TypeOfTile.MEADOW.getPictureAddress()), 150, 90);
        meadow.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.MEADOW;
        });

        Rectangle fullMeadow = createRectangleForGround(
                new FileInputStream(TypeOfTile.FULL_MEADOW.getPictureAddress()), 250, 90);
        fullMeadow.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.FULL_MEADOW;
        });

        Rectangle oilGround = createRectangleForGround(
                new FileInputStream(TypeOfTile.OIL_GROUND.getPictureAddress()), 350, 90);
        oilGround.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.OIL_GROUND;
        });

        Rectangle plain = createRectangleForGround(
                new FileInputStream(TypeOfTile.PLAIN.getPictureAddress()), 450, 90);
        plain.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.PLAIN;
        });

        anchorPane.getChildren().addAll(normalGround, gravelGround, stoneMine, ironMine, grassLand, meadow, fullMeadow, oilGround, plain);
    }

    public Rectangle createRectangleForGround(FileInputStream inputStream, int x, int y) {
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
        Rectangle wRockRectangle = createRectangleForRock(new FileInputStream(TypeOfTile.W_ROCK.getPictureAddress()), 150, 50);
        wRockRectangle.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.W_ROCK;
        });

        Rectangle eRockRectangle = createRectangleForRock(new FileInputStream(TypeOfTile.E_ROCK.getPictureAddress()), 250, 50);
        eRockRectangle.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.E_ROCK;
        });

        Rectangle nRockRectangle = createRectangleForRock(new FileInputStream(TypeOfTile.N_ROCK.getPictureAddress()), 350, 50);
        nRockRectangle.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.N_ROCK;
        });

        Rectangle sRockRectangle = createRectangleForRock(new FileInputStream(TypeOfTile.S_ROCK.getPictureAddress()), 450, 50);
        sRockRectangle.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.S_ROCK;
        });

        anchorPane.getChildren().addAll(wRockRectangle, eRockRectangle, nRockRectangle, sRockRectangle);
    }

    public Rectangle createRectangleForRock(FileInputStream inputStream, int x, int y) {
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
        Rectangle desertShrub = createRectangleForGround(
                new FileInputStream(BuildingName.desertShrub.getPictureAddress()), 50, 50);
        desertShrub.setOnMouseClicked(mouseEvent -> {
            buildingName = BuildingName.desertShrub;
        });

        Rectangle cherryPalm = createRectangleForGround(
                new FileInputStream(BuildingName.cherryPalm.getPictureAddress()), 150, 50);
        cherryPalm.setOnMouseClicked(mouseEvent -> {
            buildingName = BuildingName.cherryPalm;
        });

        Rectangle oliveTree = createRectangleForGround(
                new FileInputStream(BuildingName.oliveTree.getPictureAddress()), 250, 50);
        oliveTree.setOnMouseClicked(mouseEvent -> {
            buildingName = BuildingName.oliveTree;
        });

        Rectangle coconutPalm = createRectangleForGround(
                new FileInputStream(BuildingName.coconutPalm.getPictureAddress()), 350, 50);
        coconutPalm.setOnMouseClicked(mouseEvent -> {
            buildingName = BuildingName.coconutPalm;
        });

        Rectangle palmTree = createRectangleForGround(
                new FileInputStream(BuildingName.palmTree.getPictureAddress()), 450, 50);
        palmTree.setOnMouseClicked(mouseEvent -> {
            buildingName = BuildingName.palmTree;
        });

        anchorPane.getChildren().addAll(desertShrub, cherryPalm, oliveTree, coconutPalm, palmTree);
    }

    public void setWater(AnchorPane anchorPane) throws FileNotFoundException {
        removeAdditional(anchorPane);
        Rectangle shallowWater = createRectangleForGround(
                new FileInputStream(TypeOfTile.SHALLOW_WATER.getPictureAddress()), 50, 50);
        shallowWater.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.SHALLOW_WATER;
        });

        Rectangle river = createRectangleForGround(
                new FileInputStream(TypeOfTile.RIVER.getPictureAddress()), 130, 50);
        river.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.RIVER;
        });

        Rectangle smallPond = createRectangleForGround(
                new FileInputStream(TypeOfTile.SMALL_POND.getPictureAddress()), 210, 50);
        smallPond.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.SMALL_POND;
        });

        Rectangle bigPond = createRectangleForGround(
                new FileInputStream(TypeOfTile.BIG_POND.getPictureAddress()), 290, 50);
        bigPond.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.BIG_POND;
        });

        Rectangle beach = createRectangleForGround(
                new FileInputStream(TypeOfTile.BEACH.getPictureAddress()), 370, 50);
        beach.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.BEACH;
        });

        Rectangle sea = createRectangleForGround(
                new FileInputStream(TypeOfTile.SEA.getPictureAddress()), 450, 50);
        sea.setOnMouseClicked(mouseEvent -> {
            typeOfTile = TypeOfTile.SEA;
        });


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

    public void createCircle(AnchorPane anchorPane,int x, int y, String pictureAddress) throws FileNotFoundException {
        Circle circle = new Circle(x, y, 50);
        circle.setStyle("-fx-background-color: #1ea1ff");
        circle.setFill(new ImagePattern(new Image(new FileInputStream(pictureAddress))));
        circle.setOnMouseClicked(mouseEvent -> {
            militaryUnitName = MilitaryUnitName.getUnitByPictureAddress(pictureAddress);
        });
        anchorPane.getChildren().add(circle);
    }

    public void createCircleForCatapult(AnchorPane anchorPane,int x, int y, String pictureAddress) throws FileNotFoundException {
        Circle circle = new Circle(x, y, 50);
        circle.setStyle("-fx-background-color: #1ea1ff");
        circle.setFill(new ImagePattern(new Image(new FileInputStream(pictureAddress))));
        circle.setOnMouseClicked(mouseEvent -> {
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
        circle.setFill(new ImagePattern(new Image(new FileInputStream(pictureAddress))));
        circle.setOnMouseClicked(mouseEvent -> {
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
