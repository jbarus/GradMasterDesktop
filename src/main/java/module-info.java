module com.github.jbarus.gradmasterdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.desktop;


    opens com.github.jbarus.gradmasterdesktop to javafx.fxml;
    exports com.github.jbarus.gradmasterdesktop;
    exports com.github.jbarus.gradmasterdesktop.communication;
    exports com.github.jbarus.gradmasterdesktop.models;
    opens com.github.jbarus.gradmasterdesktop.communication to javafx.fxml;
    exports com.github.jbarus.gradmasterdesktop.controllers;
    opens com.github.jbarus.gradmasterdesktop.controllers to javafx.fxml;
}