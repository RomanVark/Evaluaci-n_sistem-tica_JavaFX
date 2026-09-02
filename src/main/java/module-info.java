module ni.edu.uam.registro_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens ni.edu.uam.registro_app.controller to javafx.fxml;
    opens ni.edu.uam.registro_app.modelos to javafx.base;

    exports ni.edu.uam.registro_app;
}