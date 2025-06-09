package org.example.jewelryshop.models;


import jakarta.persistence.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.util.Manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "products",schema = "public")
public class products {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "type_product")
    private String typeProduct;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gemstone_id", nullable = false)
    private gemstones gemstone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id", nullable = false)
    private workers worker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metal_id", nullable = false)
    private metals metal;

    @Column(name = "quantity_of_products")
    private Integer quantityOfProducts;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "photo")
    private  byte[] photo;

    public String getFullName(){
        return typeProduct + ": " + title;
    }

    public Long getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public String getDescription() {
        return description;
    }

    public gemstones getGemstone() {
        return gemstone;
    }

    public workers getWorker() {
        return worker;
    }

    public metals getMetal() { return metal;}

    public Integer getQuantityOfProducts() {
        return quantityOfProducts;
    }

    public Integer getCost() {
        return cost;
    }

    public boolean isHasPhoto() {
        return photo != null;
    }

    public Image getPhoto() throws IOException {
        if (photo == null)
            return new Image(JewelryShop.class.getResourceAsStream("picture.png"));
        BufferedImage capture = ImageIO.read(new ByteArrayInputStream(photo));
        return SwingFXUtils.toFXImage(capture, null);
    }

    public ImageView getImage() throws IOException {
        ImageView image = new ImageView();
        image.setImage(getPhoto());
        image.setFitHeight(60);
        image.setPreserveRatio(true);
        return image;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGemstone(gemstones gemstone) {
        this.gemstone = gemstone;
    }

    public void setWorker(workers worker) {
        this.worker = worker;
    }

    public void setMetal(metals metal) { this.metal = metal; }

    public void setQuantityOfProducts(Integer quantityOfProducts) {
        this.quantityOfProducts = quantityOfProducts;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setPhoto(Image img) throws IOException {
        BufferedImage buf = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buf, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        this.photo = bytes;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(title,((products) obj).title) &&
                Objects.equals(typeProduct,((products) obj).typeProduct) &&
                Objects.equals(metal,((products) obj).metal) &&
                Objects.equals(gemstone,((products) obj).gemstone) &&
                Objects.equals(cost,((products) obj).cost) ;
    }

    @Override
    public int hashCode() {
        return 17 * title.hashCode() + 31 * typeProduct.hashCode() + 37 * metal.hashCode() +  41 * gemstone.hashCode() + 53 * cost.hashCode();
    }

    public static void WarningMessageDelete(products productSelect) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите это сделать?\n Данная операция необратима!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Manager.productServices.delete(productSelect);
        }
    }
}
