package org.example.jewelryshop.models;

import jakarta.persistence.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.jewelryshop.util.Manager;

import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "metals",schema = "public")
public class metals {

    @Id
    @Column(name = "metal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metalId;

    @Column(name = "title")
    private String title;

    @Column(name = "sample")
    private String sample;

    public metals(){}

    public metals(String title, Long metalId ){
        this.metalId = metalId;
        this.title = title;
    }

    public Long getMetalId() {
        return metalId;
    }

    public String getTitle() {
        return title;
    }

    public String getSample() {
        return sample;
    }

    public void setMetalId(Long metalId) {
        this.metalId = metalId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    @Override
    public String toString() {
        return title + " " + sample;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(title,((metals) obj).title) &&
                Objects.equals(sample,((metals) obj).sample);
    }

    @Override
    public int hashCode() {
        return 17 * title.hashCode() + 31 * sample.hashCode();
    }

    public static void WarningMessageDelete(metals metalSelect) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите это сделать?\n Данная операция необратима!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Manager.metalServices.delete(metalSelect);
        }
    }
}
