package org.example.jewelryshop.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ring_sizes",schema = "public")
public class ringSizes {

    @Id
    @Column(name = "ring_size_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ringSizeId;

    @Column(name = "size")
    private String size;

    @Override
    public String toString() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        return Objects.equals(size,((ringSizes) obj).size);
    }

    public Long getRingSizeId() {
        return ringSizeId;
    }

    public String getSize() {
        return size;
    }

    public void setRingSizeId(Long ringSizeId) {
        this.ringSizeId = ringSizeId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        return 17 * ringSizeId.hashCode() + 31 * size.hashCode();
    }
}
