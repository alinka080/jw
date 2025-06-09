package org.example.jewelryshop.controllers.gemstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.jewelryshop.util.Manager;


public class GemstoneEditViewController implements Initializable {

    @FXML
    private Button BtnOk;

    @FXML
    private TextField TextFieldColor;

    @FXML
    private TextField TextFieldShapeOfTheCut;

    @FXML
    private TextField TextFieldSize;

    @FXML
    private TextField TextFieldTitle;

    @FXML
    private TextField TextFieldTypeOfStone;

    @FXML
    private TextField TextFieldQuantity;

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    void BtnOkAction(ActionEvent event) {
        String errors = checkInputData().toString();
        if(!errors.isEmpty()) {
            Manager.MessageBox("Ошибка!", "Ошибка ввода данных!", errors, Alert.AlertType.ERROR);
            return;
        }
        Manager.currentGemstone.setTitle(TextFieldTitle.getText());
        Manager.currentGemstone.setTypeOfStone(TextFieldTypeOfStone.getText());
        Manager.currentGemstone.setColor(TextFieldColor.getText());
        Manager.currentGemstone.setShapeOfTheCut(TextFieldShapeOfTheCut.getText());
        Manager.currentGemstone.setSize(TextFieldSize.getText());
        Manager.currentGemstone.setQuantityOfGemstones(Integer.parseInt(TextFieldQuantity.getText()));
        Manager.MessageBox("Камень", "Удачно!", "Данные успешно обновлены!", Alert.AlertType.INFORMATION);
        Manager.gemstoneServices.update(Manager.currentGemstone);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldTitle.getText().isEmpty())
            TextFieldTitle.setText("Название не указано");
        if(TextFieldTypeOfStone.getText().isEmpty())
            TextFieldTypeOfStone.setText("Тип не указан");
        if(TextFieldColor.getText().isEmpty())
            TextFieldColor.setText("Цвет не указан");
        if(TextFieldShapeOfTheCut.getText().isEmpty())
            TextFieldShapeOfTheCut.setText("Форма огранки не указана");
        if(TextFieldSize.getText().isEmpty())
            TextFieldSize.setText("Размер не указан");
        if(TextFieldQuantity.getText().isEmpty())
            TextFieldQuantity.setText("Сколько в наличие не указано");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFieldTitle.setText(Manager.currentGemstone.getTitle());
        TextFieldTypeOfStone.setText(Manager.currentGemstone.getTypeOfStone());
        TextFieldColor.setText(Manager.currentGemstone.getColor());
        TextFieldShapeOfTheCut.setText(Manager.currentGemstone.getShapeOfTheCut());
        TextFieldSize.setText(Manager.currentGemstone.getSize());
        TextFieldSize.setText(Manager.currentGemstone.getSize());
        TextFieldQuantity.setText(Manager.currentGemstone.getQuantityOfGemstones().toString());
    }
}

