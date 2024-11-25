package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.GradMasterDesktopApplication;
import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class CreateContextController {

    @FXML
    private Button nextButton;

    @FXML
    private DatePicker problemDateDatePicker;

    @FXML
    private TextField problemNameTextField;

    @FXML
    void nextButtonClicked(MouseEvent event) {
        String problemName = problemNameTextField.getText();
        LocalDate problemDate = problemDateDatePicker.getValue();


        if (!problemName.isEmpty() && problemDate != null) {
            ContextDisplayInfoDTO context = ContextDisplayInfoDTO.builder()
                    .name(problemName)
                    .date(problemDate)
                    .build();
            UUID id = HTTPRequests.createProblemContext(context);
            if(id != null) {
                Context.getInstance().getContextDisplayInfoDTOS().add(context);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("university-employee-file-upload-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    UniversityEmployeeFileUploadController controller = fxmlLoader.getController();
                    controller.setContextId(id);

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

}
