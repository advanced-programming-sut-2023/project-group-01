module strongholdCrusader {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires com.google.gson;
    requires passay;

    opens org.example to javafx.fxml;
    exports org.example;
    opens org.example.controller;
    exports org.example.controller;
    opens org.example.model to com.google.gson;
    opens org.example.model.building to com.google.gson;
    exports org.example.model.building.castleBuilding.enums to com.google.gson;
    opens org.example.model.unit to com.google.gson;
    exports org.example.model.unit.enums to com.google.gson;
    exports org.example.model.enums to com.google.gson;
    exports org.example.model.building.enums to com.google.gson;
    exports org.example.view;

    exports org.example.view.graphicView;
    opens org.example.view.graphicView to javafx.fxml;
    exports org.example.controller.mainMenuController;
    opens  org.example.controller.mainMenuController to javafx.fxml;
}