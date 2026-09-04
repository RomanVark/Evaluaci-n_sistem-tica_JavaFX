package ni.edu.uam.registro_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistroApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(
                RegistroApp.class.getResource("login-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Inicio de sesión");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}