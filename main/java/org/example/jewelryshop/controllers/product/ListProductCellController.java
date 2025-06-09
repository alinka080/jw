package org.example.jewelryshop.controllers.product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.jewelryshop.models.products;

import java.io.IOException;

public class ListProductCellController {
    @FXML
    public ImageView ImageViewProduct;
    @FXML
    public Label LableFullName;
    @FXML
    public Label LableDescription;
    @FXML
    public Label LableWorker;
    @FXML
    public Label LableGemstone;
    @FXML
    public Label LablePrice;
    @FXML
    public Label LableQuantity;


    public void setCell(products product) throws IOException {
        ImageViewProduct.setImage(product.getPhoto());
        LableFullName.setText(product.getFullName());
        LableDescription.setText(product.getDescription());
        LableWorker.setText("Мастер: " + product.getWorker().getInitial());
        if(product.getGemstone() == null) {
            LableGemstone.setText("");
        }
        else
            LableGemstone.setText("Камень: "+ product.getGemstone().getTitle());
        LablePrice.setText("Стоимость: "+ product.getCost().toString() + " руб.");
        if (product.getQuantityOfProducts() == null || product.getQuantityOfProducts() == 0) {
            LableQuantity.setText("Нет в наличии");
        } else if (product.getQuantityOfProducts() < 2) {
            LableQuantity.setText("Скоро закончится: " + product.getQuantityOfProducts().toString() + " шт");
        } else {
            LableQuantity.setText("В наличии: " + product.getQuantityOfProducts().toString() + " шт");
        }
    }
}
