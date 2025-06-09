package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TypeOfUser implements Initializable {

    public void BtnClientAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("login-view.fxml","Вход как Клиент");
    }

    public void BtnWorkerAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("login-view-worker.fxml","Вход как Работник");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
