package ni.edu.uam.registro_app.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import ni.edu.uam.registro_app.dao.RegistroDao;

import javax.swing.*;

public class RegistroController {

    private final RegistroDao listado = new RegistroDao();

    private int inidiceSeleccionado = -1;

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private ComboBox<String> cmbCargo;
    @FXML
    private ListView

}