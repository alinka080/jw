package org.example.jewelryshop.models;


import jakarta.persistence.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.jewelryshop.util.Manager;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "orders",schema = "public")
public class orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private customers customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private products product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ring_size_id", nullable = false)
    private ringSizes ringSize;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private statuses status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_method_id", nullable = false)
    private deliveryMethods deliveryMethod;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public Integer getFullCost(){
        return product.getCost() * getQuantity() + deliveryMethod.getCost();
    }

    public Long getOrderId() {
        return orderId;
    }

    public customers getCustomer() {
        return customer;
    }

    public products getProduct() {
        return product;
    }

    public ringSizes getRingSize() {return ringSize;}

    public statuses getStatus() {return status;}

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getCreateDate() { return createDate; }

    public deliveryMethods getDeliveryMethod() {
            return deliveryMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(customers customer) {
        this.customer = customer;
    }

    public void setProduct(products product) {
        this.product = product;
    }

    public void setRingSize(ringSizes ringSize) {
        this.ringSize = ringSize;
    }

    public void setStatus(statuses status) {
        this.status = status;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    public void setDeliveryMethod(deliveryMethods deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

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
        return Objects.equals(deliveryAddress,((orders) obj).deliveryAddress) &&
                Objects.equals(product,((orders) obj).product) &&
                Objects.equals(ringSize,((orders) obj).ringSize) &&
                Objects.equals(status,((orders) obj).status) &&
                Objects.equals(customer,((orders) obj).customer);
    }

    @Override
    public int hashCode() {
        return 17 * deliveryAddress.hashCode() +
                31 * product.hashCode() +
                37 * ringSize.hashCode() +
                41 * status.hashCode() +
                53 * customer.hashCode();
    }

    public static void WarningMessageDelete(orders orderSelect) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите это сделать?\n Данная операция необратима!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Manager.orderServices.delete(orderSelect);
        }
    }
}
