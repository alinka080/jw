package org.example.jewelryshop.controllers.metal;

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

public class CreateMetalWindowController implements Initializable {

    @FXML
    private Button BtnOk;

    @FXML
    private TextField TextFieldOfSample;

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
        Manager.currentMetal.setTitle(TextFieldTitle.getText());
        Manager.currentMetal.setSample(TextFieldOfSample.getText());
        try {
            Manager.metalServices.save(Manager.currentMetal);
            Manager.MessageBox("Металл", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) BtnOk.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Manager.MessageBox("Ошибка!", "Ошибка сохранения данных!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldTitle.getText().isEmpty())
            TextFieldTitle.setText("Название не указано");
        if(TextFieldOfSample.getText().isEmpty())
            TextFieldOfSample.setText("Проба металла не указана");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}