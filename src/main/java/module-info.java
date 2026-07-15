module com.example.finalproject_varquez {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalproject_varquez to javafx.fxml;
    exports com.example.finalproject_varquez;
}