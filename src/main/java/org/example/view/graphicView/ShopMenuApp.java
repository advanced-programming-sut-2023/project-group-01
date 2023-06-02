package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.building.Building;
import org.example.model.building.Material;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class ShopMenuApp extends Application {
    private static Building currentBuilding;
    private static Stage stage;
    private static Pane shopPane;
    private int index;
    private ArrayList<Material> materials = new ArrayList<>();
    private HashMap<Material, Integer> materialHashMap = new HashMap<>();
    @FXML
    private Text goldText;
    @FXML
    private ImageView prevCommodity;
    @FXML
    private ImageView nextCommodity;
    @FXML
    private ImageView commodity;
    @FXML
    private Text buy;
    @FXML
    private Text sell;
    private Material currentMaterial;

    @Override
    public void start(Stage stage) throws Exception {
        ShopMenuApp.stage = stage;
    }

    @FXML
    public void initialize() {
        int index = 0;
        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (index == 20) return;
            materials.add(material);
            materialHashMap.put(material, getThisEmpire().getMaterials().get(material));
            index++;
        }
        goldText.setText("" + getThisEmpire().getGold());

        //TODO
    }


    public static void setCurrentBuilding(Building currentBuilding) {
        ShopMenuApp.currentBuilding = currentBuilding;
    }

    public void enterFoodShop(MouseEvent mouseEvent) throws IOException {
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/foodShop.fxml");
        openMenu(url);
    }

    public void enterindustryShop(MouseEvent mouseEvent) throws IOException {
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/industryShop.fxml");
        openMenu(url);
    }

    public void enterWeaponShop(MouseEvent mouseEvent) throws IOException {
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/weaponShop.fxml");
        openMenu(url);
    }

    public void openMenu(URL url) throws IOException {
        //TODO set amount
        shopPane = FXMLLoader.load(BuildingMenu.class.getResource("/FXML/BuildingMenu/Granary.fxml"));
        ShopMenuApp.stage = stage;
        Scene scene = new Scene(shopPane);
        stage.setScene(scene);
        stage.show();
    }

    public void selling() {

    }

    public void buying() {

    }

    public void goShopping(MouseEvent mouseEvent) {
        Material material = getMaterialByName(((ImageView)mouseEvent.getSource()).getId());
        int size = materials.size();
        if (index < 0) index += size;
        if (material != null) {
            currentMaterial = material;
            Material prevMaterial = materials.get((index - 1 + size) % size);
            Material nextMaterial = materials.get((index + 1) % size);
            buy.setText("buy" + material.getMaterialType().getBuyingPrice());
            sell.setText("buy" + material.getMaterialType().getSellingPrice());
            //TODO chek
            prevCommodity.setImage(new Image(prevMaterial.getMaterialType().getPictureAddress()));
            commodity.setImage(new Image(currentMaterial.getMaterialType().getPictureAddress()));
            nextCommodity.setImage(new Image(nextMaterial.getMaterialType().getPictureAddress()));
        }
    }
    //TODO metalArmour
    public Material getMaterialByName(String name) {
        int index = 0;
        for (Material material : materialHashMap.keySet()) {
            if (material.getMaterialType().getName().equals(name)) {
                this.index = index;
                return material;
            }
            index++;
        }
        return null;
    }


}
