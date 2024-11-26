package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.ConfigurationKeys;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class EditUniversityEmployeeController {

    @FXML
    private TextField firstnameTextField;

    @FXML
    private CheckBox isHabilitatedCheckBox;

    @FXML
    private TextField preferredCommitteeDuration;

    @FXML
    private Button saveButton;

    @FXML
    private TextField secondnameTextField;

    @FXML
    private TextField timeslotEndTextField;

    @FXML
    private TextField timeslotStartTextField;

    @Setter
    private UniversityEmployee selectedUniversityEmployee;

    @FXML
    void saveButtonClicked(MouseEvent event) {
        UniversityEmployee universityEmployee = new UniversityEmployee();
        universityEmployee.setId(selectedUniversityEmployee.getId());
        universityEmployee.setFirstName(firstnameTextField.getText());
        universityEmployee.setSecondName(secondnameTextField.getText());
        universityEmployee.setHabilitated(isHabilitatedCheckBox.isSelected());
        universityEmployee.setTimeslotStart(LocalTime.parse(timeslotStartTextField.getText()));
        universityEmployee.setTimeslotEnd(LocalTime.parse(timeslotEndTextField.getText()));
        universityEmployee.setPreferredCommitteeDuration(Integer.parseInt(preferredCommitteeDuration.getText()));
        int index = Context.getInstance().getUniversityEmployees().indexOf(selectedUniversityEmployee);
        Context.getInstance().getUniversityEmployees().set(index, universityEmployee);

        HTTPRequests.updateUniversityEmployeeById(Context.getInstance().getId(), Context.getInstance().getUniversityEmployees());

        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();
    }

    public void loadData(){
        firstnameTextField.setText(selectedUniversityEmployee.getFirstName());
        secondnameTextField.setText(selectedUniversityEmployee.getSecondName());
        timeslotStartTextField.setText(selectedUniversityEmployee.getTimeslotStart().toString());
        timeslotEndTextField.setText(selectedUniversityEmployee.getTimeslotEnd().toString());
        preferredCommitteeDuration.setText(String.valueOf(selectedUniversityEmployee.getPreferredCommitteeDuration()));
        isHabilitatedCheckBox.setSelected(selectedUniversityEmployee.isHabilitated());
    }

}
