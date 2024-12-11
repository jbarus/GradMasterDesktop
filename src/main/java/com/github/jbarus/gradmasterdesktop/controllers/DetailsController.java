package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.GradMasterDesktopApplication;
import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.*;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import com.github.jbarus.gradmasterdesktop.models.dto.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;


public class DetailsController {

    @FXML
    private Label calculationDetailsLabel;

    @FXML
    private Label calculationTimeDetails;

    @FXML
    private Button committeeDetailsButton;

    @FXML
    private Button backButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Button deleteCommitteButton;

    @FXML
    private Button editNegativeRelationButton;

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
    private TableColumn<UniversityEmployeeRelation, String> negativeRelation1Column;

    @FXML
    private TableColumn<UniversityEmployeeRelation, String> negativeRelation2Column;

    @FXML
    private TableView<UniversityEmployeeRelation> negativeRelationTable;

    @FXML
    private Label numberOfPeoplePerCommitteeLabel;

    @FXML
    private TableColumn<UniversityEmployeeRelation, String> positiveRelation1Column;

    @FXML
    private TableColumn<UniversityEmployeeRelation, String> positiveRelation2Column;

    @FXML
    private TableView<UniversityEmployeeRelation> positiveRelationTable;

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
    private TableColumn<UniversityEmployee, String> unassignedUniversityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> unassignedUniversityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> unassignedUniversityEmployeeTable;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> universityEmployeeTable;

    @FXML
    private Button uploadFilesButton;

    @FXML
    public void initialize(){
        initializeUniversityEmployees();
        initializeStudents();
        initializeSolution();
        initializeRelations();
        initializeProblemParameters();
        dateLabel.setText("Data: " + Context.getInstance().getDate().toString());
        nameLabel.setText("Name: " + Context.getInstance().getName());

    }

    private void initializeProblemParameters() {
        Response<ProblemParametersDTO> problemParametersDTOResponse = HTTPRequests.getProblemParametersByContextId(Context.getInstance().getId());

        if(problemParametersDTOResponse != null && problemParametersDTOResponse.getHTTPStatusCode() < 300){
            numberOfPeoplePerCommitteeLabel.setText("Liczba pracowników w komisji: " + problemParametersDTOResponse.getBody().getCommitteeSize());
            calculationTimeDetails.setText("Czas obliczeń: " + problemParametersDTOResponse.getBody().getCalculationTimeInSeconds());
        }
    }

