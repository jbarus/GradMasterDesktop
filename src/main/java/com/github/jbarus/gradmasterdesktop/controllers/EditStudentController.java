package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import com.github.jbarus.gradmasterdesktop.models.dto.StudentDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.stream.Collectors;

public class EditStudentController {
    @FXML
    private TextField firstnameTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField secondnameTextField;

    @Setter
    private Student selectedStudent;

    @FXML
    void saveButtonClicked(MouseEvent event) {
        Student student = new Student();
        student.setId(selectedStudent.getId());
        student.setFirstName(firstnameTextField.getText());
        student.setSecondName(secondnameTextField.getText());
        int index = Context.getInstance().getStudents().indexOf(selectedStudent);
        Context.getInstance().getStudents().set(index, student);
        Response<StudentDTO> response = HTTPRequests.updateStudentsByContextId(Context.getInstance().getId(), Context.getInstance().getStudents());
        System.out.println(response.getHTTPStatusCode());
        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();
    }

    public void loadData(){
        firstnameTextField.setText(selectedStudent.getFirstName());
        secondnameTextField.setText(selectedStudent.getSecondName());
    }
}
