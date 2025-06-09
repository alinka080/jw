package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialWindow implements Initializable {

    public void BtnRegAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("type-of-user-reg-view.fxml","Выбор типа пользователя");
    }

    public void BtnEnterAction(ActionEvent actionEvent) throws IOException {
        Manager.ShowWindow1("type-of-user-view.fxml","Выбор типа пользователя");
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
