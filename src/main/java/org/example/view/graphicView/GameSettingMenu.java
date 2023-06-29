package org.example.view.graphicView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.mainMenuController.gameMenuController.CreateMapMenuController;
import org.example.controller.mainMenuController.gameMenuController.GameSettingMenuController;
import org.example.model.*;
import org.example.view.enums.Outputs;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameSettingMenu extends Application implements Initializable {

    public TextField username;
    public VBox vBoxPopUp;
    public Rectangle rectanglePopUp;

    public Text source;
    public VBox maps;
    private ArrayList<User> players = new ArrayList<>();
    public GridPane names;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Fxml/GameSettingMenu.fxml"));
        Data.getStayedLoggedIn().setInGame(true);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players.add(Data.getStayedLoggedIn());
        Text text = new Text(Data.getStayedLoggedIn().getUsername());
        for (String mapName : Data.getStayedLoggedIn().getMaps().keySet()) {
            Text textMap = new Text(mapName);
            textMap.setOnMouseClicked(this::changeMap);
            maps.getChildren().add(textMap);
            textMap.setFill(Color.RED);
        }
        text.setFill(Color.WHITE);
        names.add(text, 0, 0);
    }

    public void removeUser(MouseEvent mouseEvent) {
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        names.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row);
        players.get(row).setInGame(false);
        players.remove(row);
        for (Node child : names.getChildren())
            if (GridPane.getRowIndex(child) > row)
                GridPane.setRowIndex(child, GridPane.getRowIndex(child) - 1);
    }

    public void addUser(MouseEvent mouseEvent) {
        if (players.size() == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("add user error");
            alert.setContentText("you cant choose more than 8 player");
            alert.showAndWait();
        } else {
            rectanglePopUp.setVisible(true);
            vBoxPopUp.setVisible(true);
        }
    }

    public void CheckUser(MouseEvent mouseEvent) {
        Outputs outputs = GameSettingMenuController.checkUser(username.getText());
        if (outputs.equals(Outputs.SUCCESS)) {
            Text text = new Text(username.getText());
            text.setFill(Color.WHITE);
            names.add(text, 0, players.size());
            Data.findUserWithUsername(username.getText()).setInGame(true);
            ImageView imageView = new ImageView(new Image(Main.class.getResource
                    ("/Images/photo5070330372.png").toExternalForm()));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            imageView.setOnMouseClicked(this::removeUser);
            vBoxPopUp.setVisible(false);
            rectanglePopUp.setVisible(false);
            names.add(imageView, 1, players.size());
            players.add(Data.findUserWithUsername(username.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("add user error");
            alert.setContentText(outputs.toString());
            vBoxPopUp.setVisible(false);
            rectanglePopUp.setVisible(false);
            alert.showAndWait();
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        players.forEach(user -> user.setInGame(false));
        new MainMenuApp().start(Main.stage);
    }

    public void setSource(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getImage().getUrl().contains("6101"))
            source.setText("High Source Game");
        if (imageView.getImage().getUrl().contains("6074"))
            source.setText("Normal Game");
        if (imageView.getImage().getUrl().contains("6102"))
            source.setText("DeathMatch Game");
    }

    public void changeMap(MouseEvent mouseEvent) {
        for (Node child : maps.getChildren())
            if (child instanceof Text) {
                if (((Text) child).getText().equals("default map 200 * 200") ||
                        ((Text) child).getText().equals("default map 400 * 400"))
                    ((Text) child).setFill(Color.BLACK);
                else ((Text) child).setFill(Color.RED);
            }
        Text text = (Text) mouseEvent.getSource();
        text.setFill(Color.web("#2024ff"));
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        if (players.size() <= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("start game error");
            alert.setContentText("you most choose a other player or more for start game");
            alert.showAndWait();
        } else {
            new GameMenuApp(setGameMap(), players, setMaterials()).start(Main.stage);
        }
    }

    private InitializeMaterial setMaterials() {
        return switch (source.getText()) {
            case "High Source Game" -> InitializeMaterial.HIGH_SOURCE;
            case "Normal Game" -> InitializeMaterial.MIDDLE_SOURCE;
            default -> InitializeMaterial.LOW_SOURCE;
        };
    }

    private Map setGameMap() {
        String json = "";
        try {
            UsersDatabaseJSON.saveUsersInJSON();
            UsersDatabaseJSON.saveStayedLoggedInUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Node child : maps.getChildren()) {
            if (child instanceof Text && ((Text) child).getFill().equals(Color.web("#2024ff")))
                switch (((Text) child).getText()) {
                    case "default map 200 * 200" -> {
                        try {
                            json = new String(Files.readAllBytes(Paths.get("default map 200.json")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "default map 400 * 400" -> {
                        try {
                            json = new String(Files.readAllBytes(Paths.get("default map 400.json")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    default -> {
                        try {
                            return (Map) Data.getStayedLoggedIn().getMapWithName(((Text) child).getText()).clone();
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

        }
        return new Gson().fromJson(json, new TypeToken<Map>() {
        }.getType());
    }

    public void CreateMap(ActionEvent actionEvent) throws Exception {
        new SetTexture().start(Main.stage);
    }

    public void showReceivedMaps(ActionEvent actionEvent) {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        for (String key : Data.getStayedLoggedIn().getReceivedMap().keySet()) {
            ImageView accept = new ImageView(new Image(Main.class.getResource
                    ("/Images/start Game.PNG").toExternalForm()));
            accept.setFitHeight(30);
            accept.setFitWidth(30);
            ImageView reject = new ImageView(new Image(Main.class.getResource
                    ("/Images/photo5070330372.png").toExternalForm()));
            reject.setFitHeight(30);
            reject.setFitWidth(30);
            HBox hBox = new HBox(new Text(key), accept, reject);
            hBox.setBackground(new Background(new BackgroundFill(Color.SILVER,CornerRadii.EMPTY, Insets.EMPTY )));
            hBox.setSpacing(20);
            accept.setOnMouseClicked(mouseEvent -> {
                TextField textField = new TextField("map name");
                Button cancel = new Button("cancel");
                Button save = new Button("save");
                HBox hBox1 = new HBox(cancel, save);
                hBox1.setSpacing(20);
                VBox vBox1 = new VBox(textField, hBox1);
                Popup popup = new Popup();
                cancel.setOnAction(actionEvent1 -> popup.hide());
                save.setOnAction(actionEvent1 -> {
                    Outputs outputs = CreateMapMenuController.checkSaveMap(textField.getText());
                    if (outputs.equals(Outputs.SUCCESS)) {
                        Data.getStayedLoggedIn().getMaps().put(textField.getText(), Data.getStayedLoggedIn().
                                getReceivedMap().get(key));
                        vBox.getChildren().remove(hBox);
                        Data.getStayedLoggedIn().getReceivedMap().remove(key);
                        popup.hide();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("save map error");
                        alert.setContentText(outputs.toString());
                        alert.showAndWait();
                    }
                });
                popup.getContent().add(vBox1);
                popup.setY(Main.stage.getHeight() / 2);
                popup.setX(Main.stage.getWidth() / 2);
                popup.show(Main.stage);
            });
            reject.setOnMouseClicked(mouseEvent -> {
                vBox.getChildren().remove(hBox);
                Data.getStayedLoggedIn().getReceivedMap().remove(key);
            });
            vBox.getChildren().add(hBox);
        }
        Button button = new Button("exit");
        vBox.getChildren().add(button);
        setPopUp(vBox, button);
    }

    private void setPopUp(VBox vBox, Button button) {
        Popup popup = new Popup();
        button.setOnAction(actionEvent -> popup.hide());
        popup.getContent().add(vBox);
        popup.setY(Main.stage.getHeight() / 2);
        popup.setX(Main.stage.getWidth() / 2);
        popup.show(Main.stage);
    }

    public void sendMap(ActionEvent actionEvent) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (String name : Data.getStayedLoggedIn().getMaps().keySet())
            choiceBox.getItems().add(name);
        TextField textField = new TextField("username");
        Button send = new Button("send");
        Button exit = new Button("exit");
        VBox vBox = new VBox(textField, choiceBox, send, exit);
        vBox.setSpacing(20);
        send.setOnAction(actionEvent1 -> {
            Outputs outputs = CreateMapMenuController.checkSendMap(textField.getText());
            if (outputs.equals(Outputs.SUCCESS)) {
                User user = Data.findUserWithUsername(textField.getText());
                user.addReceivedMap(choiceBox.getValue() + " from user: " + Data.getStayedLoggedIn().getUsername(),
                        Data.getStayedLoggedIn().getMapWithName(choiceBox.getValue()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("send map error");
                alert.setContentText(outputs.toString());
                alert.showAndWait();
            }
        });
        setPopUp(vBox, exit);
    }
}
