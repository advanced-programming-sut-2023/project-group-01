package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.building.Building;

public class ShopMenuApp extends Application {
    private static Building currentBuilding;

    @Override
    public void start(Stage stage) throws Exception {

    }


    public static void setCurrentBuilding(Building currentBuilding) {
        ShopMenuApp.currentBuilding = currentBuilding;
    }


    public void enterFoodShop(MouseEvent mouseEvent) {
    }

    public void enterindustryShop(MouseEvent mouseEvent) {
    }

    public void enterWeaponShop(MouseEvent mouseEvent) {

    }
}
