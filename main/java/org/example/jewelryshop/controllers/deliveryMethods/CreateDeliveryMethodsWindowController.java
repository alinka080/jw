package org.example.jewelryshop.controllers.deliveryMethods;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.jewelryshop.util.Manager;


public class CreateDeliveryMethodsWindowController implements Initializable {

    @FXML
    private Button BtnOk;

    @FXML
    private TextField TextFieldCost;

    @FXML
    private TextField TextFieldDescription;

    @FXML
    private TextField TextFieldTitle;

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    void BtnOkAction(ActionEvent event) throws IOException {
        String errors = checkInputData().toString();
        if(!errors.isEmpty()) {
            Manager.MessageBox("Ошибка!", "Ошибка ввода данных!", errors, Alert.AlertType.ERROR);
            return;
        }
        Manager.currentDeliveryMethods.setTitle(TextFieldTitle.getText());
        Manager.currentDeliveryMethods.setDescription(TextFieldDescription.getText());
        Manager.currentDeliveryMethods.setCost(Integer.parseInt(TextFieldCost.getText()));
        Manager.MessageBox("Способ доставки", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
        Manager.deliveryMethodServices.save(Manager.currentDeliveryMethods);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldTitle.getText().isEmpty())
            TextFieldTitle.setText("Название не указано");
        if(TextFieldDescription.getText().isEmpty())
            TextFieldDescription.setText("Описание не указано");
        if(TextFieldCost.getText().isEmpty())
            TextFieldCost.setText("Цена не указана");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}

