package org.example.view.graphicView;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.unit.enums.MilitaryUnitName;

public class BarrackMenu extends Application {

    private int x;
    private int y;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void createArcher(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.ARCHER, x, y);
    }

    public void createSpearmen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.SPEAR_MEN, x, y);
    }

    public void createMacemen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.MACE_MEN, x, y);
    }

    public void createCrossbowmen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.CROSSBOW_MEN, x, y);
    }

    public void createPikemen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.PIKE_MEN, x, y);
    }

    public void createSwordsmen(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.SWORDSMEN, x, y);
    }

    public void createKnight(MouseEvent mouseEvent) {
        MercenaryBarrackMenu.createUnit(MilitaryUnitName.KNIGHT, x, y);
    }

    public void findXY() {

    }

}
