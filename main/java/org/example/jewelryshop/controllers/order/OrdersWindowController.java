package org.example.jewelryshop.controllers.order;

import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.controllers.*;
import org.example.jewelryshop.models.*;
import org.example.jewelryshop.util.Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.example.jewelryshop.util.Manager.MessageBox;
import static org.example.jewelryshop.util.Manager.PrintOrdersToPDF;

public class OrdersWindowController implements Initializable {

    @FXML
    public TableColumn<orders, String> CustomerTC;
    @FXML
    public TableColumn<orders, ImageView> photoTC;
    @FXML
    public TableColumn<orders, String> ProductTC;
    @FXML
    public TableColumn<orders, String> RingSizeTC;
    @FXML
    public TableColumn<orders, String> QuantityTC;
    @FXML
    public TableColumn<orders, String> DataTC;
    @FXML
    public TableColumn<orders, String> CostTC;
    @FXML
    public TableColumn<orders, String> FIOTC;
    @FXML
    public TableColumn<orders, String> DeliveryMethodTC;
    @FXML
    public TableColumn<orders, String> AddressTC;
    @FXML
    public TableColumn<orders, String> StatusTC;
    @FXML
    private TableView<orders> TWOrders;

    public Label labelCurrentCount;
    public TextField TFSearch;

    private List<orders> ordersList = Manager.orderServices.findAll();
    final Integer currentCount = ordersList.size();
    private List<String> sort = new ArrayList<>();

    @FXML
    private ComboBox<String> CBSort;

    @FXML
    void MenuItemPrintToPDFAllAction(ActionEvent event) throws DocumentException, FileNotFoundException {
        List<orders> allOrders = TWOrders.getItems();

        if (!allOrders.isEmpty()) {
            Manager.PrintOrdersToPDFAll(allOrders);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        } else {
            MessageBox("Ошибка", "", "Нет доступных заказов для печати", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void MenuItemPrintToPDFAction(ActionEvent event) throws DocumentException, FileNotFoundException {
        orders order = TWOrders.getSelectionModel().getSelectedItem();
        if(order == null)
            MessageBox("Удаление","Внимание!","Для того, чтобы удалить заказ, выделите его!", Alert.AlertType.INFORMATION);
        else {
            if (order != null) {
                Manager.PrintOrdersToPDF(order);
                MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
                return;
            }
        }
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        orders orderSelect = TWOrders.getSelectionModel().getSelectedItem();
        if(orderSelect == null)
            MessageBox("Удаление","Внимание!","Для того, чтобы удалить заказ, выделите его!", Alert.AlertType.INFORMATION);
        else{
            orders.WarningMessageDelete(orderSelect);
            initData();
        }
    }

    @FXML
    void MenuItemUpdateAction(ActionEvent event) {
        orders orderSelect = TWOrders.getSelectionModel().getSelectedItem();
        Manager.currentOrder = orderSelect;
        ShowEditOrderWindow();
    }

    @FXML
    void ShowEditOrderWindow() {
        orders orderSelect = TWOrders.getSelectionModel().getSelectedItem();
        if(orderSelect == null)
            MessageBox("Редактирование","Внимание!","Для того, чтобы редактировать продукт, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("edit-order-view.fxml"));

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

    void updateData(List<orders> ordersList){
        TWOrders.getItems().clear();
        ObservableList<orders> trips = FXCollections.observableArrayList(ordersList);
        TWOrders.setItems(trips);
        labelCurrentCount.setText(ordersList.size() + " записей из " + currentCount);
    }

    private void initData(){
        if(TFSearch.getText().isEmpty()) {
            ordersList = Manager.orderServices.findAll();
        }
        updateData(ordersList);
        labelCurrentCount.setText(ordersList.size() + " записей из " + currentCount);
    }

    @FXML
    void TFSearchAction(ActionEvent event) {
        ordersList = ordersList.stream().filter(order -> order.getProduct().getFullName().toLowerCase().contains(TFSearch.getText().toLowerCase())).toList();
        initData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        initData();
        photoTC.setCellValueFactory(cellData -> {
            try {
                return new SimpleObjectProperty<>(cellData.getValue().getProduct().getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        DataTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreateDate().format(formatter)));
        ProductTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getFullName()));
        QuantityTC.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject().asString());
        RingSizeTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRingSize().getSize()));
        FIOTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFullName()));
        DeliveryMethodTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryMethod().getTitle()));
        AddressTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryAddress()));
        StatusTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getTitle()));
        CostTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullCost() + " руб."));
        sort.add("По возрастанию цены");
        sort.add("По убыванию цены");
        sort.add("По возрастанию даты");
        sort.add("По убыванию даты");
        CBSort.setItems(FXCollections.observableArrayList(sort));
    }

    public void CBSortAction(ActionEvent actionEvent) {
        if(CBSort.getValue().equals("По возрастанию цены")){
            ordersList = ordersList.stream().sorted(Comparator.comparing(order -> order.getProduct().getCost())).collect(Collectors.toList());
            updateData(ordersList);
        }
        if(CBSort.getValue().equals("По убыванию цены")){
            ordersList = ordersList.stream().sorted(Comparator.comparing(order -> order.getProduct().getCost())).collect(Collectors.toList()).reversed();
            updateData(ordersList);
        }
        if (CBSort.getValue().equals("По возрастанию даты")) {
            ordersList = ordersList.stream().sorted(Comparator.comparing(orders::getCreateDate)).toList();
            updateData(ordersList);
        }
        if (CBSort.getValue().equals("По убыванию даты")) {
            ordersList = ordersList.stream().sorted(Comparator.comparing(orders::getCreateDate)).collect(Collectors.toList()).reversed();
            updateData(ordersList);
        }
    }

    public void MenuItemBackAction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) TFSearch.getScene().getWindow();
        currentStage.close();
        Manager.ShowWindow("products-view.fxml","Изделия");
    }
}
