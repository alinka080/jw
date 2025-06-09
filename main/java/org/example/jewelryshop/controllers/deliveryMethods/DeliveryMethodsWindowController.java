package org.example.jewelryshop.controllers.deliveryMethods;

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
import org.example.jewelryshop.models.*;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryMethodsWindowController implements Initializable {

    @FXML
    private TableColumn<deliveryMethods,String> CostTC;

    @FXML
    private TableColumn<deliveryMethods,String> DMIdTC;

    @FXML
    private TableColumn<deliveryMethods,String> DescriptionTC;

    @FXML
    private Menu MenuItemsData;

    @FXML
    private Menu MenuItemsInstruments;

    @FXML
    private TextField TFSearch;

    @FXML
    private TableView<deliveryMethods> TWDeliveryMethods;

    @FXML
    private TableColumn<deliveryMethods,String> TitleTC;

    @FXML
    private Label labelCurrentCount;

    private List<deliveryMethods> deliveryMethodsList = Manager.deliveryMethodServices.findAll();

    @FXML
    final Integer currentCount = deliveryMethodsList.size();

    @FXML
    void MenuItemBackAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) TFSearch.getScene().getWindow();
        currentStage.close();
        Manager.ShowWindow("products-view.fxml","Изделия");
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        deliveryMethods deliveryMethodsSelect = TWDeliveryMethods.getSelectionModel().getSelectedItem();
        if(deliveryMethodsSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы удалить способ доставки, выделите его!", Alert.AlertType.INFORMATION);
        else{
            deliveryMethods.WarningMessageDelete(deliveryMethodsSelect);
            initData();
        }
    }

    @FXML
    public void MenuItemCreateDeliveryMethodsAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowEditWindow("create-delivery-methods-view.fxml","Добавить");
    }

    @FXML
    void ShowEditDeliveryMethodsWindow() {
        deliveryMethods deliveryMethodsSelect = TWDeliveryMethods.getSelectionModel().getSelectedItem();
        if(deliveryMethodsSelect == null)
            Manager.MessageBox("Редактирование","Внимание!","Для того, чтобы редактировать камень, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("delivery-methods-edit-view.fxml"));

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
        deliveryMethods deliveryMethodsSelect = TWDeliveryMethods.getSelectionModel().getSelectedItem();
        Manager.currentDeliveryMethods = deliveryMethodsSelect;
        ShowEditDeliveryMethodsWindow();
    }

    void updateData(List<deliveryMethods> deliveryMethods){
        TWDeliveryMethods.getItems().clear();
        ObservableList<deliveryMethods> trips = FXCollections.observableArrayList(deliveryMethods);
        TWDeliveryMethods.setItems(trips);
        labelCurrentCount.setText(deliveryMethods.size() + " записей из " + currentCount);
    }

    private void initData(){
        if(TFSearch.getText().isEmpty()) {
            deliveryMethodsList = Manager.deliveryMethodServices.findAll();
        }
        updateData(deliveryMethodsList);
        labelCurrentCount.setText(deliveryMethodsList.size() + " записей из " + currentCount);
    }

    @FXML
    void TFSearchAction(ActionEvent event) {
        deliveryMethodsList = deliveryMethodsList.stream().filter(deliveryMethod -> deliveryMethod.getTitle().toLowerCase().contains(TFSearch.getText().toLowerCase())).toList();
        initData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        DMIdTC.setCellValueFactory(cellData -> {
            Object id = cellData.getValue().getDeliveryMethodId();
            return new SimpleStringProperty(id != null ? id.toString() : "");
        });
        TitleTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        DescriptionTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        CostTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCost().toString() + " руб."));
    }
}

