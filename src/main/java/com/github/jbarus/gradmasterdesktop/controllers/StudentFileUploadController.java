package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.GradMasterDesktopApplication;
import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import com.github.jbarus.gradmasterdesktop.models.dto.StudentDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StudentFileUploadController {

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Button nextButton;

    private File selectedFile;

    @Setter
    private UUID contextId;

    @FXML
    void chooseFileButtonClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki programu Excel", "*.xlsx")
        );

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            nameLabel.setText("Wybrany plik: " + selectedFile.getName());
        }
    }

    @FXML
    void nextButtonClicked(MouseEvent event) {
        Response<StudentDTO> response = null;

        if (selectedFile != null) {
            response = HTTPRequests.uploadStudentFileByContextId(contextId, selectedFile);
        }

        if(response != null && response.getHTTPStatusCode() < 300){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("relation-upload-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                RelationUploadController controller = fxmlLoader.getController();
                controller.setContextId(contextId);
                controller.loadData();

                Stage stage = new Stage();
                stage.setTitle("GradMaster");
                stage.setScene(scene);
                stage.show();

                Stage currentStage = (Stage) nextButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

