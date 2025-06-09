package org.example.jewelryshop.controllers.metal;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.models.metals;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MetalsWindowController implements Initializable{

    @FXML
    private TableColumn<metals,String> MetalIdTC;

    @FXML
    private Menu MenuItemsData;

    @FXML
    private Menu MenuItemsInstruments;

    @FXML
    private TableColumn<metals,String> SampleTC;

    @FXML
    private TextField TFSearch;

    @FXML
    private TableView<metals> TWMetal;

    @FXML
    private TableColumn<metals,String> TitleTC;

    @FXML
    private Label labelCurrentCount;

    private List<metals> metalsList = Manager.metalServices.findAll();

    @FXML
    final Integer currentCount = metalsList.size();

    @FXML
    void MenuItemBackAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) TFSearch.getScene().getWindow();
        currentStage.close();
        Manager.ShowWindow("products-view.fxml","Изделия");
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        metals metalSelect = TWMetal.getSelectionModel().getSelectedItem();
        if(metalSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы удалить металл, выделите его!", Alert.AlertType.INFORMATION);
        else{
            metals.WarningMessageDelete(metalSelect);
            initData();
        }
    }

    @FXML
    public void MenuItemCreateMetalAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowEditWindow("create-metal-view.fxml","Добавить");
    }

    @FXML
    void ShowEditMetalWindow() {
        metals metalSelect = TWMetal.getSelectionModel().getSelectedItem();
        if(metalSelect == null)
            Manager.MessageBox("Редактирование","Внимание!","Для того, чтобы редактировать металл, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("edit-metal-view.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add("base-styles.css");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            newWindow.setTitle("Изменить данные");
            newWindow.initOwner(Manager.secondStage);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setScene(scene);
            Manager.currentStage = newWindow;
            newWindow.showAndWait();
            Manager.currentStage = null;
            initData();
        }
    }

    @FXML
    void MenuItemUpdateAction(ActionEvent event) {
        metals metalSelect = TWMetal.getSelectionModel().getSelectedItem();
        Manager.currentMetal = metalSelect;
        ShowEditMetalWindow();
    }

    void updateData(List<metals> metalsList){
        TWMetal.getItems().clear();
        ObservableList<metals> trips = FXCollections.observableArrayList(metalsList);
        TWMetal.setItems(trips);
        labelCurrentCount.setText(metalsList.size() + " записей из " + currentCount);
    }

    private void initData(){
        if(TFSearch.getText().isEmpty()) {
            metalsList = Manager.metalServices.findAll();
        }
        updateData(metalsList);
        labelCurrentCount.setText(metalsList.size() + " записей из " + currentCount);
    }

    @FXML
    void TFSearchAction(ActionEvent event) {
        String filter = TFSearch.getText().toLowerCase();
        List<metals> filteredList = Manager.metalServices.findAll().stream()
                .filter(metal -> metal.getTitle().toLowerCase().contains(filter))
                .toList();
        updateData(filteredList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        MetalIdTC.setCellValueFactory(cellData -> {
            Object id = cellData.getValue().getMetalId();
            return new SimpleStringProperty(id != null ? id.toString() : "");
        });
        TitleTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        SampleTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSample()));
    }
}
