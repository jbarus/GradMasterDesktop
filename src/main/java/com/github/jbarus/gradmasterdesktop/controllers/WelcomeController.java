package com.github.jbarus.gradmasterdesktop.controllers;

import com.github.jbarus.gradmasterdesktop.GradMasterDesktopApplication;
import com.github.jbarus.gradmasterdesktop.communication.HTTPRequests;
import com.github.jbarus.gradmasterdesktop.context.Context;
import com.github.jbarus.gradmasterdesktop.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterdesktop.models.communication.Response;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        deleteButton.setDisable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.prefWidthProperty().bind(problemTable.widthProperty().multiply(0.75));
        dateColumn.prefWidthProperty().bind(problemTable.widthProperty().multiply(0.25));
        Response<List<ContextDisplayInfoDTO>> response =  HTTPRequests.getAllContextDisplayInfos();

       if(response != null && response.getHTTPStatusCode() < 300 ){
           Context.getInstance().getContextDisplayInfoDTOS().addAll(FXCollections.observableArrayList(response.getBody()));
       }
       problemTable.setItems(Context.getInstance().getContextDisplayInfoDTOS());
    }

    @FXML
    void addButtonClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("create-context-view.fxml"));
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
    void openButtonClicked(ActionEvent event) {
        ContextDisplayInfoDTO selectedItem = problemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Context.getInstance().setId(selectedItem.getId());
            Context.getInstance().setName(selectedItem.getName());
            Context.getInstance().setDate(selectedItem.getDate());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(GradMasterDesktopApplication.class.getResource("details-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("GradMaster");
                stage.setScene(scene);
                stage.show();

                Stage currentStage = (Stage) openButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    public void itemSelected(MouseEvent mouseEvent) {
        if(problemTable.getSelectionModel().getSelectedItem() != null) {
            openButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        ContextDisplayInfoDTO selectedItem = problemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Response<Void> response = HTTPRequests.deleteContextDisplayInfoByContextId(selectedItem.getId());
            if(response != null && response.getHTTPStatusCode() < 300) {
                problemTable.getItems().remove(selectedItem);
            }
        }
    }
}