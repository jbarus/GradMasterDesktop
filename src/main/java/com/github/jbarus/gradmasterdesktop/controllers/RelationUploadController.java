package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployeeRelation;
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
import java.util.stream.Collectors;

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
        ObservableList<UniversityEmployee> universityEmployees = FXCollections.observableList(HTTPRequests.getUniversityEmployeesById(contextId));
        negativeUniversityEmployeeTable.setItems(universityEmployees);
        positiveUniversityEmployeeTable.setItems(universityEmployees);
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
        boolean negativeResponse = false;
        boolean positiveResponse = false;
        if(checkValidity(negativeRelations,positiveRelations)){
            System.out.println("Haloooo");
            positiveResponse = HTTPRequests.uploadPositiveRelation(contextId, positiveRelations.stream().map(UniversityEmployee::getId).toList());
            negativeResponse = HTTPRequests.uploadNegativeRelation(contextId, negativeRelations.stream().map(UniversityEmployee::getId).toList());
        }
        if(negativeResponse && positiveResponse){
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
            Context.getInstance().getUniversityEmployees().addAll(HTTPRequests.getUniversityEmployeesById(contextId));
            Context.getInstance().getStudents().addAll(HTTPRequests.getStudentsById(contextId));
            Context.getInstance().getPositiveRelations().addAll(UniversityEmployeeRelation.convertListToRelation(positiveRelationTable.getItems()));
            Context.getInstance().getNegativeRelations().addAll(UniversityEmployeeRelation.convertListToRelation(negativeRelationTable.getItems()));
        }
    }

}
