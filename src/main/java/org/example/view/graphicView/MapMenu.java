package org.example.view.graphicView;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Tile;
import org.example.model.unit.MilitaryUnit;

import java.util.ArrayList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;


public class MapMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        createPane(anchorPane);
    }

    @FXML
    public void initialize() {

    }

    private void createPane(AnchorPane anchorPane) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(200, 200);
        for (int i = 0; i < 200; i++)
            for (int j = 0; j < 200; j++)
                gridPane.add(new ImageView(new Image(getMap().getTile(i, j).getTypeOfTile().getPictureAddress())), i, j);

        TabPane tabPane = new TabPane();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().add(scrollPane);
        anchorPane.getChildren().add(splitPane);
    }

    public void createTab() {
        TabPane tabPane = new TabPane();
        Tab castleTab = new Tab();
        Tab farmTab = new Tab();
        Tab foodTab = new Tab();
        Tab industyTab = new Tab();
        Tab townTab = new Tab();
        Tab weaponTab = new Tab();
        castleTab.setContent(new ImageView(new Image("")));
        farmTab.setContent(new ImageView(new Image("")));
        foodTab.setContent(new ImageView(new Image("")));
        industyTab.setContent(new ImageView(new Image("")));
        townTab.setContent(new ImageView(new Image("")));
        weaponTab.setContent(new ImageView(new Image("")));
        tabPane.getTabs().addAll(castleTab, farmTab, foodTab, industyTab, townTab, weaponTab);
    }

    public void addPopularityToTab() {

    }

    public void fullTabs(Tab castleTab, String...args) {
        HBox hBox = new HBox();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (String arg : args)
            imageViews.add(new ImageView(new Image(arg)));
        hBox.getChildren().addAll(imageViews);
        castleTab.setContent(hBox);
    }


    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            node.setScaleX(node.getScaleX() * zoomFactor);
            node.setScaleY(node.getScaleY() * zoomFactor);
        });
    }

    public void hover(Stage stage) {
        StackPane notedPane = new StackPane();
        notedPane.setPrefSize(20, 20);
        notedPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        notedPane.setStyle("-fx-background-color: purple;");

        StackPane rootPane = new StackPane(notedPane);
        rootPane.setPrefSize(400, 400);
        StackPane.setAlignment(notedPane, Pos.BOTTOM_CENTER);

        stage.setScene(new Scene(rootPane));
        stage.show();
        Stage stickyNotesStage = new Stage();
        stickyNotesStage.initOwner(stage);
        stickyNotesStage.initStyle(StageStyle.UNDECORATED);
        StackPane stickyNotesPane = new StackPane();
        stickyNotesPane.setPrefSize(200, 200);
        stickyNotesPane.setStyle("-fx-background-color: yellow;");
        stickyNotesStage.setScene(new Scene(stickyNotesPane));

        notedPane.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                stickyNotesStage.show();
            } else {
                stickyNotesStage.hide();
            }
        });
    }

    public void zoom() {

    }

    public void graphicalMoveTroop(Tile moving, Empire empire) {
        //TODO animation of the troops
    }

    public ArrayList<MilitaryUnit> troopsOfTheEmpire(Tile tile, Empire empire) {
        ArrayList<MilitaryUnit> units = new ArrayList<>();
        for (People person : tile.getPeople())
            if (person instanceof MilitaryUnit)
                units.add((MilitaryUnit) person);
        return units;
    }

    public void showStatus() {

    }

    public void showAttackingUnits(Empire empire) {

    }

    public void focusOnATile() {

    }

    public void moveShortcut() {

    }

    public void selectUnitsInATile() {

    }

    public void selectAGroupOfTiles() {

    }




}
