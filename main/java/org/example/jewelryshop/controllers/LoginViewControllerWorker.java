package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.jewelryshop.models.workers;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.util.List;

public class LoginViewControllerWorker {


    private List<workers> workersList = Manager.workerServices.findAll();

    @FXML
    private TextField TFLogin;

    @FXML
    private TextField TFPassword;

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Manager.currentStage.close();
    }

    @FXML
    void BtnOkAction(ActionEvent event) throws IOException {
        boolean loginSuccessful = false;
        for(workers worker: workersList)
            if (TFLogin.getText().equals(worker.getEmail()) && TFPassword.getText().equals(worker.getPassword())){
                Manager.currentWorker = worker;
                Manager.ShowWindow("products-view.fxml","Изделия");
                loginSuccessful = true;
                break;
            }
        if (!loginSuccessful)
                Manager.ErrorosMessageBox("Логин или пароль введён не верно!");
    }
}



