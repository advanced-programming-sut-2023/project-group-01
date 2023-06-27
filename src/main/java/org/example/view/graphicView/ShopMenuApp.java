package org.example.view.graphicView;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.building.Material;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.GameMenu;
import org.example.view.mainMenu.gameMenu.ShopMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class ShopMenuApp {
    private static Building currentBuilding;
    private static Stage stage;
    private static Pane shopPane;
    private static int index;
    private static ArrayList<Material> materials = new ArrayList<>();
    private static ImageView prevCommodity;
    private static ImageView nextCommodity;
    private static ImageView commodity;
    private static Text goldText;
    private static Text buy;
    private static Text sell;
    private static Text meatText;
    private static Text cheeseText;
    private static Text appleText;
    private static Text barleyText;
    private static Text aleText;
    private static Text wheatText;
    private static Text floorText;
    private static Text breadText;
    private static HBox foodHbox;
    private static Text woodText;
    private static Text stoneText;
    private static Text ironText;
    private static Text oilText;
    private static HBox sourceHbox;
    private static Text spearText;
    private static Text arcText;
    private static Text maceText;
    private static Text tannerText;
    private static Text crossbowText;
    private static Text pikeText;
    private static Text swordText;
    private static HBox weaponHbox;
    private static Text metalArmourText;
    private static Material currentMaterial;
    private static Text price;

    public ShopMenuApp() {
        initialize();
    }

    public void initialize() {
        int index = 0;
        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (index == 20) return;
            materials.add(material);
            getThisEmpire().getMaterials().put(material, getThisEmpire().getMaterials().get(material));
            index++;
        }

        prevCommodity = new ImageView();
        nextCommodity = new ImageView();
        commodity = new ImageView();
        //TODO
    }

    public static void callInitializers() {
        initializeFoodShop();
        initializeWeaponShop();
        initializeIndustry();
        initializeAmounts();
        initializeGoShopping();
    }

    public static void initializeFoodShop() {
        if (meatText == null) meatText = new Text();
        if (cheeseText == null) cheeseText = new Text();
        if (appleText == null) appleText = new Text();
        if (barleyText == null) barleyText = new Text();
        if (aleText == null) aleText = new Text();
        if (wheatText == null) wheatText = new Text();
        if (floorText == null) floorText = new Text();
        if (breadText == null) breadText = new Text();

        if (foodHbox == null) {
            foodHbox = new HBox();
            foodHbox.setLayoutX(120);
            foodHbox.setPrefWidth(420);
            foodHbox.setMaxWidth(300);
            foodHbox.setLayoutY(130);
            foodHbox.setSpacing(43);
            foodHbox.getChildren().addAll(
                    meatText, cheeseText, appleText, barleyText, aleText, wheatText, floorText, breadText);
        }
    }

    public static void initializeWeaponShop() {
        if (spearText == null) spearText = new Text();
        if (arcText == null) arcText = new Text();
        if (maceText == null) maceText = new Text();
        if (tannerText == null) tannerText = new Text();
        if (crossbowText == null) crossbowText = new Text();
        if (pikeText == null) pikeText = new Text();
        if (swordText == null) swordText = new Text();
        if (metalArmourText == null) metalArmourText = new Text();

        if (weaponHbox == null) {
            weaponHbox = new HBox();
            weaponHbox.setLayoutX(135);
            weaponHbox.setSpacing(48);
            weaponHbox.setLayoutY(133);
            weaponHbox.getChildren().addAll(
                    spearText, arcText, maceText, tannerText, crossbowText, pikeText, swordText, metalArmourText);
        }
    }

    public static void initializeIndustry() {
        if (woodText == null) woodText = new Text();
        if (stoneText == null) stoneText = new Text();
        if (ironText == null) ironText = new Text();
        if (oilText == null) oilText = new Text();

        if (sourceHbox == null) {
            sourceHbox = new HBox();
            sourceHbox.setLayoutX(160);
            sourceHbox.setLayoutY(130);
            sourceHbox.setPrefWidth(440);
            sourceHbox.setSpacing(100);
            sourceHbox.getChildren().addAll(woodText, stoneText, ironText, oilText);
        }
    }

    public static void initializeAmounts() {
        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (material.getMaterialType().getTypeOfProduct().equals("source")) {
                setIndustryText(material, getThisEmpire().getMaterials().get(material));
            } else if (material.getMaterialType().getTypeOfProduct().equals("food")) {
                setFoodText(material, getThisEmpire().getMaterials().get(material));
            } else if (material.getMaterialType().getTypeOfProduct().equals("warfare")) {
                setWeaponText(material, getThisEmpire().getMaterials().get(material));
            }
        }
    }

    public static void initializeGoShopping() {
        if (goldText == null) {
            goldText = new Text();
            goldText.setLayoutX(500);
            goldText.setLayoutY(60);
        }
        goldText.setText("" + getThisEmpire().getGold());
        if (buy == null) {
            buy = new Text();
            buy.setLayoutX(250);
            buy.setLayoutY(80);
        }
        if (sell == null) {
            sell = new Text();
            sell.setLayoutX(250);
            sell.setLayoutY(130);
        }
        if (commodity == null) {
            commodity = new ImageView();
            commodity.setLayoutX(110);
            commodity.setLayoutY(60);
            commodity.setFitWidth(60);
            commodity.setFitHeight(60);
        }
        if (prevCommodity == null) {
            prevCommodity = new ImageView();
            prevCommodity.setLayoutX(14);
            prevCommodity.setLayoutY(18);
            prevCommodity.setFitHeight(30);
            prevCommodity.setFitWidth(30);
        }
        if (nextCommodity == null) {
            nextCommodity = new ImageView();
            nextCommodity.setLayoutX(170);
            nextCommodity.setLayoutY(18);
            nextCommodity.setFitHeight(30);
            nextCommodity.setFitWidth(30);
        }
        //TODO set Position
        if (price == null) {
            price = new Text();
            price.setLayoutX(90);
            price.setLayoutY(60);
        }
        //TODO check
        price.setText("" + getThisEmpire().getMaterials().get(currentMaterial));
    }

    public static void setFoodText(Material material, int value) {
        switch (material.getMaterialType().getName()) {
            case "meat" -> meatText.setText("" + value);
            case "cheese" -> cheeseText.setText("" + value);
            case "apple" -> appleText.setText("" + value);
            case "barley" -> barleyText.setText("" + value);
            case "ale" -> aleText.setText("" + value);
            case "wheat" -> wheatText.setText("" + value);
            case "flour" -> floorText.setText("" + value);
            case "bread" -> breadText.setText("" + value);
        }
    }

    public static void setIndustryText(Material material, int value) {
        switch (material.getMaterialType().getName()) {
            case "wood" -> woodText.setText("" + value);
            case "stone" -> stoneText.setText("" + value);
            case "iron" -> ironText.setText("" + value);
            case "oil" -> oilText.setText("" + value);
        }
    }

    public static void setWeaponText(Material material, int value) {
        switch (material.getMaterialType().getName()) {
            case "spear" -> spearText.setText("" + value);
            case "arc" -> arcText.setText("" + value);
            case "mace" -> maceText.setText("" + value);
            case "leatherArmour" -> tannerText.setText("" + value);
            case "crossbow" -> crossbowText.setText("" + value);
            case "pike" -> pikeText.setText("" + value);
            case "sword" -> swordText.setText("" + value);
            case "metalArmour" -> metalArmourText.setText("" + value);
        }
    }

    public void enterFoodShop(MouseEvent mouseEvent) throws IOException {
        callInitializers();
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/foodShop.fxml");
        shopPane = FXMLLoader.load(url);
        shopPane.getChildren().add(foodHbox);
        openMenu();
    }

    public void enterindustryShop(MouseEvent mouseEvent) throws IOException {
        callInitializers();
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/industryShop.fxml");
        shopPane = FXMLLoader.load(url);
        shopPane.getChildren().add(sourceHbox);
        openMenu();
    }

    public void enterWeaponShop(MouseEvent mouseEvent) throws IOException {
        callInitializers();
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/weaponShop.fxml");
        shopPane = FXMLLoader.load(url);
        shopPane.getChildren().add(weaponHbox);
        openMenu();
    }

    public void returnBack(MouseEvent mouseEvent) throws IOException {
        callInitializers();
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/ShopMenu.fxml");
        shopPane = FXMLLoader.load(url);
        openMenu();
    }

    public static void openMenu() throws IOException {
        HBox pane = (HBox) GameMenuApp.anchorPaneInSplitPan.getChildren().get(0);
        pane.getChildren().remove(0);
        pane.getChildren().add(0, shopPane);
    }

    //TODO قشنگ کردن منو
    public void goShopping(MouseEvent mouseEvent) throws IOException {
        Material material = getMaterialByName(((ImageView) mouseEvent.getSource()).getId());
        callInitializers();
        URL url = ShopMenuApp.class.getResource("/FXML/BuildingMenu/Shop/goShopping.fxml");
        shopPane = FXMLLoader.load(url);
        shopPane.getChildren().addAll(prevCommodity, commodity, nextCommodity, buy, sell, goldText);
        openMenu();
        doShopping(material);
    }

    public void doShopping(Material material) throws IOException {
        int size = materials.size();
        if (index < 0) index += size;
        if (index > size) index -= size;

        currentMaterial = material;
        Material prevMaterial = materials.get((index - 1 + size) % size);
        Material nextMaterial = materials.get((index + 1) % size);
        //TODO ????????????////
        buy.setText("buy : " + material.getMaterialType().getBuyingPrice());
        sell.setText("sell : " + material.getMaterialType().getSellingPrice());
        //TODO check
        prevCommodity.setImage(new Image(prevMaterial.getMaterialType().getPictureAddress().toExternalForm()));
        commodity.setImage(new Image(material.getMaterialType().getPictureAddress().toExternalForm()));
        nextCommodity.setImage(new Image(nextMaterial.getMaterialType().getPictureAddress().toExternalForm()));
    }


    //TODO metalArmour
    public Material getMaterialByName(String name) {
        int index = 0;
        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (material.getMaterialType().getName().equalsIgnoreCase(name)) {
                ShopMenuApp.index = index;
                return material;
            }
            index++;
        }
        return null;
    }

    //TODO آنشلف کردن فایل های مربوط به این منو

    public void buying(MouseEvent mouseEvent) {
        //TODO pop up
        Outputs outputs = ShopMenu.buy(currentMaterial);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputs.toString(), ButtonType.CLOSE);
        alert.setTitle("Buying");
        alert.setGraphic(new ImageView(new Image(currentMaterial.getMaterialType().getPictureAddress().toExternalForm())));
        if (outputs.equals(Outputs.SUCCESS_BUY))
            alert.setHeaderText("Successful buy");
        else
            alert.setHeaderText("Failure in buying");
        alert.showAndWait();
    }

    public void selling(MouseEvent mouseEvent) {
        //TODO
        Outputs outputs = ShopMenu.sell(currentMaterial.getMaterialType().getName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, outputs.toString(), ButtonType.CLOSE);
        alert.setTitle("Selling");
        alert.setGraphic(new ImageView(new Image(currentMaterial.getMaterialType().getPictureAddress().toExternalForm())));
        if (outputs.equals(Outputs.SUCCESS_SELL))
            alert.setHeaderText("Successful sell");
        else
            alert.setHeaderText("Failure in selling");
        alert.showAndWait();
    }

    public void goNext() throws IOException {
        index++;
        doShopping(materials.get((index) % materials.size()));
    }

    public void goPrev() throws IOException {
        index--;
        doShopping(materials.get((index + materials.size()) % materials.size()));
    }

    public static void setCurrentBuilding(Building currentBuilding) {
        ShopMenuApp.currentBuilding = currentBuilding;
    }

    public static void restart() {
        Building currentBuilding;
        index = 0;
        materials = new ArrayList<>();
        ImageView prevCommodity = null;
        ImageView nextCommodity = null;
        ImageView commodity;
        goldText = null;
        buy = null;
        sell = null;
        meatText = null;
        cheeseText = null;
        appleText = null;
        barleyText = null;
        aleText = null;
        wheatText = null;
        floorText = null;
        breadText = null;
        foodHbox = null;
        woodText = null;
        stoneText = null;
        ironText = null;
        oilText = null;
        sourceHbox = null;
        spearText = null;
        arcText = null;
        maceText = null;
        tannerText = null;
        crossbowText = null;
        pikeText = null;
        swordText = null;
        weaponHbox = null;
        metalArmourText = null;
        currentMaterial = null;
        price = null;
    }
}
