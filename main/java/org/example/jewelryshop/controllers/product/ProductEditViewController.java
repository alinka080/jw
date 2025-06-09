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
import org.example.jewelryshop.services.GemstoneServices;
import org.example.jewelryshop.services.MetalServices;
import org.example.jewelryshop.services.WorkerServices;
import org.example.jewelryshop.util.Manager;


public class ProductEditViewController implements Initializable  {

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

    private MetalServices metalServices = new MetalServices();
    private WorkerServices workerServices = new WorkerServices();
    private GemstoneServices gemstoneServices = new GemstoneServices();

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
        Manager.MessageBox("Товар", "Удачно!", "Данные успешно обновлены!", Alert.AlertType.INFORMATION);
        Manager.productServices.update(Manager.currentProduct);
    }

    StringBuilder checkInputData(){
        StringBuilder errors = new StringBuilder();
        if(TextFieldTitle.getText().isEmpty())
            TextFieldTitle.setText("Название товара не указано");
        if(TextFieldTypeProduct.getText().isEmpty())
            TextFieldTypeProduct.setText("Тип товара не указан");
        if(TextFieldDescription.getText().isEmpty())
            TextFieldDescription.setText("Описание не указано");
        if(ComboBoxWorker.getValue() == null)
            errors.append("Мастер не указан\n");
        if(ComboBoxMetal.getValue() == null)
            errors.append("Металл не указан\n");
        if(ComboBoxGemstone.getValue() == null)
            errors.append("Камень не указан\n");
        if(TextFieldQuantity.getText().isEmpty())
            TextFieldQuantity.setText("1");
        if(TextFieldCost.getText().isEmpty())
            TextFieldCost.setText("Цена товара не указана");
        return errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFieldTitle.setText(Manager.currentProduct.getTitle());
        TextFieldTypeProduct.setText(Manager.currentProduct.getTypeProduct());
        TextFieldDescription.setText(Manager.currentProduct.getDescription());
        ComboBoxMetal.setItems(FXCollections.observableArrayList(metalServices.findAll()));
        ComboBoxMetal.setValue(Manager.currentProduct.getMetal());
        ComboBoxWorker.setItems(FXCollections.observableArrayList(workerServices.findAll()));
        ComboBoxWorker.setValue(Manager.currentProduct.getWorker());
        ComboBoxGemstone.setItems(FXCollections.observableArrayList(gemstoneServices.findAll()));
        ComboBoxGemstone.setValue(Manager.currentProduct.getGemstone());
        try {
            ImageViewProduct.setImage(Manager.currentProduct.getPhoto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextFieldQuantity.setText(Manager.currentProduct.getQuantityOfProducts().toString());
        TextFieldCost.setText(Manager.currentProduct.getCost().toString());
    }
}