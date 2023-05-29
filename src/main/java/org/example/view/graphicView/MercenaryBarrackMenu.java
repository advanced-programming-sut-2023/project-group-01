package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.controller.NextTurn;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

import java.net.URL;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class MercenaryBarrackMenu extends Application {
    private int x;
    private int y;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = MercenaryBarrackMenu.class.getResource("/FXML/MercenaryBarrackMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    //TODO set x, y mercenaryBarrack
    //TODO get tab and add these things to it

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

    public static void createUnit(MilitaryUnitName militaryUnitName, int x , int y) {
        //new MilitaryUnit(getMap().getTile(x, y), NextTurn.getCurrentEmpire(), militaryUnitName, x, y);
        militaryUnitName.getVoice().playVoice(militaryUnitName.getVoice());
    }

    public static void findXY(int x, int y, int size) {

    }

}
