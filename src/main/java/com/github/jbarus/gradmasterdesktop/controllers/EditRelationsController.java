package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployeeRelation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;

public class EditRelationsController {

    @FXML
    private Button moveFromRelationListButton;

    @FXML
    private Button moveToRealtionListButton;

    @FXML
    private TableColumn<UniversityEmployee, String> relationFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> relationSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> relationTable;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeFirstnameColumn;

    @FXML
    private TableColumn<UniversityEmployee, String> universityEmployeeSecondnameColumn;

    @FXML
    private TableView<UniversityEmployee> universityEmployeeTable;

    @Setter
    private List<UniversityEmployeeRelation> selectedRelations;
    @Setter
    private boolean isNegativeRelationList;

    @FXML
    public void initialize() {
        relationFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        relationSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        universityEmployeeFirstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        universityEmployeeSecondnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));

    }

    public void loadData(){
        universityEmployeeTable.setItems(Context.getInstance().getUniversityEmployees());
        relationTable.setItems(FXCollections.observableList(UniversityEmployeeRelation.convertRelationToList(selectedRelations)));
    }

    @FXML
    void moveFromRelationListButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = relationTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            relationTable.getItems().remove(selectedUniversityEmployee);
        }
    }

    @FXML
    void moveToRealtionListButtonClicked(MouseEvent event) {
        UniversityEmployee selectedUniversityEmployee = universityEmployeeTable.getSelectionModel().getSelectedItem();
        if(selectedUniversityEmployee != null){
            relationTable.getItems().add(selectedUniversityEmployee);
        }
    }

    @FXML
    void saveButtonClicked(MouseEvent event) {
        if(!relationTable.getItems().isEmpty() && relationTable.getItems().size() % 2 == 0){
            if(isNegativeRelationList) {
                Context.getInstance().getNegativeRelations().clear();
                Context.getInstance().getNegativeRelations().addAll(UniversityEmployeeRelation.convertListToRelation(relationTable.getItems()));
            }else{
                Context.getInstance().getPositiveRelations().clear();
                Context.getInstance().getPositiveRelations().addAll(UniversityEmployeeRelation.convertListToRelation(relationTable.getItems()));
            }

            HTTPRequests.updateNegativeRelationByContextId(Context.getInstance().getId(), UniversityEmployeeRelation.convertRelationToList(Context.getInstance().getNegativeRelations()).stream().map(UniversityEmployee::getId).toList());
            HTTPRequests.updatePositiveRelationByContextId(Context.getInstance().getId(), UniversityEmployeeRelation.convertRelationToList(Context.getInstance().getPositiveRelations()).stream().map(UniversityEmployee::getId).toList());

            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.close();
        }
    }

}
