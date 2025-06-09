package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreateWorkerWindowController implements Initializable {

    @FXML
    private Button BtnOk;

    @FXML
    private TextField TextFieldPosition;

    @FXML
    private TextField TextFieldMiddlename;

    @FXML
    private TextField TextFieldSecondName;

    @FXML
    private TextField TextFieldFirstName;

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
        Manager.currentWorker.setPosition(TextFieldPosition.getText());
        Manager.currentWorker.setFirstName(TextFieldFirstName.getText());
        Manager.currentWorker.setSecondName(TextFieldSecondName.getText());
        Manager.currentWorker.setMiddleName(TextFieldMiddlename.getText());
        Manager.currentWorker.setEmail(TextFieldEmail.getText());
        Manager.currentWorker.setPassword(TextFieldPassword.getText());
        Manager.MessageBox("Работник", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
        Manager.workerServices.save(Manager.currentWorker);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldFirstName.getText().isEmpty())
            TextFieldFirstName.setText("Фамилия не указана");
        if(TextFieldSecondName.getText().isEmpty())
            TextFieldSecondName.setText("Имя не указано");
        if(TextFieldMiddlename.getText().isEmpty())
            TextFieldMiddlename.setText("Отчество не указано");
        if(TextFieldPosition.getText().isEmpty())
            TextFieldPosition.setText("Должность не указана");
        if(TextFieldEmail.getText().isEmpty())
            TextFieldEmail.setText("Адрес электронной почты не указан");
        if(TextFieldPassword.getText().isEmpty())
            TextFieldPassword.setText("Пароль не указан");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}

