package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BuildingMenu extends Application {

    private static Pane buildingPane;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Pane buildingPane = FXMLLoader.load(BuildingMenu.class.getResource("/FXML/foodProcessing.fxml"));
        BuildingMenu.buildingPane = buildingPane;
        BuildingMenu.stage = stage;
        Scene scene = new Scene(buildingPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

    }

    public void addElements(TabPane tabPane) {
        Tab foodProcessing = new Tab();
        Tab weaponTab = new Tab();
        Tab townBuildings = new Tab();
        Tab farmTab = new Tab();
        Tab industryTab = new Tab();
        Tab castleBuilding = new Tab();
        Tab towerTab = new Tab();
        Tab careHouse = new Tab();
        Tab militaryTab = new Tab();

    }

    public void enterSelectedBuildingMenu() {

    }

    public void copyBuilding() {

    }

    public void pasteBuilding() {

    }

    public void showBuildingDetail() {

    }


    public void handleDragDetected(MouseEvent mouseEvent) {

    }

    public void handleDragOver(DragEvent dragEvent) {

    }

    public void handleOnDragDropped(DragEvent dragEvent) {

    }

    public void showEmpiresInfo(MouseEvent mouseEvent) {
        System.out.println("show info");
    }

    public void openTrade(MouseEvent mouseEvent) {
        System.out.println("trade");
    }

    public void foodProcessingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/foodProcessing.fxml");
        System.out.println("foodProcessing");
        openMenu(address);
    }

    public void weaponMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/WeaponBuilding.fxml");
        System.out.println("weaponBuilding");
        openMenu(address);
    }

    public void townBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/TownBuilding.fxml");
        System.out.println("townBuilding");
        openMenu(address);
    }

    public void dairyFarmMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/DairyFarm.fxml");
        System.out.println("dairyFarm");
        openMenu(address);
    }

    public void industryMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/IndustryBuilding.fxml");
        System.out.println("industry");
        openMenu(address);
    }

    public void castleBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/CastleBuilding.fxml");
        System.out.println("castleBuilding");
        openMenu(address);
    }

    public void towerMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/tower.fxml");
        System.out.println("towerBuilding");
        openMenu(address);
    }

    public void careHouseMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/careHouse.fxml");
        System.out.println("careHouse");
        openMenu(address);
    }

    public void militaryBuildingMenu(MouseEvent mouseEvent) throws IOException {
        URL address = BuildingMenu.class.getResource("/FXML/MilitaryBuilding.fxml");
        System.out.println("militaryBuilding");
        openMenu(address);
    }

    public void openMenu(URL url) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.show();
    }
}
