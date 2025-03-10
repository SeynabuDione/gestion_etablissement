module com.groupeisi.g_etablissement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;



    opens com.groupeisi.g_etablissement to javafx.fxml;
    exports com.groupeisi.g_etablissement;

    opens com.groupeisi.g_etablissement.dao to javafx.fxml;
    exports com.groupeisi.g_etablissement.dao;

    opens com.groupeisi.g_etablissement.entities to javafx.fxml;
    exports com.groupeisi.g_etablissement.entities;

    opens com.groupeisi.g_etablissement.controller to javafx.fxml;
    exports com.groupeisi.g_etablissement.controller;
}
