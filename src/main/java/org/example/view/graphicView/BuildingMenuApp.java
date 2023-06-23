package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.building.Building;
import org.example.model.building.Gatehouse;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.building.enums.BuildingName;
import org.example.view.mainMenu.gameMenu.BuildingMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class BuildingMenuApp extends Application {

    private Building currentBuilding;
    private static Pane buildingPane;
    private static Pane savePreviousPane;
    private static Stage stage;
    private static double x;
    private static double y;
    @FXML
    private ImageView dast;
    private static String pictureAddress;

    @Override
    public void start(Stage stage) throws Exception {
        //BuildingMenu.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml")
        Pane buildingPane = FXMLLoader.load(BuildingMenu.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml"));
        BuildingMenuApp.buildingPane = buildingPane;
        BuildingMenuApp.stage = stage;
        Scene scene = new Scene(buildingPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

    }


    public void handleDragDetected(MouseEvent mouseEvent) {
        ImageView sourceImageView = (ImageView) mouseEvent.getSource();
        Dragboard db = sourceImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putImage(sourceImageView.getImage());
        db.setContent(clipboardContent);
        pictureAddress = ((ImageView) mouseEvent.getSource()).getImage().getUrl();
        pictureAddress = pictureAddress.replace("2", "");
        System.out.println(pictureAddress);
        System.out.println(BuildingName.INN.getPictureAddress().replace("2", ""));
        mouseEvent.consume();
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
            x = dragEvent.getX();
            y = dragEvent.getY();
        }
    }

    public void handleOnDragDropped(DragEvent dragEvent) {
        Image img = dragEvent.getDragboard().getImage();
        for (Node child : GameMenuApp.gridPane.getChildren()) {
            if (child instanceof ImageView && ((ImageView) child).getFitHeight() == 20 &&
                    child.getLayoutX() < dragEvent.getX() && dragEvent.getX() < child.getLayoutX() + 20 &&
                    child.getLayoutY() < dragEvent.getY() && dragEvent.getY() < child.getLayoutY() + 20) {
                ((ImageView) child).setImage(img);
                GameMenuApp.map.getTile(GridPane.getColumnIndex(child), GridPane.getRowIndex(child)).setBuilding(new Building
                        (null, GridPane.getColumnIndex(child), GridPane.getRowIndex(child), BuildingName.INN));
            }
        }
//        targetImageView.setImage(img);
//        targetImageView.setX(200);
//        targetImageView.setY(200);
//        targetImageView = new ImageView();

        ImageView imageView = new ImageView(img);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
    }

    public void showEmpiresInfo(MouseEvent mouseEvent) {
        //TODO
        System.out.println("show info");
    }

    public void openTrade(MouseEvent mouseEvent) throws Exception {
        new TradeMenuApp().start(stage);
    }

    public void foodProcessingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/foodProcessing.fxml");
        openMenu(address);
    }

    public void weaponMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/WeaponBuilding.fxml");
        openMenu(address);
    }

    public void townBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/TownBuilding.fxml");
        openMenu(address);
    }

    public void dairyFarmMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/DairyFarm.fxml");
        openMenu(address);
    }

    public void industryMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/IndustryBuilding.fxml");
        openMenu(address);
    }

    public void castleBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/CastleBuilding.fxml");
        openMenu(address);
    }

    public void towerMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/tower.fxml");
        openMenu(address);
    }

    public void careHouseMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/careHouse.fxml");
        openMenu(address);
    }

    public void militaryBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/MilitaryBuilding.fxml");
        openMenu(address);
    }

    public void openMenu(URL url) throws IOException {
        Node menu;
        for (Node child : GameMenuApp.anchorPaneInSplitPan.getChildren()) {
            if (child instanceof HBox) {
                for (Node node : ((HBox) child).getChildren()) {
                    if (node instanceof AnchorPane) {
                        ((HBox) child).getChildren().add(((HBox) child).getChildren().indexOf(node), FXMLLoader.load(url));
                        ((HBox) child).getChildren().remove(node);
                        break;
                    }
                }
            }
        }

        menu = FXMLLoader.load(url);
//        Scene scene = new Scene(FXMLLoader.load(url));
//        stage.setScene(scene);
//        stage.show();
    }

    public void prev(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenuApp.class.getResource("/FXML/BuildingMenu/CastleBuilding.fxml");
        openMenu(address);
    }

    public BuildingName getBuildingNameByFxId(String fxId) {
        for (BuildingName buildingName : BuildingName.values())
            if (buildingName.getName().equalsIgnoreCase(fxId))
                return buildingName;
        return null;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public void enterSelectedBuildingMenu() throws Exception {
        if (currentBuilding != null) {
            savePreviousPane = buildingPane;
            if (currentBuilding.getBuildingName().equals(BuildingName.BARRACK) ||
                    currentBuilding.getBuildingName().equals(BuildingName.MERCENARY_BARRACKS) ||
                    currentBuilding.getBuildingName().equals(BuildingName.CATHEDRAL) ||
                    currentBuilding.getBuildingName().equals(BuildingName.ENGINEER_GUILD) ||
                    currentBuilding.getBuildingName().equals(BuildingName.TUNNELER_GUILD)
            ) {
                CreateUnitMenuApp.setCurrentBuilding(currentBuilding);
                CreateUnitMenuApp createUnitMenu = new CreateUnitMenuApp();
                createUnitMenu.start(stage);
            } else if (currentBuilding.getBuildingName().equals(BuildingName.GRANARY)) {
                openGranaryMenu();
            } else if (currentBuilding instanceof Gatehouse || currentBuilding instanceof Tower) {
                RepairMenu.setCurrentBuilding(currentBuilding);
                RepairMenu repairMenu = new RepairMenu();
                repairMenu.start(stage);
            } else if (currentBuilding.getBuildingName().equals(BuildingName.MARKET)) {
                ShopMenuApp shopMenu = new ShopMenuApp();
                ShopMenuApp.setCurrentBuilding(currentBuilding);
                shopMenu.start(stage);
            } else if (currentBuilding.getNumberOfWorkers() > 0) {
                createProducerMenu();
            }
        }
    }

    public void createProducerMenu() throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(160);
        anchorPane.setPrefWidth(600);
        Image image = new Image(new FileInputStream("src\\main\\resources\\Images\\towerRepair.png"));
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        String info = "number of workers : " + currentBuilding.getNumberOfWorkers() +
                "\nmax number of workers : " + currentBuilding.getBuildingName().getNumberOfWorkers() +
                "\nnumber of workers left : " + (currentBuilding.getBuildingName().getNumberOfWorkers() -
                currentBuilding.getNumberOfWorkers());
        if (currentBuilding.getNumberOfWorkers() < currentBuilding.getBuildingName().getNumberOfWorkers())
            info += "\nbuilding : " + currentBuilding.getBuildingName().getName() + " is not working";
        else
            info += "\nbuilding : " + currentBuilding.getBuildingName().getName() + " is working";
        Text text = new Text(info);
        if (currentBuilding.getNumberOfWorkers() == currentBuilding.getBuildingName().getNumberOfWorkers())
            text.setFill(Color.GREEN);
        else
            text.setFill(Color.RED);
        text.setLayoutX(200);
        text.setLayoutY(75);
        anchorPane.getChildren().add(text);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void openGranaryMenu() throws IOException {
        Pane buildingPane = FXMLLoader.load(BuildingMenuApp.class.getResource("/FXML/BuildingMenu/Granary.fxml"));
        BuildingMenuApp.buildingPane = buildingPane;
        Scene scene = new Scene(buildingPane);
        stage.setScene(scene);
        stage.show();
    }

    public void changeFoodRate(MouseEvent mouseEvent) {
        String id = ((ImageView) mouseEvent.getSource()).getId();
        int foodRate = 1;
        if (id.equals("aLot"))
            foodRate = 2;
        else if (id.equals("full"))
            foodRate = 1;
        else if (id.equals("middle"))
            foodRate = 0;
        else if (id.equals("little"))
            foodRate = -1;
        else if (id.equals("veryLittle"))
            foodRate = -2;
        //TODO check
        if (getThisEmpire().getFoodRate() != foodRate) {
            dast.setRotate(dast.getRotate() + 72);
            getThisEmpire().setFoodRate(foodRate);
        }
    }

    public static String getPictureAddress() {
        return pictureAddress;
    }

}
