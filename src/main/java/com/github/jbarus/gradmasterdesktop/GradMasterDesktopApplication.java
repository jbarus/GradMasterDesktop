package com.github.jbarus.gradmasterdesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GradMasterDesktopApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GradMaster");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}