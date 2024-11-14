package com.github.jbarus.gradmasterdesktop;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
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
        System.out.println(HTTPRequests.getAllContextDisplayInfo());
    }

    public static void main(String[] args) {
        launch();
    }
}