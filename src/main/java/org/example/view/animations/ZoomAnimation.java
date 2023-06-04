package org.example.view.animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

public class ZoomAnimation {
    private final Timeline timeline;

    public ZoomAnimation() {
        this.timeline = new Timeline(60);
    }

    public void zoom(Node node, double factor, double x, double y) {
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        double f = (scale / oldScale) - 1;
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
        timeline.getKeyFrames().clear();
        if (node.getScaleX() < 2.5 && factor > 1 || node.getScaleX() > 1 &&factor < 1 )
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
            );
        timeline.play();
    }
}
