package org.example.jewelryshop.models;

import jakarta.persistence.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.jewelryshop.util.Manager;

import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "gemstones",schema = "public")
public class gemstones {

    @Id
    @Column(name = "gemstone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gemstoneId;

    @Column(name = "title")
    private String title;

    @Column(name = "type_of_stone")
    private String typeOfStone;

    @Column(name = "color")
    private String color;

    @Column(name = "shape_of_the_cut")
    private String shapeOfTheCut;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity_of_gemstones")
    private Integer quantityOfGemstones;

    public gemstones(){}

    public gemstones(String title, Long gemstoneId ){
        this.gemstoneId = gemstoneId;
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public String getShapeOfTheCut() {
        return shapeOfTheCut;
    }

    public String getColor() {
        return color;
    }

    public String getTypeOfStone() {
        return typeOfStone;
    }

    public String getTitle() {
        return title;
    }

    public Long getGemstoneId() {
        return gemstoneId;
    }

    public Integer getQuantityOfGemstones() {
        return quantityOfGemstones;
    }

    @Override
    public String toString() {
        return title;
    }

    public void setGemstoneId(Long gemstoneId) {
        this.gemstoneId = gemstoneId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTypeOfStone(String typeOfStone) {
        this.typeOfStone = typeOfStone;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setShapeOfTheCut(String shapeOfTheCut) {
        this.shapeOfTheCut = shapeOfTheCut;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantityOfGemstones(Integer quantityOfGemstones) {
        this.quantityOfGemstones = quantityOfGemstones;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(title,((gemstones) obj).title) &&
                Objects.equals(typeOfStone,((gemstones) obj).typeOfStone) &&
                Objects.equals(color,((gemstones) obj).color) &&
                Objects.equals(shapeOfTheCut,((gemstones) obj).shapeOfTheCut) &&
                Objects.equals(size,((gemstones) obj).size);
    }

    @Override
    public int hashCode() {
        return 17 * title.hashCode() + 31 * typeOfStone.hashCode() + 37 * color.hashCode() + 41 * shapeOfTheCut.hashCode() + 53 * size.hashCode();
    }

    public static void WarningMessageDelete(gemstones gemstoneSelect) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите это сделать?\n Данная операция необратима!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Manager.gemstoneServices.delete(gemstoneSelect);
        }
    }
}
