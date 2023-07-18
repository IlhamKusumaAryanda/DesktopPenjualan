module com.example.desktoppenjualan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.desktoppenjualan to javafx.fxml;
    exports com.example.desktoppenjualan;
}