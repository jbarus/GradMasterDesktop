module com.github.jbarus.gradmasterdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.jbarus.gradmasterdesktop to javafx.fxml;
    exports com.github.jbarus.gradmasterdesktop;
}