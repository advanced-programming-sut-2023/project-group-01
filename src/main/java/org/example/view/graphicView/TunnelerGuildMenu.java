package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.unit.enums.MilitaryUnitName;

public class TunnelerGuildMenu extends Application {
    private int x;
    private int y;
    @Override
    public void start(Stage stage) throws Exception {

    }


    public void createTunneler(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.TUNNELER, x, y);
    }

}
