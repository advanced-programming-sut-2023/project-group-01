package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.Empire;
import org.example.model.building.Building;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

import java.net.URL;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;
import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class CreateUnitMenuApp extends Application {
    private static Building currentBuilding;
    private static int x;
    private static int y;
    private static URL url;
    private static Text text = new Text();

    public void createPane(Pane buildingPane) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(url);
        buildingPane.getChildren().clear();
        buildingPane.getChildren().addAll(anchorPane.getChildren());
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public static void setCurrentBuilding(Building currentBuilding) {
        CreateUnitMenuApp.currentBuilding = currentBuilding;
        if (currentBuilding.getBuildingName().equals(BuildingName.BARRACK)) {
            url = CreateUnitMenuApp.class.getResource("/FXML/CreateUnit/BarrackMenu.fxml");
            createText(585, 40);
        } else if (currentBuilding.getBuildingName().equals(BuildingName.MERCENARY_BARRACKS)) {
            url = CreateUnitMenuApp.class.getResource("/FXML/CreateUnit/MercenaryBarrackMenu.fxml");
            createText(540, 148);
        } else if (currentBuilding.getBuildingName().equals(BuildingName.CATHEDRAL)) {
            url = CreateUnitMenuApp.class.getResource("/FXML/CreateUnit/CathedralMenu.fxml");
            createText(550, 20);
        } else if (currentBuilding.getBuildingName().equals(BuildingName.ENGINEER_GUILD)) {
            url = CreateUnitMenuApp.class.getResource("/FXML/CreateUnit/MercenaryBarrackMenu.fxml");
            createText(540, 20);
        } else if (currentBuilding.getBuildingName().equals(BuildingName.TUNNELER_GUILD)) {
            url = CreateUnitMenuApp.class.getResource("/FXML/CreateUnit/TunnlerGuildMenu.fxml");
            createText(540, 20);
        }
        findXY();
    }

    public static void createText(double x, double y) {
        text.setText(getThisEmpire().getPopulation() + "");
        text.setLayoutX(x);
        text.setLayoutY(y);
        text.setStroke(Color.WHITE);
    }

    public static void findXY() {
        int buildingSize = currentBuilding.getBuildingName().getSize();
        int x = currentBuilding.getBeginX();
        int y = currentBuilding.getBeginY();
        int size = getMap().getSize();

        for (int i = x; i < x + buildingSize; i++)
            if (y > 1 && i < size && getMap().getTile(i, y - 1) != null) {
                CreateUnitMenuApp.x = i;
                CreateUnitMenuApp.y = y - 1;
                return;
            }
        for (int j = y; j < y + buildingSize; j++)
            if (x > 1 && j < size && getMap().getTile(x - 1, j) != null) {
                CreateUnitMenuApp.x = x - 1;
                CreateUnitMenuApp.y = j;
                return;
            }
        for (int i = x; i < x + buildingSize; i++)
            if (y + buildingSize < size && i < size && getMap().getTile(i, y + buildingSize) != null) {
                CreateUnitMenuApp.x = i;
                CreateUnitMenuApp.y = y + buildingSize;
                return;
            }
        for (int j = y; j < y + buildingSize; j++)
            if (x + buildingSize < size && j < size && getMap().getTile(x, j) != null) {
                CreateUnitMenuApp.x = x + buildingSize;
                CreateUnitMenuApp.y = j;
                return;
            }

        CreateUnitMenuApp.x = x - 1;
        CreateUnitMenuApp.y = y - 1;
    }


    //TODO set x, y mercenaryBarrack
    //TODO get tab and add these things to it
    //TODO setPicture of a unit in map

    public void createArabianArcher(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.ARCHER_BOW, x, y);
    }

    public void createSlaves(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.SLAVES, x, y);
    }

    public void createSligners(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.SLINGERS, x, y);
    }

    public void createAssassins(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.ASSASSINS, x, y);
    }

    public void createHorseArcher(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.HORSE_ARCHER, x, y);
    }

    public void createArabianSwordsmen(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.ARABIAN_SWORSMEN, x, y);
    }

    public void createFireThrowers(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.FIRE_THROWERS, x, y);
    }

    public void createTunneler(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.TUNNELER, x, y);

    }

    public void createEngineer(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.ENGINEER, x, y);
    }

    public void createLadderMen(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.LADDER_MEN, x, y);
    }

    public void createBlackMonk(MouseEvent mouseEvent) {
        createUnit(MilitaryUnitName.BLACK_MONK, x, y);
    }

    public void createArcher(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.ARCHER, x, y);
    }

    public void createSpearmen(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.SPEAR_MEN, x, y);
    }

    public void createMacemen(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.MACE_MEN, x, y);
    }

    public void createCrossbowmen(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.CROSSBOW_MEN, x, y);
    }

    public void createPikemen(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.PIKE_MEN, x, y);
    }

    public void createSwordsmen(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.SWORDSMEN, x, y);
    }

    public void createKnight(MouseEvent mouseEvent) {
        checkBarrack(MilitaryUnitName.KNIGHT, x, y);
    }

    public void checkBarrack(MilitaryUnitName militaryUnitName, int x, int y) {
        Empire empire = currentBuilding.getEmpire();
        MaterialType armour = militaryUnitName.getArmour();
        MaterialType armament = militaryUnitName.getArmament();
        if ((armour != null && !empire.havingMaterial(armour, 1) ||
                (armament != null && !empire.havingMaterial(armament, 1) ||
                        (militaryUnitName.equals(MilitaryUnitName.KNIGHT)) &&
                                !empire.havingMaterial(MaterialType.HORSE, 1)))) {
            ErrorInCreatingUnit("selah mored niaz ast");
            return;
        }

        if (empire.getNormalPopulation() < 1) {
            ErrorInCreatingUnit("adem nadari");
            return;
        }
        if (empire.getGold() < militaryUnitName.getCost()) {
            ErrorInCreatingUnit("preduce ool nadari");
            return;
        }

        if (armour != null) empire.reduceMaterial(armour);
        if (armament != null) empire.reduceMaterial(armament);
        empire.decreaseGold(militaryUnitName.getCost());
        //        new MilitaryUnit(getMap().getTile(x, y), NextTurn.getCurrentEmpire(), militaryUnitName, x, y);
        militaryUnitName.getVoice().playVoice(militaryUnitName.getVoice());
    }

    public void createUnit(MilitaryUnitName militaryUnitName, int x, int y) {
        Empire empire = currentBuilding.getEmpire();
        if (empire.getNormalPopulation() < 1) {
            ErrorInCreatingUnit("adem nadari");
            return;
        }
        if (empire.getGold() < militaryUnitName.getCost()) {
            ErrorInCreatingUnit("pool nadari");
            return;
        }
        //TODO check
        new MilitaryUnit(getMap().getTile(x, y), getThisEmpire(), militaryUnitName, x, y);
        militaryUnitName.getVoice().playVoice(militaryUnitName.getVoice());
        createText(text.getLayoutX(), text.getLayoutY());
    }

    public void ErrorInCreatingUnit(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, error, ButtonType.CLOSE);
        alert.setHeaderText("Failure in creating unit");
        alert.showAndWait();
    }
}
