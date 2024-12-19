module com.group4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.group4 to javafx.fxml, com.google.gson;
    exports com.group4;
}
