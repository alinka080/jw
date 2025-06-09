package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TypeOfUserReg implements Initializable {

    public void BtnClientAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("create-client.fxml","Регистрация как Клиент");
    }

    public void BtnWorkerAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("create-worker.fxml","Регистрация как Работник");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
