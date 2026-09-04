package ni.edu.uam.registro_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ni.edu.uam.registro_app.RegistroApp;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void ingresarOnClick() {

        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();

        if (usuario.equals("admin")
                && contrasena.equals("admin")) {

            abrirRegistro();

        } else {

            lblMensaje.setText(
                    "Usuario o contraseña incorrectos"
            );
        }
    }

    private void abrirRegistro() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    RegistroApp.class.getResource(
                            "Registro-view.fxml"
                    )
            );

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) txtUsuario
                    .getScene()
                    .getWindow();

            stage.setScene(scene);
            stage.setTitle("Registro de Empleados");
            stage.setResizable(true);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}