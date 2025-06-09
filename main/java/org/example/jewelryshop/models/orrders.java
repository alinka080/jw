package org.example.jewelryshop.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "public")
public class orrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private customers customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_method_id", nullable = false)
    private deliveryMethods deliveryMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private statuses status;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<orderItems> items;

    public Long getOrderId() {
        return orderId;
    }

    public customers getCustomer() {
        return customer;
    }

    public deliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public statuses getStatus() {
        return status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<orderItems> getItems() {
        return items;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(customers customer) {
        this.customer = customer;
    }

    public void setDeliveryMethod(deliveryMethods deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setStatus(statuses status) {
        this.status = status;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setItems(List<orderItems> items) {
        this.items = items;
    }

    public Integer getFullCost() {
        return items.stream()
                .mapToInt(item -> item.getProduct().getCost() * item.getQuantity())
                .sum() + deliveryMethod.getCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        orrders orders = (orrders) o;
        return Objects.equals(orderId, orders.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}