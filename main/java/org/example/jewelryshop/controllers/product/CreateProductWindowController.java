package org.example.jewelryshop.controllers.product;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import org.example.jewelryshop.models.gemstones;
import org.example.jewelryshop.models.metals;
import org.example.jewelryshop.models.workers;
import org.example.jewelryshop.util.Manager;
import java.util.List;


public class CreateProductWindowController implements Initializable  {

    boolean imageLoaded = false;

    @FXML
    private Button BtnOk;

    @FXML
    public TextField TextFieldTitle;

    @FXML
    public TextField TextFieldTypeProduct;

    @FXML
    public TextField TextFieldDescription;

    @FXML
    public ComboBox<metals> ComboBoxMetal;

    @FXML
    public ComboBox<workers> ComboBoxWorker;

    @FXML
    public ComboBox<gemstones> ComboBoxGemstone;

    @FXML
    public TextField TextFieldQuantity;

    @FXML
    public TextField TextFieldCost;

    @FXML
    private ImageView ImageViewProduct;

    @FXML
    private Button BtnLoadImage;

    private List<metals> metalsList = Manager.metalServices.findAll();
    private List<workers> workersList = Manager.workerServices.findAll();
    private List<gemstones> gemstonesList = Manager.gemstoneServices.findAll();

    @FXML
    void BtnLoadImageAction(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        Stage stage = (Stage) BtnLoadImage.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String imageUrl = file.toURI().toURL().toExternalForm();
            ImageViewProduct.setImage(new Image(imageUrl));
            imageLoaded = true;
        }
    }

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
        Manager.currentProduct.setTitle(TextFieldTitle.getText());
        Manager.currentProduct.setTypeProduct(TextFieldTypeProduct.getText());
        Manager.currentProduct.setDescription(TextFieldDescription.getText());
        Manager.currentProduct.setMetal(ComboBoxMetal.getValue());
        Manager.currentProduct.setWorker(ComboBoxWorker.getValue());
        Manager.currentProduct.setGemstone(ComboBoxGemstone.getValue());
        if (imageLoaded) {
            Manager.currentProduct.setPhoto(ImageViewProduct.getImage());
        }
        Manager.currentProduct.setQuantityOfProducts(Integer.parseInt(TextFieldQuantity.getText()));
        Manager.currentProduct.setCost(Integer.parseInt(TextFieldCost.getText()));
        Manager.MessageBox("Товар", "Удачно!", "Данные успешно сохранены!", Alert.AlertType.INFORMATION);
        Manager.productServices.save(Manager.currentProduct);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldTitle.getText().isEmpty())
            TextFieldTitle.setText("Название товара не указано");
        if(TextFieldTypeProduct.getText().isEmpty())
            TextFieldTypeProduct.setText("Тип товара не указан");
        if(TextFieldDescription.getText().isEmpty())
            TextFieldDescription.setText("Описание не указано");
        if(TextFieldQuantity.getText().isEmpty())
            TextFieldQuantity.setText("1");
        if(ComboBoxMetal.getValue() == null)
            errors.append("Металл не указан\n");
        if(ComboBoxGemstone.getValue() == null)
            errors.append("Камень не указан\n");
        if(ComboBoxWorker.getValue() == null)
            errors.append("Мастер не указан\n");
        if(TextFieldQuantity.getText().isEmpty())
            TextFieldQuantity.setText("Количество товара не указано");
        if(TextFieldCost.getText().isEmpty())
            TextFieldCost.setText("Цена товара не указана");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBoxMetal.setItems(FXCollections.observableArrayList(metalsList));
        ComboBoxWorker.setItems(FXCollections.observableArrayList(workersList));
        ComboBoxGemstone.setItems(FXCollections.observableArrayList(gemstonesList));
        try {
            ImageViewProduct.setImage(Manager.currentProduct.getPhoto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}