    private void initializeRelations() {
        negativeRelation1Column.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getUniversityEmployee1().getFirstName() + " " + cellData.getValue().getUniversityEmployee1().getSecondName()
                )
        );

        negativeRelation2Column.setCellValueFactory(cellData -> {
            UniversityEmployee employee2 = cellData.getValue().getUniversityEmployee2();
            return new SimpleStringProperty(
                    employee2 != null ? employee2.getFirstName() + " " + employee2.getSecondName() : "N/A"
            );
        });

        positiveRelation1Column.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getUniversityEmployee1().getFirstName() + " " + cellData.getValue().getUniversityEmployee1().getSecondName()
                )
        );

        positiveRelation2Column.setCellValueFactory(cellData -> {
            UniversityEmployee employee2 = cellData.getValue().getUniversityEmployee2();
            return new SimpleStringProperty(
                    employee2 != null ? employee2.getFirstName() + " " + employee2.getSecondName() : "N/A"
            );
        });

        Response<RelationDTO> negativeRelationResponse = HTTPRequests.getNegativeRelationsByContextId(Context.getInstance().getId());

        if(negativeRelationResponse != null && negativeRelationResponse.getHTTPStatusCode() < 300 ){
            Context.getInstance().getNegativeRelations().addAll(UniversityEmployeeRelation.convertListToRelation(negativeRelationResponse.getBody().getRelationList()));
        }

        negativeRelationTable.setItems(Context.getInstance().getNegativeRelations());

        Response<RelationDTO> positiveRelationResponse = HTTPRequests.getPositiveRelationsByContextId(Context.getInstance().getId());

        if(positiveRelationResponse != null && positiveRelationResponse.getHTTPStatusCode() < 300 ){
            Context.getInstance().getPositiveRelations().addAll(UniversityEmployeeRelation.convertListToRelation(positiveRelationResponse.getBody().getRelationList()));
        }

        positiveRelationTable.setItems(Context.getInstance().getPositiveRelations());
    }

    private void initializeSolution() {

        committeeNameColumn.setCellValueFactory(cellData -> {
            int index = Context.getInstance().getCommittees().indexOf(cellData.getValue()) + 1;
            String name = "Komisja " + index;
            return new SimpleStringProperty(name);
        });

        unassignedStudentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        unassignedStudentSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));

        unassignedUniversityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        unassignedUniversityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));

        Response<SolutionDTO> response =  HTTPRequests.getSolutionByContextId(Context.getInstance().getId());

        if(response != null && response.getHTTPStatusCode() < 300) {
            if(response.getBody().getCommittees() != null) {
                Context.getInstance().getCommittees().addAll(response.getBody().getCommittees());
            }
            if(response.getBody().getUnassignedStudents() != null) {
                Context.getInstance().getUnassignedStudents().addAll(response.getBody().getUnassignedStudents());
            }
            if(response.getBody().getUnassignedUniversityEmployees() != null){
                Context.getInstance().getUnassignedUniversityEmployee().addAll(response.getBody().getUnassignedUniversityEmployees());
            }
        }

        solutionTable.setItems(Context.getInstance().getCommittees());
        unassignedStudentTable.setItems(Context.getInstance().getUnassignedStudents());
        unassignedUniversityEmployeeTable.setItems(Context.getInstance().getUnassignedUniversityEmployee());
    }

    private void initializeStudents() {
        studentFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentSecondNameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));

        Response<StudentDTO> response =  HTTPRequests.getStudentsByContextId(Context.getInstance().getId());

        if(response != null && response.getHTTPStatusCode() < 300 ){
            Context.getInstance().getStudents().addAll(response.getBody().getStudents());
        }

        studentTable.setItems(Context.getInstance().getStudents());
    }

    private void initializeUniversityEmployees() {
        universityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        universityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        isHabilitatedColumn.setCellValueFactory(new PropertyValueFactory<>("habilitated"));
        timeslotStartColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotStart"));
        timeslotEndColumn.setCellValueFactory(new PropertyValueFactory<>("timeslotEnd"));
        prefferedCommitteeDurationColumn.setCellValueFactory(new PropertyValueFactory<>("preferredCommitteeDuration"));

        Response<UniversityEmployeeDTO> response =  HTTPRequests.getUniversityEmployeeByContextId(Context.getInstance().getId());

        if(response != null && response.getHTTPStatusCode() < 300 ){
            Context.getInstance().getUniversityEmployees().addAll(response.getBody().getUniversityEmployees());
        }

        universityEmployeeTable.setItems(Context.getInstance().getUniversityEmployees());
    }

    @FXML
    void committeeDetailsButtonClicked(MouseEvent event) {
        Committee selectedItem = solutionTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null){
            Context.getInstance().setSelectedCommittee(selectedItem);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("edit-committee-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("GradMaster");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void deleteCommitteeButtonClicked(MouseEvent event) {
        Committee committee = solutionTable.getSelectionModel().getSelectedItem();
        if(committee != null){
            Context.getInstance().getCommittees().remove(committee);
            Solution solution = new Solution();
            solution.setCommittees(Context.getInstance().getCommittees());
            solution.setUnassignedStudents(Context.getInstance().getUnassignedStudents());
            solution.setUnassignedUniversityEmployees(Context.getInstance().getUnassignedUniversityEmployee());
            HTTPRequests.updateSolutionByContextId(Context.getInstance().getId(), solution);
        }
    }

    @FXML
    void editStudentButtonClicked(MouseEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("edit-student-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                EditStudentController controller = fxmlLoader.getController();

                controller.setSelectedStudent(selectedStudent);
                controller.loadData();

                Stage stage = new Stage();
                stage.setTitle("GradMaster");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void editUniversityEmployeeButtonClicked(MouseEvent event) {
        UniversityEmployee universityEmployee = universityEmployeeTable.getSelectionModel().getSelectedItem();
        if(universityEmployee != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("edit-university-employee-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                EditUniversityEmployeeController controller = fxmlLoader.getController();

                controller.setSelectedUniversityEmployee(universityEmployee);
                controller.loadData();

                Stage stage = new Stage();
                stage.setTitle("GradMaster");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void refreshCommitteeButtonClicked(MouseEvent event) {
        Context.getInstance().clearSolution();
        Response<SolutionDTO> response = HTTPRequests.getSolutionByContextId(Context.getInstance().getId());

        if(response != null && response.getHTTPStatusCode() < 300 ){
            Context.getInstance().getCommittees().addAll(response.getBody().getCommittees());
            Context.getInstance().getUnassignedStudents().addAll(response.getBody().getUnassignedStudents());
        }
    }

    @FXML
    void startCalculationButtonClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("problem-parameters-upload-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("GradMaster");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void uploadFilesButtonClicked(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("university-employee-file-upload-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            UniversityEmployeeFileUploadController controller = fxmlLoader.getController();
            controller.setContextId(Context.getInstance().getId());

            Stage stage = new Stage();
            stage.setTitle("GradMaster");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPositiveRelationButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("edit-relations-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            EditRelationsController controller = fxmlLoader.getController();
            controller.setSelectedRelations(positiveRelationTable.getItems());
            controller.setNegativeRelationList(false);
            controller.loadData();

            Stage stage = new Stage();
            stage.setTitle("GradMaster");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void editNegativeRelationButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("edit-relations-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            EditRelationsController controller = fxmlLoader.getController();
            controller.setSelectedRelations(negativeRelationTable.getItems());
            controller.setNegativeRelationList(true);
            controller.loadData();

            Stage stage = new Stage();
            stage.setTitle("GradMaster");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void backButtonClicked(MouseEvent event) {
        try {
            Context.getInstance().clearAll();
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("welcome-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("GradMaster");
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
