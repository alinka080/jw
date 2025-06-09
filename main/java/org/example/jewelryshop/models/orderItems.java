package org.example.jewelryshop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items", schema = "public")
public class orderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private orrders order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private products product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ring_size_id")
    private ringSizes ringSize;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public orrders getOrder() {
        return order;
    }

    public products getProduct() {
        return product;
    }

    public ringSizes getRingSize() {
        return ringSize;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrder(orrders order) {
        this.order = order;
    }

    public void setProduct(products product) {
        this.product = product;
    }

    public void setRingSize(ringSizes ringSize) {
        this.ringSize = ringSize;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSubtotal() {
        return product.getCost() * quantity;
    }

    @Override
    public String toString() {
        return product.getFullName() + " x" + quantity + " (" + product.getCost() + "₽)";
    }
}
