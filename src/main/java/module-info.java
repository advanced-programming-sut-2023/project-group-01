module strongholdCrusader {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.google.gson;
    requires passay;

    exports org.example.view.graphicView;
    opens org.example.view.graphicView to javafx.fxml;
}