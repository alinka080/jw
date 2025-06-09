package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.jewelryshop.util.Manager;


public class CreateCustomerWindowController implements Initializable {

    @FXML
    private Button BtnOk;

    @FXML
    private TextField TextFieldMiddlename;

    @FXML
    private TextField TextFieldSecondName;

    @FXML
    private TextField TextFieldFirstName;

    @FXML
    private TextField TextFieldPhone;

    @FXML
    private TextField TextFieldCardNumber;

    @FXML
    private TextField TextFieldPassword;

    @FXML
    private TextField TextFieldEmail;

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
        Manager.currentCustomer.setFirstName(TextFieldFirstName.getText());
        Manager.currentCustomer.setSecondName(TextFieldSecondName.getText());
        Manager.currentCustomer.setMiddleName(TextFieldMiddlename.getText());
        Manager.currentCustomer.setPhone(TextFieldPhone.getText());
        Manager.currentCustomer.setCardNumber(TextFieldCardNumber.getText());
        Manager.currentCustomer.setEmail(TextFieldEmail.getText());
        Manager.currentCustomer.setPassword(TextFieldPassword.getText());
        Manager.MessageBox("Клиент", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
        Manager.customerServices.save(Manager.currentCustomer);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldFirstName.getText().isEmpty())
            TextFieldFirstName.setText("Фамилия не указана");
        if(TextFieldSecondName.getText().isEmpty())
            TextFieldSecondName.setText("Имя не указано");
        if(TextFieldMiddlename.getText().isEmpty())
            TextFieldMiddlename.setText("Отчество не указано");
        if(TextFieldPhone.getText().isEmpty())
            TextFieldPhone.setText("Телефон не указан");
        if(TextFieldCardNumber.getText().isEmpty())
            TextFieldCardNumber.setText("Номер карты не указан");
        if(TextFieldEmail.getText().isEmpty())
            TextFieldEmail.setText("Адрес электронной почты не указан");
        if(TextFieldPassword.getText().isEmpty())
            TextFieldPassword.setText("Пароль не указан");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}

