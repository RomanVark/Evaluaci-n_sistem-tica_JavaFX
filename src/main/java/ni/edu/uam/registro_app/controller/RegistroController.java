package ni.edu.uam.registro_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ni.edu.uam.registro_app.dao.RegistroDao;
import ni.edu.uam.registro_app.modelos.Colaborador;

import java.time.LocalDate;

public class RegistroController {

    private final RegistroDao listado = new RegistroDao();

    private int indiceSeleccionado = -1;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ComboBox<String> combCargo;

    @FXML
    private ListView<String> lvAreaTrabajo;

    @FXML
    private DatePicker dpfechaCont;

    @FXML
    private RadioButton rbDeterminado;

    @FXML
    private RadioButton rbIndeterminado;

    @FXML
    private CheckBox chkSeguro;

    @FXML
    private CheckBox chkAlimentacion;

    @FXML
    private CheckBox chkTransporte;

    @FXML
    private ToggleGroup grupoContrato;

    @FXML
    private ImageView imgLogoUam;

    @FXML
    private TableView<Colaborador> tbtablaRegistros;

    @FXML
    private TableColumn<Colaborador, String> colNombreCompleto;

    @FXML
    private TableColumn<Colaborador, String> colCargo;

    @FXML
    private TableColumn<Colaborador, String> colArea;

    @FXML
    private TableColumn<Colaborador, LocalDate> colFecha;

    @FXML
    private TableColumn<Colaborador, String> colContrato;

    @FXML
    private TableColumn<Colaborador, String> colBeneficios;

    @FXML
    private Label lblRegistro;

    public void initialize() {

        combCargo.getItems().addAll(
                "Administrador",
                "Contador",
                "Vendedor",
                "Bodeguero",
                "Cajero",
                "Supervisor",
                "Jefe"
        );

        lvAreaTrabajo.getItems().addAll(
                "Recursos Humanos",
                "Administracion",
                "Ventas",
                "Bodega",
                "Caja",
                "Supervisión",
                "Gerencia"
        );

        colNombreCompleto.setCellValueFactory(
                new PropertyValueFactory<>("nombreCompleto")
        );

        colCargo.setCellValueFactory(
                new PropertyValueFactory<>("cargo")
        );

        colArea.setCellValueFactory(
                new PropertyValueFactory<>("areaTrabajo")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fechaContratacion")
        );

        colContrato.setCellValueFactory(
                new PropertyValueFactory<>("tipoContrato")
        );

        colBeneficios.setCellValueFactory(
                new PropertyValueFactory<>("beneficios")
        );

        Image imagen = new Image(
                getClass().getResourceAsStream(
                        "/ni/edu/uam/registro_app/images/LogoUAM.png"
                )
        );

        imgLogoUam.setImage(imagen);

        cargarTabla();
    }


}

}
