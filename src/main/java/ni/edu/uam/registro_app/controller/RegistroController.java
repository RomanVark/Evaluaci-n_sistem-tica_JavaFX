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
    private TableColumn<Colaborador, String> colNombre;

    @FXML
    private TableColumn<Colaborador, String> colApellido;

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

    @FXML
    public void initialize() {

        cargarCargos();

        cargarAreas();

        configurarTabla();

        cargarLogo();

        cargarTabla();
    }

    private void cargarCargos() {

        combCargo.getItems().addAll(
                "Administrador",
                "Contador",
                "Vendedor",
                "Bodeguero",
                "Cajero",
                "Supervisor"
        );
    }

    private void cargarAreas() {

        lvAreaTrabajo.getItems().addAll(
                "Administración",
                "Contabilidad",
                "Ventas",
                "Bodega",
                "Caja",
                "Recursos Humanos"
        );
    }

    private void configurarTabla() {

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombres")
        );

        colApellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidos")
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
    }

    private void cargarLogo() {

        Image imagen = new Image(
                getClass().getResourceAsStream(
                        "/ni/edu/uam/registro_app/images/LogoUAM.png"
                )
        );

        imgLogoUam.setImage(imagen);
    }

    private Colaborador leerDatos() {

        String nombres =
                txtNombres.getText().trim();

        String apellidos =
                txtApellidos.getText().trim();

        String usuario =
                txtUsuario.getText().trim();

        String contrasena =
                txtContrasena.getText();

        String cargo =
                combCargo.getValue();

        String area =
                lvAreaTrabajo
                        .getSelectionModel()
                        .getSelectedItem();

        LocalDate fecha =
                dpfechaCont.getValue();

        String contrato;

        if (rbDeterminado.isSelected()) {
            contrato = "Determinado";
        } else {
            contrato = "Indeterminado";
        }

        String beneficios =
                obtenerBeneficios();

        return new Colaborador(
                cargo,
                nombres,
                usuario,
                apellidos,
                contrasena,
                area,
                fecha,
                contrato,
                beneficios
        );
    }

    private String obtenerBeneficios() {

        StringBuilder beneficios =
                new StringBuilder();

        if (chkSeguro.isSelected()) {

            beneficios.append(
                    "Seguro médico"
            );
        }

        if (chkAlimentacion.isSelected()) {

            if (beneficios.length() > 0) {
                beneficios.append(", ");
            }

            beneficios.append(
                    "Alimentación"
            );
        }

        if (chkTransporte.isSelected()) {

            if (beneficios.length() > 0) {
                beneficios.append(", ");
            }

            beneficios.append(
                    "Transporte"
            );
        }

        return beneficios.toString();
    }

    @FXML
    protected void guardarOnClick() {

        if (!validarCampos()) {
            return;
        }

        Colaborador colaborador =
                leerDatos();

        listado.agregar(colaborador);

        cargarTabla();

        limpiarCampos();

        mostrarInformacion(
                "Colaborador guardado correctamente."
        );
    }

    @FXML
    protected void actualizarOnClick() {

        if (indiceSeleccionado == -1) {

            mostrarAlerta(
                    "Seleccione un colaborador para actualizar."
            );

            return;
        }

        if (!validarCampos()) {
            return;
        }

        Colaborador colaborador =
                leerDatos();

        listado.actualizar(
                indiceSeleccionado,
                colaborador
        );

        cargarTabla();

        limpiarCampos();

        mostrarInformacion(
                "Colaborador actualizado correctamente."
        );
    }

    @FXML
    protected void eliminarOnClick() {

        int indice =
                tbtablaRegistros
                        .getSelectionModel()
                        .getSelectedIndex();

        if (indice == -1) {

            mostrarAlerta(
                    "Seleccione un colaborador para eliminar."
            );

            return;
        }

        listado.eliminar(indice);

        cargarTabla();

        limpiarCampos();

        mostrarInformacion(
                "Colaborador eliminado correctamente."
        );
    }

    @FXML
    protected void limpiarOnClick() {

        limpiarCampos();
    }

    private void cargarTabla() {

        ObservableList<Colaborador> colaboradores =
                FXCollections.observableArrayList(
                        listado.obtenerRegistros()
                );

        tbtablaRegistros.setItems(
                colaboradores
        );

        lblRegistro.setText(
                "Registros guardados: "
                        + listado
                        .obtenerRegistros()
                        .size()
        );
    }

    private boolean validarCampos() {

        if (txtNombres.getText().trim().isEmpty()) {

            mostrarAlerta(
                    "Ingrese los nombres."
            );

            return false;
        }

        if (txtApellidos.getText().trim().isEmpty()) {

            mostrarAlerta(
                    "Ingrese los apellidos."
            );

            return false;
        }

        if (txtUsuario.getText().trim().isEmpty()) {

            mostrarAlerta(
                    "Ingrese un usuario."
            );

            return false;
        }

        if (txtContrasena.getText().isEmpty()) {

            mostrarAlerta(
                    "Ingrese una contraseña."
            );

            return false;
        }

        if (combCargo.getValue() == null) {

            mostrarAlerta(
                    "Seleccione un cargo."
            );

            return false;
        }

        if (lvAreaTrabajo
                .getSelectionModel()
                .getSelectedItem() == null) {

            mostrarAlerta(
                    "Seleccione un área de trabajo."
            );

            return false;
        }

        if (dpfechaCont.getValue() == null) {

            mostrarAlerta(
                    "Seleccione una fecha de contratación."
            );

            return false;
        }

        if (grupoContrato.getSelectedToggle() == null) {

            mostrarAlerta(
                    "Seleccione un tipo de contrato."
            );

            return false;
        }

        if (txtUsuario
                .getText()
                .trim()
                .length() < 5) {

            mostrarAlerta(
                    "El usuario debe tener al menos 5 caracteres."
            );

            return false;
        }

        if (txtContrasena
                .getText()
                .length() < 8) {

            mostrarAlerta(
                    "La contraseña debe tener al menos 8 caracteres."
            );

            return false;
        }

        if (dpfechaCont
                .getValue()
                .isAfter(LocalDate.now())) {

            mostrarAlerta(
                    "La fecha de contratación no puede ser posterior a la fecha actual."
            );

            return false;
        }

        if (!chkSeguro.isSelected()
                && !chkAlimentacion.isSelected()
                && !chkTransporte.isSelected()) {

            mostrarAlerta(
                    "Debe seleccionar al menos un beneficio."
            );

            return false;
        }

        return true;
    }

    @FXML
    private void tablaMouseClicked(
            MouseEvent event
    ) {

        if (event.getClickCount() == 2) {

            Colaborador colaborador =
                    tbtablaRegistros
                            .getSelectionModel()
                            .getSelectedItem();

            if (colaborador == null) {
                return;
            }

            indiceSeleccionado =
                    tbtablaRegistros
                            .getSelectionModel()
                            .getSelectedIndex();

            txtNombres.setText(
                    colaborador.getNombres()
            );

            txtApellidos.setText(
                    colaborador.getApellidos()
            );

            txtUsuario.setText(
                    colaborador.getUsuario()
            );

            txtContrasena.setText(
                    colaborador.getContrasena()
            );

            combCargo.setValue(
                    colaborador.getCargo()
            );

            lvAreaTrabajo
                    .getSelectionModel()
                    .select(
                            colaborador.getAreaTrabajo()
                    );

            dpfechaCont.setValue(
                    colaborador.getFechaContratacion()
            );

            if ("Determinado".equals(
                    colaborador.getTipoContrato()
            )) {

                rbDeterminado
                        .setSelected(true);

            } else {

                rbIndeterminado
                        .setSelected(true);
            }

            cargarBeneficios(
                    colaborador.getBeneficios()
            );
        }
    }

    private void cargarBeneficios(
            String beneficios
    ) {

        chkSeguro.setSelected(
                beneficios.contains(
                        "Seguro médico"
                )
        );

        chkAlimentacion.setSelected(
                beneficios.contains(
                        "Alimentación"
                )
        );

        chkTransporte.setSelected(
                beneficios.contains(
                        "Transporte"
                )
        );
    }

    @FXML
    private void manejarTeclado(
            KeyEvent event
    ) {

        if (event.getCode()
                == KeyCode.ENTER) {

            guardarOnClick();

        } else if (event.getCode()
                == KeyCode.ESCAPE) {

            limpiarCampos();
        }
    }

    private void limpiarCampos() {

        txtNombres.clear();
        txtApellidos.clear();
        txtUsuario.clear();
        txtContrasena.clear();

        combCargo.setValue(null);

        lvAreaTrabajo
                .getSelectionModel()
                .clearSelection();

        dpfechaCont.setValue(null);

        grupoContrato.selectToggle(null);

        chkSeguro.setSelected(false);
        chkAlimentacion.setSelected(false);
        chkTransporte.setSelected(false);

        tbtablaRegistros
                .getSelectionModel()
                .clearSelection();

        indiceSeleccionado = -1;
    }

    private void mostrarAlerta(
            String mensaje
    ) {

        Alert alert = new Alert(
                Alert.AlertType.WARNING
        );

        alert.setTitle(
                "Validación"
        );

        alert.setHeaderText(null);

        alert.setContentText(
                mensaje
        );

        alert.showAndWait();
    }

    private void mostrarInformacion(
            String mensaje
    ) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(
                "Información"
        );

        alert.setHeaderText(null);

        alert.setContentText(
                mensaje
        );

        alert.showAndWait();
    }
}