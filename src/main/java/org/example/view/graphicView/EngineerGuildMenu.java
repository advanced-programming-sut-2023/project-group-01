package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.unit.enums.MilitaryUnitName;

public class EngineerGuildMenu extends Application {
    private int x;
    private int y;

    @Override
    public void start(Stage stage) throws Exception {


    }

    public void createEngineer(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.ENGINEER, x, y);
    }

    public void createLadderMen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.LADDER_MEN, x, y);
    }

    public void findXY() {

    }

}
