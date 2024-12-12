package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.Solution;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import com.github.jbarus.gradmasterdesktop.models.dto.SolutionDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalTime;

public class EditCommitteeController {

    @FXML
    private TableColumn<Student, String> studentFirstnameColumn;

    @FXML
    private TableColumn<Student, String> studentSecondnameColumn;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<UniversityEmployee, Boolean> isHabilitatedColumn;

    @FXML
    private Button moveStudentFromAssignedToUnassignedButton;

    @FXML
    private Button moveStudentFromUnassignedToAssigned;

    @FXML
    private Button moveUniversityEmployeeFromAssingedToUnassignedButton;

    @FXML
    private Button moveUniversityEmployeeFromUnassingedToAssignedButton;

    @FXML
    private TableColumn<UniversityEmployee, Integer> prefferedCommitteeDurationColumn;

    @FXML
    private TableColumn<UniversityEmployee, LocalTime> timeslotEndColumn;

    @FXML
    private TableColumn<UniversityEmployee, LocalTime> timeslotStartColumn;

    @FXML
    private TableColumn<Student, String> unassignedStudentFirstnameColumn;

    @FXML
    private TableColumn<Student, String> unassignedStudentSecondnameColumn;

    @FXML
    private TableView<Student> unassignedStudentTable;

    @FXML
    private TableColumn<UniversityEmployee, String> unassignedUniversityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, Boolean> unassignedUniversityEmployeeIsHabilitatedColumn;

    @FXML
    private TableColumn<UniversityEmployee, Integer> unassignedUniversityEmployeePrefferedCommitteeDurationColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> unassignedUniversityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> unassignedUniversityEmployeeTable;

    @FXML
    private TableColumn<UniversityEmployee, LocalTime> unassignedUniversityEmployeeTimeslotEndColumn;

    @FXML
    private TableColumn<UniversityEmployee, LocalTime> unassignedUniversityEmployeeTimeslotStartColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> universityEmployeeTable;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize(){
        initializeUniversityEmployees();
        initializeStudents();
        initializeUnassignedUniversityEmployee();
        initializeUnassignedStudents();

    }

    private void initializeUnassignedStudents() {
        unassignedStudentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        unassignedStudentSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        unassignedStudentTable.setItems(Context.getInstance().getUnassignedStudents());
    }

    private void initializeUnassignedUniversityEmployee() {
        unassignedUniversityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        unassignedUniversityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        unassignedUniversityEmployeeIsHabilitatedColumn.setCellValueFactory(new PropertyValueFactory<>("habilitated"));
        unassignedUniversityEmployeeTimeslotStartColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotStart"));
        unassignedUniversityEmployeeTimeslotEndColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotEnd"));
        unassignedUniversityEmployeePrefferedCommitteeDurationColumn.setCellValueFactory(new PropertyValueFactory<>("preferredCommitteeDuration"));
        unassignedUniversityEmployeeTable.setItems(Context.getInstance().getUnassignedUniversityEmployee());
    }

    private void initializeStudents() {
        studentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        studentTable.getItems().addAll(FXCollections.observableArrayList(Context.getInstance().getSelectedCommittee().getStudents()));
    }

    private void initializeUniversityEmployees() {
        universityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        universityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        isHabilitatedColumn.setCellValueFactory(new PropertyValueFactory<>("habilitated"));
        timeslotStartColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotStart"));
        timeslotEndColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotEnd"));
        prefferedCommitteeDurationColumn.setCellValueFactory(new PropertyValueFactory<>("preferredCommitteeDuration"));
        universityEmployeeTable.getItems().addAll(FXCollections.observableArrayList(Context.getInstance().getSelectedCommittee().getUniversityEmployees()));
    }

    @FXML
    void moveStudentFromAssignedToUnassignedButtonClicked(MouseEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            studentTable.getItems().remove(selectedStudent);
            Context.getInstance().getSelectedCommittee().getStudents().remove(selectedStudent);
            Context.getInstance().getUnassignedStudents().add(selectedStudent);
        }
    }

    @FXML
    void moveStudentFromUnassignedToAssignedButtonClicked(MouseEvent event) {
        Student selectedStudent = unassignedStudentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            studentTable.getItems().add(selectedStudent);
            Context.getInstance().getSelectedCommittee().getStudents().add(selectedStudent);
            Context.getInstance().getUnassignedStudents().remove(selectedStudent);
        }
    }

    @FXML
    void moveUniversityEmployeeFromAssingedToUnassignedButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = universityEmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            universityEmployeeTable.getItems().remove(selectedUniversityEmployee);
            Context.getInstance().getSelectedCommittee().getUniversityEmployees().remove(selectedUniversityEmployee);
            Context.getInstance().getUnassignedUniversityEmployee().add(selectedUniversityEmployee);
        }
    }

    @FXML
    void moveUniversityEmployeeFromUnassingedToAssignedButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = unassignedUniversityEmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            universityEmployeeTable.getItems().add(selectedUniversityEmployee);
            Context.getInstance().getSelectedCommittee().getUniversityEmployees().add(selectedUniversityEmployee);
            Context.getInstance().getUnassignedUniversityEmployee().remove(selectedUniversityEmployee);
        }
    }

    @FXML
    void saveButtonClicked(MouseEvent event){
        Solution solution = new Solution();
        solution.setCommittees(Context.getInstance().getCommittees());
        solution.setUnassignedStudents(Context.getInstance().getUnassignedStudents());
        solution.setUnassignedUniversityEmployees(Context.getInstance().getUnassignedUniversityEmployee());

        HTTPRequests.updateSolutionByContextId(Context.getInstance().getId(), solution);
        Stage currentStage = (Stage) saveButton.getScene().getWindow();
        currentStage.close();
    }

}
