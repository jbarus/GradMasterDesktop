package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployeeDTO;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployeeRelation;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import com.github.jbarus.gradmasterdesktop.models.dto.RelationDTO;
import com.github.jbarus.gradmasterdesktop.models.dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class RelationUploadController {

    @FXML
    private Button moveFromNegativeRelationButton;

    @FXML
    private Button moveFromPositiveRelationButton;

    @FXML
    private Button moveToNegativeRelationButton;

    @FXML
    private Button moveToPositiveRelationButton;

    @FXML
    private TableColumn<UniversityEmployee, String> negativeRelationFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> negativeRelationSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> negativeRelationTable;

    @FXML
    private TableColumn<UniversityEmployee, String> negativeUniversityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> negativeUniversityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> negativeUniversityEmployeeTable;

    @FXML
    private Button nextButton;

    @FXML
    private TableColumn<UniversityEmployee, String> positiveRelationFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> positiveRelationSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> positiveRelationTable;

    @FXML
    private TableColumn<UniversityEmployee, String> positiveUniversityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> positiveUniversityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> positiveUniversityEmployeeTable;

    @Setter
    private UUID contextId;

    @FXML
    public void initialize(){
        negativeUniversityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        negativeUniversityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        positiveUniversityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        positiveUniversityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        negativeRelationFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        negativeRelationSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        positiveRelationFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        positiveRelationSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
    }

    public void loadData(){
        Response<UniversityEmployeeDTO> response = HTTPRequests.getUniversityEmployeeByContextId(contextId);
        if(response != null && response.getHTTPStatusCode() < 300 ){
            ObservableList<UniversityEmployee> universityEmployees = FXCollections.observableList(response.getBody().getUniversityEmployees());
            negativeUniversityEmployeeTable.setItems(universityEmployees);
            positiveUniversityEmployeeTable.setItems(universityEmployees);
        }
    }

    @FXML
    void moveFromNegativeRelationButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = negativeRelationTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            negativeRelationTable.getItems().remove(selectedUniversityEmployee);
        }
    }

    @FXML
    void moveFromPositiveRelationButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = positiveRelationTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            positiveRelationTable.getItems().remove(selectedUniversityEmployee);
        }
    }

    @FXML
    void moveToNegativeRelationButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = negativeUniversityEmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            negativeRelationTable.getItems().add(selectedUniversityEmployee);
        }
    }

    @FXML
    void moveToPositiveRelationButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = positiveUniversityEmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            positiveRelationTable.getItems().add(selectedUniversityEmployee);
        }
    }

    @FXML
    void nextButtonClicked(MouseEvent event) {
        List<UniversityEmployee> negativeRelations = negativeRelationTable.getItems();
        List<UniversityEmployee> positiveRelations = positiveRelationTable.getItems();
        Response<RelationDTO> negativeResponse = null;
        Response<RelationDTO> positiveResponse = null;
        if(checkValidity(negativeRelations,positiveRelations)){
            positiveResponse = HTTPRequests.addPositiveRelationByContextId(contextId, positiveRelations.stream().map(UniversityEmployee::getId).toList());
            negativeResponse = HTTPRequests.addNegativeRelationByContextId(contextId, negativeRelations.stream().map(UniversityEmployee::getId).toList());
        }
        if(negativeResponse != null && negativeResponse.getHTTPStatusCode() < 300 && positiveResponse != null && positiveResponse.getHTTPStatusCode() < 300){
            updateContext();
            Stage currentStage = (Stage) nextButton.getScene().getWindow();
            currentStage.close();
        }
    }

    private boolean checkValidity(List<UniversityEmployee> negativeRelations, List<UniversityEmployee> positiveRelations) {
        if(negativeRelations.isEmpty() || positiveRelations.isEmpty()){
            return false;
        }
        if(negativeRelations.size() % 2 != 0 || positiveRelations.size() % 2 != 0){
            return false;
        }
        return true;
    }

    private void updateContext(){
        if(Context.getInstance().getId() != null){
            Context.getInstance().clearProblemData();
            Context.getInstance().setId(contextId);

            Response<UniversityEmployeeDTO> universityEmployeeResponse = HTTPRequests.getUniversityEmployeeByContextId(contextId);
            if(universityEmployeeResponse != null && universityEmployeeResponse.getHTTPStatusCode() < 300){
                Context.getInstance().getUniversityEmployees().addAll(universityEmployeeResponse.getBody().getUniversityEmployees());
            }

            Response<StudentDTO> studentResponse = HTTPRequests.getStudentsByContextId(contextId);
            if(studentResponse != null && studentResponse.getHTTPStatusCode() < 300){
                Context.getInstance().getStudents().addAll(studentResponse.getBody().getStudents());
            }

            Context.getInstance().getPositiveRelations().addAll(UniversityEmployeeRelation.convertListToRelation(positiveRelationTable.getItems()));
            Context.getInstance().getNegativeRelations().addAll(UniversityEmployeeRelation.convertListToRelation(negativeRelationTable.getItems()));
        }
    }

}
