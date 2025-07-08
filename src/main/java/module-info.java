module com.example.app_javafx_gest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports main;
    exports controllers;
    exports models;
    exports utils;

    opens controllers to javafx.fxml;
    opens models to javafx.base;
    opens utils to javafx.base;
}
