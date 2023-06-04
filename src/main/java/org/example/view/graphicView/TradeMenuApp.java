package org.example.view.graphicView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.model.Empire;
import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.view.LoginMenu;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Objects;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class TradeMenuApp extends Application {

    public MaterialType materialType;
    public Empire toEmpire;
    public int price;
    public ImageView background;
    public TextField priceField;
    private int amountValue;
    public ImageView image1;
    public Label amount1;
    public ImageView image2;
    public Label amount2;
    public ImageView image3;
    public Label amount3;
    public ImageView image4;
    public Label amount4;
    public ImageView image5;
    public Label amount5;
    public ImageView image6;
    public Label amount6;
    public ImageView image7;
    public Label amount7;
    public ImageView image8;
    public Label amount8;
    public ImageView image9;
    public Label amount9;
    public ImageView image10;
    public Label amount10;
    public ImageView image11;
    public Label amount11;
    public ImageView image12;
    public Label amount12;
    public ImageView image13;
    public Label amount13;
    public ImageView image14;
    public Label amount14;
    public ImageView image15;
    public Label amount15;
    public ImageView image16;
    public Label amount16;
    public ImageView image17;
    public Label amount17;
    public ImageView image18;
    public Label amount18;
    public ImageView image19;
    public Label amount19;
    public ImageView image20;
    public Label amount20;
    public ComboBox usersList;
    public ImageView paper;
    public VBox commodities;
    public HBox usersListHBox;
    public Label amount;

    @Override
    public void start(Stage stage) throws Exception {
        amountValue = 0;
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/FXML/TradeMenu.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setTitle("Trade Menu");
        stage.show();
    }

    public void initialize() {

        GameMenu.setThisEmpire(new Empire(null, null));
        ArrayList<String> users = new ArrayList<>();
        users.add("ssss");
        for (Empire empire : GameMenu.getEmpires())
            users.add(empire.getPlayer().getUsername());

        usersList.setItems(FXCollections.observableArrayList(users));

        usersList.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            toEmpire = GameMenu.getEmpireWhitUsername((String) newValue);
            commodities.setVisible(true);
            background.requestFocus();
            background.setFocusTraversable(true);
        });

        priceField.textProperty().addListener((observable, oldText, newText) -> {
            try {
                price = Integer.parseInt(newText);
                System.out.println(price);
            }catch (NumberFormatException e){
                Platform.runLater(() -> {
                    priceField.clear();
                });
                price=0;
            }
        });


        background.setFocusTraversable(true);
        background.requestFocus();
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();
                if (commodities.isVisible()){
                    if(keyName.equals("Add"))
                        increaseAmount();
                    if (keyName.equals("Subtract"))
                        decreaseAmount();
                }
            }
        });

        image1.setImage(new Image(MaterialType.WOOD.getPictureAddress().toString()));
        image2.setImage(new Image(MaterialType.BARLEY.getPictureAddress().toString()));
        image3.setImage(new Image(MaterialType.STONE.getPictureAddress().toString()));
        image4.setImage(new Image(MaterialType.IRON.getPictureAddress().toString()));
        image5.setImage(new Image(MaterialType.OIL.getPictureAddress().toString()));
        image6.setImage(new Image(MaterialType.WHEAT.getPictureAddress().toString()));
        image7.setImage(new Image(MaterialType.BREAD.getPictureAddress().toString()));
        image8.setImage(new Image(MaterialType.CHEESE.getPictureAddress().toString()));
        image9.setImage(new Image(MaterialType.MEAT.getPictureAddress().toString()));
        image10.setImage(new Image(MaterialType.APPLE.getPictureAddress().toString()));

        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (material.getMaterialType().getName().equals(MaterialType.WOOD.getName()))
                amount1.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.BARLEY.getName()))
                amount2.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.STONE.getName()))
                amount3.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.IRON.getName()))
                amount4.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.OIL.getName()))
                amount5.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.WHEAT.getName()))
                amount6.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.BREAD.getName()))
                amount7.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.CHEESE.getName()))
                amount8.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.MEAT.getName()))
                amount9.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.APPLE.getName()))
                amount10.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.ALE.getName()))
                amount11.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.FLOUR.getName()))
                amount12.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.ARC.getName()))
                amount13.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.CROSSBOW.getName()))
                amount14.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.SPEAR.getName()))
                amount15.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.PIKE.getName()))
                amount16.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.MACE.getName()))
                amount17.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.SWORD.getName()))
                amount18.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.LEATHER_ARMOUR.getName()))
                amount19.setText(getThisEmpire().getMaterials().get(material).toString());
            else if (material.getMaterialType().getName().equals(MaterialType.METAL_ARMOUR.getName()))
                amount20.setText(getThisEmpire().getMaterials().get(material).toString());
        }


        image11.setImage(new Image(MaterialType.ALE.getPictureAddress().toString()));
        image12.setImage(new Image(MaterialType.FLOUR.getPictureAddress().toString()));
        image13.setImage(new Image(MaterialType.ARC.getPictureAddress().toString()));
        image14.setImage(new Image(MaterialType.CROSSBOW.getPictureAddress().toString()));
        image15.setImage(new Image(MaterialType.SPEAR.getPictureAddress().toString()));
        image16.setImage(new Image(MaterialType.PIKE.getPictureAddress().toString()));
        image17.setImage(new Image(MaterialType.MACE.getPictureAddress().toString()));
        image18.setImage(new Image(MaterialType.SWORD.getPictureAddress().toString()));
        image19.setImage(new Image(MaterialType.LEATHER_ARMOUR.getPictureAddress().toString()));
        image20.setImage(new Image(MaterialType.METAL_ARMOUR.getPictureAddress().toString()));


        background.setFocusTraversable(true);
    }

    public void increaseAmount() {
        amountValue += 1;
        amount.setText("Amount : " + amountValue);
    }

    public void decreaseAmount() {
        if (amountValue > 0)
            amountValue -= 1;
        amount.setText("Amount : " + amountValue);
    }


    public void sentTrade() {

    }

    public void receiveTrade() {

    }

    public void chooseMaterial(MouseEvent mouseEvent) throws MalformedURLException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getImage() != null) {
            materialType = (MaterialType.getMaterialWithImage(new URL(imageView.getImage().getUrl())));
        }
    }

    public void newTrade(MouseEvent mouseEvent) {
        paper.setVisible(true);
        usersListHBox.setVisible(true);
    }


}
