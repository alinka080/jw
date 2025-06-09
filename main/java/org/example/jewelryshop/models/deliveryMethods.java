package org.example.jewelryshop.models;


import jakarta.persistence.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.jewelryshop.util.Manager;

import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "delivery_methods",schema = "public")
public class deliveryMethods {

    @Id
    @Column(name = "delivery_method_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryMethodId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Integer cost;

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(title,((deliveryMethods) obj).title) &&
                Objects.equals(cost,((deliveryMethods) obj).cost) &&
                Objects.equals(description,((deliveryMethods) obj).description);
    }

    @Override
    public int hashCode() {
        return 17 * title.hashCode() + 31 * cost.hashCode() + 37 * description.hashCode();
    }

    public static void WarningMessageDelete(deliveryMethods deliveryMethodsSelect) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите это сделать?\n Данная операция необратима!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Manager.deliveryMethodServices.delete(deliveryMethodsSelect);
        }
    }
}
