package org.example.jewelryshop.controllers.order;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.jewelryshop.models.orders;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrdersWindowControllerCustomer implements Initializable {

    @FXML
    public TableColumn<orders,String> CustomerTC;
    @FXML
    public TableColumn<orders, ImageView> photoTC;
    @FXML
    public TableColumn<orders,String> ProductTC;
    @FXML
    public TableColumn<orders,String> RingSizeTC;
    @FXML
    public TableColumn<orders, String> QuantityTC;
    @FXML
    public TableColumn<orders, String> DataTC;
    @FXML
    public TableColumn<orders, String> CostTC;
    @FXML
    public TableColumn<orders,String> DeliveryMethodTC;
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
    void MenuItemDeleteAction(ActionEvent event) {
        orders orderSelect = TWOrders.getSelectionModel().getSelectedItem();
        if(orderSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы удалить заказ, выделите его!", Alert.AlertType.INFORMATION);
        else{
            orders.WarningMessageDelete(orderSelect);
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
            ordersList = ordersList.stream().filter(order -> order.getCustomer().equals(Manager.currentCustomer)).toList();
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
        RingSizeTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRingSize().getSize()));
        QuantityTC.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject().asString());
        CostTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullCost() + " руб."));
        DeliveryMethodTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryMethod().getTitle()));
        AddressTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryAddress()));
        StatusTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getTitle()));
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
        Manager.ShowWindow("products-view-client.fxml","Изделия");
    }
}
