package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.ProblemParameters;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

        boolean result = HTTPRequests.uploadProblemParameters(Context.getInstance().getId(),problemParameters);
        if(result) {
            HTTPRequests.startCalculation(Context.getInstance().getId());
        }
    }

}

