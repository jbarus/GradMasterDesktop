package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.Committee;
import com.github.jbarus.gradmasterdesktop.models.SolutionDTO;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalTime;


public class DetailsController {

    @FXML
    private Label calculationDetailsLabel;

    @FXML
    private Label calculationTimeDetails;

    @FXML
    private Button committeeDetailsButton;



    @FXML
    private Label dateLabel;

    @FXML
    private Button deleteCommitteButton;

    @FXML
    private Button editNegativeRelationButton1;

    @FXML
    private Button editPositiveRelationButton;

    @FXML
    private Button editStudentButton;

    @FXML
    private Button editUniversityEmployeeButton;

    @FXML
    private TableColumn<UniversityEmployee, Boolean> isHabilitatedColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<?, ?> negativeRelation1Column1;

    @FXML
    private TableColumn<?, ?> negativeRelation2Column1;

    @FXML
    private TableView<?> negativeRelationTable;

    @FXML
    private Label numberOfPeoplePerCommitteeLabel;

    @FXML
    private TableColumn<?, ?> positiveRelation1Column;

    @FXML
    private TableColumn<?, ?> positiveRelation2Column;

    @FXML
    private TableView<?> positiveRelationTable;

    @FXML
    private TableColumn<UniversityEmployee, Integer> prefferedCommitteeDurationColumn;

    @FXML
    private Button refreshCommitteeButton;

    @FXML
    private TableView<Committee> solutionTable;
    @FXML
    private TableColumn<Committee, String> committeeNameColumn;

    @FXML
    private Button startCalculationButton;

    @FXML
    private TableColumn<Student, String> studentFirstnameColumn;

    @FXML
    private TableColumn<Student, String> studentSecondNameColumn;

    @FXML
    private TableView<Student> studentTable;

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
    private TableColumn<?, ?> unassignedUniversityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<?, ?> unassignedUniversityEmployeeSecondnameColumn;

    @FXML
    private TableView<?> unassignedUniversityEmployeeTable;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> universityEmployeeTable;

    @FXML
    private Button uploadFilesButton;

    @FXML
    private Button uploadNegativeRelationButton1;

    @FXML
    private Button uploadPositiveRelationButton;

    @FXML
    public void initialize(){
        initializeUniversityEmployees();
        initializeStudents();
        initializeSolution();

    }

    private void initializeSolution() {
        SolutionDTO solutionDTO = HTTPRequests.getSolutionById(Context.getInstance().getId());
        committeeNameColumn.setCellValueFactory(cellData -> {
            int index = Context.getInstance().getCommittees().indexOf(cellData.getValue()) + 1;
            String name = "Komisja " + index;
            return new SimpleStringProperty(name);
        });

        if(solutionDTO.getCommittees() != null){
            Context.getInstance().getCommittees().addAll(solutionDTO.getCommittees());
        }

        solutionTable.setItems(Context.getInstance().getCommittees());

        if(solutionDTO.getUnassignedStudents() != null){
            Context.getInstance().getUnassignedStudents().addAll(solutionDTO.getUnassignedStudents());
        }
        unassignedStudentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        unassignedStudentSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        unassignedStudentTable.setItems(Context.getInstance().getUnassignedStudents());
    }

    private void initializeStudents() {
        studentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentSecondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        studentTable.getItems().addAll(FXCollections.observableArrayList(HTTPRequests.getStudentsById(Context.getInstance().getId())));
    }

    private void initializeUniversityEmployees() {
        universityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        universityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        isHabilitatedColumn.setCellValueFactory(new PropertyValueFactory<>("habilitated"));
        timeslotStartColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotStart"));
        timeslotEndColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotEnd"));
        prefferedCommitteeDurationColumn.setCellValueFactory(new PropertyValueFactory<>("preferredCommitteeDuration"));
        universityEmployeeTable.getItems().addAll(FXCollections.observableArrayList(HTTPRequests.getUniversityEmployeesById(Context.getInstance().getId())));
    }

    @FXML
    void committeeDetailsButtonClicked(MouseEvent event) {

    }

    @FXML
    void deleteCommitteeButtonClicked(MouseEvent event) {

    }

    @FXML
    void editRelationButtonClicked(MouseEvent event) {

    }

    @FXML
    void editStudentButtonClicked(MouseEvent event) {

    }

    @FXML
    void editUniversityEmployeeButtonClicked(MouseEvent event) {

    }

    @FXML
    void refreshCommitteeButtonClicked(MouseEvent event) {

    }

    @FXML
    void startCalculationButtonClicked(MouseEvent event) {

    }

    @FXML
    void uploadFilesButtonClicked(MouseEvent event) {

    }

}
