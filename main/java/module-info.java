module org.example.jewelryshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires javafx.swing;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires org.hibernate.validator;
    requires org.postgresql.jdbc;
    requires itextpdf;

    opens org.example.jewelryshop to javafx.fxml;
    exports org.example.jewelryshop;
    exports org.example.jewelryshop.controllers;
    opens org.example.jewelryshop.controllers to javafx.fxml;
    exports org.example.jewelryshop.repositories;
    opens org.example.jewelryshop.repositories to javafx.fxml;
    opens org.example.jewelryshop.util to org.hibernate.orm.core;
    opens org.example.jewelryshop.models to org.hibernate.orm.core, javafx.base;
    exports org.example.jewelryshop.controllers.product;
    opens org.example.jewelryshop.controllers.product to javafx.fxml;
    exports org.example.jewelryshop.controllers.order;
    opens org.example.jewelryshop.controllers.order to javafx.fxml;
    exports org.example.jewelryshop.controllers.gemstone;
    opens org.example.jewelryshop.controllers.gemstone to javafx.fxml;
    exports org.example.jewelryshop.controllers.metal;
    opens org.example.jewelryshop.controllers.metal to javafx.fxml;
    exports org.example.jewelryshop.controllers.deliveryMethods;
    opens org.example.jewelryshop.controllers.deliveryMethods to javafx.fxml;
}