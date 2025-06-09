package org.example.jewelryshop.controllers.order;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.jewelryshop.models.*;
import javafx.stage.Stage;

import org.example.jewelryshop.services.CustomerServices;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditOrderWindowController implements Initializable {

    @FXML
    private Button BtnCancel;
    public ComboBox<ringSizes> ComboBoxRingSize;
    public TextField TextFieldQuantity;
    public ComboBox<customers> ComboBoxCustomer;
    public ComboBox<products> ComboBoxProduct;
    public ComboBox<deliveryMethods> ComboBoxDeliveryMethod;
    public TextField TextFieldDeliveryAddress;
    public ComboBox<statuses> ComboBoxStatus;
    public TextField TextFieldCreateData;

    private List<ringSizes> ringSizesList = Manager.ringSizeServices.findAll();
    private List<deliveryMethods> deliveryMethodsList = Manager.deliveryMethodServices.findAll();
    private List<statuses> statusList = Manager.statusServices.findAll();

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void BtnOkAction(ActionEvent event) throws IOException {
        String errors = checkInputData().toString();
        if(!errors.isEmpty()) {
            Manager.MessageBox("Ошибка!", "Ошибка ввода данных!", errors, Alert.AlertType.ERROR);
            return;
        }
        Manager.currentOrder.setCustomer(ComboBoxCustomer.getValue());
        Manager.currentOrder.setProduct(ComboBoxProduct.getValue());
        Manager.currentOrder.setRingSize(ComboBoxRingSize.getValue());
        Manager.currentOrder.setQuantity(Integer.parseInt(TextFieldQuantity.getText()));
        Manager.currentOrder.setDeliveryMethod(ComboBoxDeliveryMethod.getValue());
        Manager.currentOrder.setDeliveryAddress(TextFieldDeliveryAddress.getText());
        Manager.currentOrder.setStatus(ComboBoxStatus.getValue());
//        Manager.currentOrder.setCreateDate(LocalDate.now());
        Manager.MessageBox("Заказ", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
        Manager.orderServices.update(Manager.currentOrder);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldQuantity.getText().isEmpty())
            TextFieldQuantity.setText("1");
        if(ComboBoxDeliveryMethod.getValue() == null)
            errors.append("Способ доставки не указан\n");
//        if(ComboBoxStatus.getValue() == null)
//            ComboBoxStatus.setValue(statusList.get(0));
//        if(TextFieldCreateData.getText().isEmpty())
//            TextFieldCreateData.setText("1");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFieldQuantity.setText(Manager.currentOrder.getQuantity().toString());
        TextFieldDeliveryAddress.setText(Manager.currentOrder.getDeliveryAddress());
        ComboBoxCustomer.setValue(Manager.currentOrder.getCustomer());
        ComboBoxProduct.setValue(Manager.currentOrder.getProduct());
        ComboBoxRingSize.setItems(FXCollections.observableArrayList(ringSizesList));
        ComboBoxRingSize.setValue(Manager.currentOrder.getRingSize());
        ComboBoxStatus.setItems(FXCollections.observableArrayList(statusList));
        ComboBoxStatus.setValue(Manager.currentOrder.getStatus());
        ComboBoxDeliveryMethod.setItems(FXCollections.observableArrayList(deliveryMethodsList));
        ComboBoxDeliveryMethod.setValue(Manager.currentOrder.getDeliveryMethod());
        ComboBoxStatus.setItems(FXCollections.observableArrayList(statusList));
        TextFieldCreateData.setText(Manager.currentOrder.getCreateDate().toString());
        Manager.currentOrder.setCustomer(Manager.currentCustomer);
        Manager.currentOrder.setProduct(Manager.currentProduct);
    }
}
