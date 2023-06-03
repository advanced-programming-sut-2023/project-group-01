package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.building.Building;
import org.example.model.building.Gatehouse;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.view.mainMenu.gameMenu.BuildingMenu;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class BuildingMenuApp extends Application {

    private Building currentBuilding;
    private static Pane buildingPane;
    private static Pane savePreviousPane;
    private static Stage stage;
    @FXML
    private ImageView dast;

    @Override
    public void start(Stage stage) throws Exception {
        //BuildingMenu.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml")
        Pane buildingPane = FXMLLoader.load(BuildingMenu.class.getResource("/FXML/BuildingMenu/weaponBuilding.fxml"));
        BuildingMenuApp.buildingPane = buildingPane;
        BuildingMenuApp.stage = stage;
        ImageView imageView = new ImageView(new Image(MaterialType.WOOD.getPictureAddress().toExternalForm()));
        buildingPane.getChildren().add(imageView);
        Scene scene = new Scene(buildingPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

    }


    public void handleDragDetected(MouseEvent mouseEvent) {

    }

    public void handleDragOver(DragEvent dragEvent) {

    }

    public void handleOnDragDropped(DragEvent dragEvent) {

    }

    public void showEmpiresInfo(MouseEvent mouseEvent) {
        //TODO
        System.out.println("show info");
    }

    public void openTrade(MouseEvent mouseEvent) {
        //TODO
        System.out.println("trade");
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
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.show();
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
                CreateUnitMenuApp createUnitMenu = new CreateUnitMenuApp();
                createUnitMenu.setCurrentBuilding(currentBuilding);
                createUnitMenu.start(stage);
            } else if (currentBuilding.getBuildingName().equals(BuildingName.GRANARY)){
                openGranaryMenu();
            } else if (currentBuilding instanceof Gatehouse) {
                openGateHouse();
            } else if (currentBuilding instanceof Tower) {
                openTower();
            } else if (currentBuilding.getBuildingName().equals(BuildingName.MARKET)) {
                ShopMenuApp shopMenu = new ShopMenuApp();
                ShopMenuApp.setCurrentBuilding(currentBuilding);
                shopMenu.start(stage);
            }
        }
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

    public void openGateHouse() {

    }

    public void openTower() {

    }

    public void copyBuilding() {

    }

    public void pasteBuilding() {

    }

    public void showBuildingDetail() {

    }

}
