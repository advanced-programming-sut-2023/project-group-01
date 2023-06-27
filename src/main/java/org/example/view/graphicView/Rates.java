package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Empire;
import org.example.view.mainMenu.gameMenu.GameMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class Rates extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        addText(anchorPane);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();

    }

    public void addText(AnchorPane anchorPane) throws FileNotFoundException {
//        Empire empire = new Empire(null, null);
//        GameMenu.setThisEmpire(empire);
        int foodRate = getThisEmpire().getFoodRate();
        int taxRate = getThisEmpire().getTaxRate();
        int religionRate = getThisEmpire().getReligionPopularity();
        int fearRate = getThisEmpire().getFearRate();
        int totalRate = foodRate + taxRate + religionRate + fearRate;

        Text foodText = new Text(getThisEmpire().getFoodRate() + " Food");
        foodText.setStyle("-fx-font: 15 arial");
        setColor(foodText, foodRate);
        foodText.setLayoutX(200);
        foodText.setLayoutY(30);
        Circle foodCircle = new Circle(180, 30, 10);
        setEmoji(foodCircle, foodRate);

        Text taxText = new Text(getThisEmpire().getTaxRate() + " tax");
        taxText.setStyle("-fx-font: 15 arial");
        setColor(taxText, taxRate);
        taxText.setLayoutX(200);
        taxText.setLayoutY(55);
        Circle taxCircle = new Circle(180, 55, 10);
        setEmoji(taxCircle, taxRate);

        Text religionText = new Text(getThisEmpire().getReligionPopularity() + " religion");
        religionText.setStyle("-fx-font: 15 arial");
        setColor(religionText, religionRate);
        religionText.setLayoutX(200);
        religionText.setLayoutY(80);
        Circle religionCircle = new Circle(180, 80, 10);
        setEmoji(religionCircle, religionRate);

        Text fearText = new Text(getThisEmpire().getFearRate() + " fear");
        fearText.setStyle("-fx-font: 15 arial");
        setColor(fearText, fearRate);
        fearText.setLayoutX(200);
        fearText.setLayoutY(105);
        Circle fearCircle = new Circle(180, 105, 10);
        setEmoji(fearCircle, fearRate);

        Text totalText = new Text(totalRate + " total");
        totalText.setStyle("-fx-font: 15 arial");
        setColor(totalText, totalRate);
        totalText.setLayoutX(300);
        totalText.setLayoutY(130);
        Circle totalCircle = new Circle(280, 130, 10);
        setEmoji(totalCircle, totalRate);

        anchorPane.getChildren().addAll(foodText, taxText, religionText, fearText, totalText,
                foodCircle, taxCircle, religionCircle, fearCircle, totalCircle);
    }

    public void setColor(Text text, int rate) {
        if (rate > 0) text.setFill(Color.GREEN);
        else if (rate == 0) text.setFill(Color.YELLOW);
        else text.setFill(Color.RED);
    }

    public void setEmoji(Circle circle, int rate) throws FileNotFoundException {
        String address;
        if (rate > 0) address = "src\\main\\resources\\Images\\Emoji\\smile.png";
        else if (rate == 0) address = "src\\main\\resources\\Images\\Emoji\\normal.jfif";
        else address = "src\\main\\resources\\Images\\Emoji\\cry.jfif";

        FileInputStream inputStream = new FileInputStream(address);
        Image image = new Image(inputStream);
        circle.setFill(new ImagePattern(image));
    }

}
