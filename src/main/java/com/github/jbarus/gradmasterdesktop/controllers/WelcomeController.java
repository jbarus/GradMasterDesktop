package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.Date;

public class WelcomeController {
    @FXML
    private Button addButton;

    @FXML
    private Button openButton;

    @FXML
    private TableView<ContextDisplayInfoDTO> problemTable;

    @FXML
    private TableColumn<ContextDisplayInfoDTO, String> nameColumn;
    @FXML
    private TableColumn<ContextDisplayInfoDTO, LocalDate> dateColumn;

    @FXML
    public void initialize(){
        openButton.setDisable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.prefWidthProperty().bind(problemTable.widthProperty().multiply(0.75));
        dateColumn.prefWidthProperty().bind(problemTable.widthProperty().multiply(0.25));
        problemTable.getItems().addAll(FXCollections.observableArrayList(HTTPRequests.getAllContextDisplayInfo()));
    }

    @FXML
    void addButtonClicked(ActionEvent event) {

    }

    @FXML
    void openButtonClicked(ActionEvent event) {
        ContextDisplayInfoDTO selectedItem = problemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            System.out.println("Zaznaczony obiekt: " + selectedItem.getId());
        } else {
            System.out.println("Brak zaznaczonego obiektu.");
        }
    }

    @FXML
    public void itemSelected(MouseEvent mouseEvent) {
        if(problemTable.getSelectionModel().getSelectedItem() != null) {
            openButton.setDisable(false);
        }
    }
}