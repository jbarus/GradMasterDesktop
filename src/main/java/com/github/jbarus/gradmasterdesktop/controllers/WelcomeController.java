package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.GradMasterDesktopApplication;
import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button deleteButton;

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
            Context.getInstance().setId(selectedItem.getId());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("details-view.fxml"));
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
    public void itemSelected(MouseEvent mouseEvent) {
        if(problemTable.getSelectionModel().getSelectedItem() != null) {
            openButton.setDisable(false);
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        ContextDisplayInfoDTO selectedItem = problemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if(HTTPRequests.deleteContext(selectedItem.getId())){
                problemTable.getItems().remove(selectedItem);
            }
        }
    }
}