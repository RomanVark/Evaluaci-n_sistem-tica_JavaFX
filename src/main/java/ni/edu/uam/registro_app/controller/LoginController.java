package ni.edu.uam.registro_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ni.edu.uam.registro_app.EstudianteApplication;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void ingresarOnClick() {

        String usuario = txtUsuario.getText();
        String password = txtContrasena.getText();

        if (usuario.equals("admin") && password.equals("admin")) {

            abrirRegistro();

        } else {

            lblMensaje.setText("Usuario o contraseña incorrectos");
        }
    }

    private void abrirRegistro() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    EstudianteApplication.class.getResource(
                            "estudiante-view.fxml"
                    )
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) txtUsuario
                    .getScene()
                    .getWindow();

            stage.setScene(scene);
            stage.setTitle("Registro App");
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}