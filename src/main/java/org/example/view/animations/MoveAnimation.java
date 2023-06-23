package org.example.view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import org.example.controller.NextTurn;
import org.example.model.unit.MilitaryUnit;

import java.util.LinkedList;

public class MoveAnimation extends Transition {

    private final int yDest;
    private final MilitaryUnit unit;
    private final boolean havePatrol;
    private final int xDest;
    private final LinkedList<Integer> path;
    private NextTurn nextTurn;
    private int counter = 0;

    public MoveAnimation(NextTurn nextTurn, MilitaryUnit unit, boolean havePatrol, int xDest, int yDest, LinkedList<Integer> path) {
        this.nextTurn = nextTurn;
        this.setCycleCount(Math.min(unit.getMilitaryUnitName().getSpeed(), path.size() - 1));
        this.setCycleDuration(Duration.millis(5000));
        this.unit = unit;
        this.havePatrol = havePatrol;
        this.xDest = xDest;
        this.yDest = yDest;
        this.path = path;
    }

    @Override
    protected void interpolate(double v) {

        if (nextTurn.isMoveFinished(unit, path.get(counter), counter, havePatrol, xDest, yDest)) {
            nextTurn.gameMenu.createPane(nextTurn.gameMenu.anchorPaneMain);
            if (unit.getXPos() == xDest && unit.getYPos() == yDest)
                unit.setNotMoved();
            this.stop();
        }
        else {
            nextTurn.gameMenu.createPane(nextTurn.gameMenu.anchorPaneMain);
            counter++;
        }
    }
}
