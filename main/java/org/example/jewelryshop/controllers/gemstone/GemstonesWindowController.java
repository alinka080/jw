package org.example.jewelryshop.controllers.gemstone;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.models.*;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GemstonesWindowController implements Initializable {

    @FXML
    private TableColumn<gemstones,String> ColorTC;

    @FXML
    private TableColumn<gemstones,String> GemstoneIdTC;

    @FXML
    private Menu MenuItemsData;

    @FXML
    private Menu MenuItemsInstruments;

    @FXML
    private TableColumn<gemstones,String> ShapeOfTheCutTC;

    @FXML
    private TableColumn<gemstones,String> SizeTC;

    @FXML
    private TextField TFSearch;

    @FXML
    private TableView<gemstones> TWGemstone;

    @FXML
    private TableColumn<gemstones,String> TitleTC;

    @FXML
    private TableColumn<gemstones,String> TypeOfStoneTC;

    @FXML
    private TableColumn<gemstones,String> QuantityTC;

    @FXML
    private Label labelCurrentCount;

    @FXML
    private List<gemstones> gemstonesList = Manager.gemstoneServices.findAll();

    @FXML
    final Integer currentCount = gemstonesList.size();

    @FXML
    void MenuItemBackAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) TFSearch.getScene().getWindow();
        currentStage.close();
        Manager.ShowWindow("products-view.fxml","Изделия");
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        gemstones gemstoneSelect = TWGemstone.getSelectionModel().getSelectedItem();
        if(gemstoneSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы удалить камень, выделите его!", Alert.AlertType.INFORMATION);
        else{
            gemstones.WarningMessageDelete(gemstoneSelect);
            initData();
        }
    }

    @FXML
    public void MenuItemCreateGemstoneAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowEditWindow("create-gemstone-view.fxml","Добавить");
    }

    @FXML
    void ShowEditGemstoneWindow() {
        gemstones gemstoneSelect = TWGemstone.getSelectionModel().getSelectedItem();
        if(gemstoneSelect == null)
            Manager.MessageBox("Редактирование","Внимание!","Для того, чтобы редактировать камень, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("gemstone-edit-view.fxml"));

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
        gemstones gemstoneSelect = TWGemstone.getSelectionModel().getSelectedItem();
        Manager.currentGemstone = gemstoneSelect;
        ShowEditGemstoneWindow();
    }

    void updateData(List<gemstones> gemstonesList){
        TWGemstone.getItems().clear();
        ObservableList<gemstones> trips = FXCollections.observableArrayList(gemstonesList);
        TWGemstone.setItems(trips);
        labelCurrentCount.setText(gemstonesList.size() + " записей из " + currentCount);
    }

    private void initData(){
        if(TFSearch.getText().isEmpty()) {
            gemstonesList = Manager.gemstoneServices.findAll();
        }
        updateData(gemstonesList);
        labelCurrentCount.setText(gemstonesList.size() + " записей из " + currentCount);
    }

    @FXML
    void TFSearchAction(ActionEvent event) {
        gemstonesList = gemstonesList.stream().filter(gemstone -> gemstone.getTitle().toLowerCase().contains(TFSearch.getText().toLowerCase())).toList();
        initData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        GemstoneIdTC.setCellValueFactory(cellData -> {
            Object id = cellData.getValue().getGemstoneId();
            return new SimpleStringProperty(id != null ? id.toString() : "");
        });
        TitleTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        TypeOfStoneTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeOfStone()));
        ColorTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        ShapeOfTheCutTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShapeOfTheCut()));
        SizeTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));
        QuantityTC.setCellValueFactory(cellData -> {
            Object quantity = cellData.getValue().getQuantityOfGemstones();
            return new SimpleStringProperty(quantity != null ? quantity.toString() : "");
        });
    }
}
