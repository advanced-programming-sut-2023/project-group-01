package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Empire;

import java.io.FileInputStream;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;
import static org.example.view.mainMenu.gameMenu.GameMenu.setThisEmpire;

public class Man extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(160);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\popularityPhoto.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        anchorPane.setOnMouseClicked(mouseEvent -> {
            try {//TODO check
                new Rates().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        man(anchorPane);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void man(AnchorPane anchorPane) {
        Empire empire = new Empire(null, null);
        setThisEmpire(empire);
        int popularity = getThisEmpire().getPopularity();
        int population = getThisEmpire().getPopulation();
        int maxPopulation = getThisEmpire().getMaxPopulation();
        double gold = getThisEmpire().getGold();

        Text popularityText = new Text("" + popularity);
        popularityText.setStyle("-fx-font: 10 arial");
        if (popularity > 60) popularityText.setFill(Color.GREEN);
        else if (popularity > 30) popularityText.setFill(Color.YELLOW);
        else popularityText.setFill(Color.RED);
        popularityText.setLayoutX(30);
        popularityText.setLayoutY(110);

        Text goldText = new Text(gold + "");
        goldText.setStyle("-fx-font: 10 arial");
        goldText.setFill(Color.GREEN);
        goldText.setLayoutX(30);
        goldText.setLayoutY(130);

        Text populationText = new Text(population + "\\" + maxPopulation);
        populationText.setStyle("-fx-font: 10 arial");
        populationText.setFill(Color.GREEN);
        populationText.setLayoutX(30);
        populationText.setLayoutY(150);

        anchorPane.getChildren().addAll(popularityText, goldText, populationText);
    }
}
