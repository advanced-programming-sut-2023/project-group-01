package org.example.view.graphicView;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class EmpireMenuApp extends Application {
    double x = 0;
    private Text taxRate;
    private Text fearRate;

    @Override
    public void start(Stage stage) throws Exception {
        createEmpireMenu(stage);
    }

    public void createEmpireMenu(Stage stage) throws FileNotFoundException {
        Text text = new Text("Empire Building");
        text.setLayoutX(30);
        text.setLayoutY(30);
        text.setStyle("-fx-font: 15 arial;");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(160);
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Images\\towerRepair.png");
        Image image = new Image(inputStream);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        anchorPane.setBackground(background);
        //TODO set the v2 is the popularity
        int taxRate = 0;
        taxRate = getThisEmpire().getTaxRate();
        Slider slider1= new Slider(-3, 8, taxRate);
        this.taxRate = new Text();
        this.taxRate.setLayoutX(400);
        this.taxRate.setLayoutY(60);
        createTaxSlider(slider1);

        int fearRate = 0;
        fearRate = getThisEmpire().getFearRate();
        Slider slider2 = new Slider(-5, 5, fearRate);
        this.fearRate = new Text();
        this.fearRate.setLayoutX(400);
        this.fearRate.setLayoutY(120);
        createFearSlider(slider2);

        anchorPane.getChildren().addAll(text, slider1, slider2, this.taxRate, this.fearRate);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void createTaxSlider(Slider slider) {
        slider.setLayoutX(200);
        slider.setLayoutY(50);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                taxRate.setText("Your tax rate: " + newValue.intValue());
                getThisEmpire().setTaxRate(newValue.intValue());
            }
        });
    }

    public void createFearSlider(Slider slider) {
        slider.setLayoutX(200);
        slider.setLayoutY(100);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                fearRate.setText("Your fear rate: " + newValue.intValue());
                getThisEmpire().setFearRate(newValue.intValue());
            }
        });
    }
}
