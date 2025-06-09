package org.example.jewelryshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.jewelryshop.models.customers;
import org.example.jewelryshop.util.Manager;

import java.io.IOException;
import java.util.List;


public class LoginViewControllerCustomer {

    private List<customers> customersList = Manager.customerServices.findAll();

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
        for(customers customer: customersList)
            if (TFLogin.getText().equals(customer.getEmail()) && TFPassword.getText().equals(customer.getPassword())){
                Manager.currentCustomer = customer;
                Manager.ShowWindow("products-view-client.fxml","Изделия");
                loginSuccessful = true;
                break;
            }
        if (!loginSuccessful) {
                Manager.ErrorosMessageBox("Логин или пароль введён не верно!");
            }
    }
}
