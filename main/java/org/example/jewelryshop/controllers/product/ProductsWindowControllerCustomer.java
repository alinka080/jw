package org.example.jewelryshop.controllers.product;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.jewelryshop.models.gemstones;
import org.example.jewelryshop.models.products;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsWindowControllerCustomer implements Initializable {

    @FXML
    public ComboBox<String> CBFilter;
    @FXML
    public TextField TFSearch;
    @FXML
    public ComboBox<String> CBSort;
    @FXML
    public Label labelCurrentCount;
    @FXML
    private ListView<products> ListViewProducts;
    @FXML
    private Label LabelUser;

    private List<products> allProductsList;
    private List<products> productsList = Manager.productServices.findAll();
    private final Integer currentCount = productsList.size();
    private List<String> productTypes = List.of("Все", "кольцо", "подвеска", "моно серьга","средство для чистки изделий");
    private List<products> FilterList;
    private List<String> sort = new ArrayList<>();

    @FXML
    private void MenuItemExit() {
        Platform.exit();
    }

    @FXML
    void MenuItemOrders(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) TFSearch.getScene().getWindow();
        currentStage.close();
        Manager.ShowWindow("orders-view-customer.fxml","Заказы");
    }

    @FXML
    void TFSearchAction(ActionEvent event) {
        String searchText = TFSearch.getText().toLowerCase().trim();
        String selectedType = CBFilter.getValue();

        productsList = new ArrayList<>(allProductsList);

        if (selectedType != null && !selectedType.equals("Все")) {
            productsList = productsList.stream()
                    .filter(product -> product.getTypeProduct() != null &&
                            product.getTypeProduct().equalsIgnoreCase(selectedType))
                    .toList();
        }

        if (!searchText.isEmpty()) {
            productsList = productsList.stream()
                    .filter(product -> product.getFullName() != null &&
                            product.getFullName().toLowerCase().contains(searchText))
                    .toList();
        }

        updateData(productsList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        CBFilter.setItems(FXCollections.observableArrayList(productTypes));
        CBFilter.getSelectionModel().selectFirst();
        sort.add("По возрастанию");
        sort.add("По убыванию");
        sort.add("По возрастанию цены");
        sort.add("По убыванию цены");
        sort.add("По наличию на складе");
        CBSort.setItems(FXCollections.observableArrayList(sort));
        LabelUser.setText("Вы вошли как " + Manager.currentCustomer.getSecondName() + " " + Manager.currentCustomer.getFirstName());
    }

    void updateData(List<products> productList){
        ListViewProducts.getItems().clear();
        for(products product: productList)
            ListViewProducts.getItems().add(product);
        ListViewProducts.setCellFactory(cell -> {
            try {
                return new ProductCell();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        labelCurrentCount.setText(productsList.size() + " записей из " + currentCount);
    }

    private void initData(){
        productsList = Manager.productServices.findAll();
        allProductsList = Manager.productServices.findAll(); // сохраняем оригинальный список
        productsList = new ArrayList<>(allProductsList);
        updateData(productsList);
        labelCurrentCount.setText(productsList.size() + " записей из " + currentCount);
    }

    @FXML
    public void CBSortAction(ActionEvent actionEvent) {
        String sortValue = CBSort.getValue();
        if (sortValue == null) return;

        switch (sortValue) {
            case "По возрастанию" -> {
                productsList = productsList.stream()
                        .sorted(Comparator.comparing(products::getFullName))
                        .toList();
            }
            case "По убыванию" -> {
                productsList = productsList.stream()
                        .sorted(Comparator.comparing(products::getFullName).reversed())
                        .toList();
            }
            case "По возрастанию цены" -> {
                productsList = productsList.stream()
                        .sorted(Comparator.comparing(products::getCost))
                        .toList();
            }
            case "По убыванию цены" -> {
                productsList = productsList.stream()
                        .sorted(Comparator.comparing(products::getCost).reversed())
                        .toList();
            }
            case "По наличию на складе" -> {
                productsList = productsList.stream()
                        .sorted(Comparator.comparing(products::getQuantityOfProducts).reversed())
                        .toList();
            }
            default -> { }
        }
        updateData(productsList);
    }

    @FXML
    public void CBFilterAction(ActionEvent actionEvent) {
        String selectedType = CBFilter.getValue();
        productsList = new ArrayList<>(allProductsList);

        if (selectedType != null && !selectedType.equals("Все")) {
            productsList = productsList.stream()
                    .filter(product -> product.getTypeProduct() != null &&
                            product.getTypeProduct().equalsIgnoreCase(selectedType))
                    .toList();
        }

        updateData(productsList);
    }

    public void BtnCreateOrderAction(ActionEvent actionEvent) throws IOException {
        if(ListViewProducts.getSelectionModel().getSelectedItem() == null)
            Manager.MessageBox("Заказ","Внимание!","Для того, чтобы заказать продукт, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Manager.currentProduct = ListViewProducts.getSelectionModel().getSelectedItem();
            Manager.ShowEditWindow("create-order-view.fxml","Заказать");
        }
    }
}
