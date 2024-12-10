package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.ProblemParameters;
import com.github.jbarus.gradmasterdesktop.models.communication.CalculationStartStatus;
import com.github.jbarus.gradmasterdesktop.models.communication.FullResponse;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import com.github.jbarus.gradmasterdesktop.models.dto.ProblemDTO;
import com.github.jbarus.gradmasterdesktop.models.dto.ProblemParametersDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProblemParametersUploadController {

    @FXML
    private TextField committeeSizeTextField;

    @FXML
    private TextField calculationTimeTextField;

    @FXML
    private Button startCalculationButton;

    @FXML
    void startCalculationButtonClicked(MouseEvent event) {
        ProblemParameters problemParameters = new ProblemParameters(Integer.parseInt(committeeSizeTextField.getText()),
                Integer.parseInt(committeeSizeTextField.getText()) - 1,
                Integer.parseInt(calculationTimeTextField.getText()));

        Response<ProblemParametersDTO> response = HTTPRequests.setProblemParametersByContextId(Context.getInstance().getId(),problemParameters);
        if(response != null && response.getHTTPStatusCode() < 300) {
            FullResponse<CalculationStartStatus, ProblemDTO> startResponse = HTTPRequests.startCalculationByContextId(Context.getInstance().getId());
            if(startResponse != null && startResponse.getHTTPStatusCode() == 200) {
                Stage currentStage = (Stage) startCalculationButton.getScene().getWindow();
                currentStage.close();
            }
        }
    }

}

