package org.example.jewelryshop.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "statuses",schema = "public")
public class statuses {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(name = "title")
    private String title;

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
        return Objects.equals(title,((statuses) obj).title);
    }

    public Long getStatusId() { return statusId; }

    public String getTitle() {
        return title;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return 17 * statusId.hashCode() + 31 * title.hashCode();
    }
}
