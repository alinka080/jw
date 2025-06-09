package org.example.jewelryshop.controllers.product;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.models.gemstones;
import org.example.jewelryshop.models.products;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsWindowControllerWorker implements Initializable {

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
        Manager.ShowWindow("orders-view.fxml","Заказы");
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
       TFSearch.setOnKeyReleased(e -> TFSearchAction(null));
       LabelUser.setText("Вы вошли как " + Manager.currentWorker.getSecondName() + " " + Manager.currentWorker.getFirstName());
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        products productSelect = ListViewProducts.getSelectionModel().getSelectedItem();
        if(productSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы удалить продукт, выделите его!", Alert.AlertType.INFORMATION);
        else{
            products.WarningMessageDelete(productSelect);
            initData();
        }
    }

    @FXML
    void ShowEditProductWindow() {
        products productSelect = ListViewProducts.getSelectionModel().getSelectedItem();
        if(productSelect == null)
            Manager.MessageBox("Удаление","Внимание!","Для того, чтобы редактировать продукт, выделите его!", Alert.AlertType.INFORMATION);
        else {
            Stage newWindow = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("product-edit-view.fxml"));

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
        products productSelect = ListViewProducts.getSelectionModel().getSelectedItem();
        Manager.currentProduct = productSelect;
        ShowEditProductWindow();
    }

    @FXML
    void MenuItemGuideGemstones(ActionEvent event) throws IOException {
        Manager.ShowWindow("gemstone-view.fxml","Справочник камней");
    }

    @FXML
    void MenuItemGuideMetals(ActionEvent event) throws IOException {
        Manager.ShowWindow("metal-view.fxml","Справочник металла");
    }

    @FXML
    void MenuItemGuideDeliveryMethods(ActionEvent event) throws IOException {
        Manager.ShowWindow("delivery-methods-view.fxml","Справочник способов доставки");
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

    @FXML
    public void MenuItemCreateProductAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowEditWindow("create-product-view.fxml","Добавить");
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
