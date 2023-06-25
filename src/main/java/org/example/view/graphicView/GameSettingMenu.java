package org.example.view.graphicView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Main;
import org.example.controller.mainMenuController.gameMenuController.GameSettingMenuController;
import org.example.model.Data;
import org.example.model.InitializeMaterial;
import org.example.model.Map;
import org.example.model.User;
import org.example.view.enums.Outputs;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        Data.getStayedLoggedIn().setInGame(false);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        players.add(Data.getStayedLoggedIn());
        Text text = new Text(Data.getStayedLoggedIn().getUsername());
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
            Data.findUserWithUsername(username.getText()).setInGame(false);
            ImageView imageView = new ImageView(new Image(Main.class.getResource
                    ("/Images/photo5070330372.jpg").toExternalForm()));
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
            if (child instanceof Text)
                ((Text) child).setFill(Color.BLACK);
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
                }

        }
        return new Gson().fromJson(json, new TypeToken<Map>() {
        }.getType());
    }
}
