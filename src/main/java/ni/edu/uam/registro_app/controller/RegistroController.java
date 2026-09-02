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
                "Supervisor"
        );

        lvAreaTrabajo.getItems().addAll(
                "Administración",
                "Contabilidad",
                "Ventas",
                "Bodega",
                "Caja",
                "Recursos Humanos"
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

    private Colaborador leerDatos() {

        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();
        String cargo = combCargo.getValue();
        String area = lvAreaTrabajo.getSelectionModel().getSelectedItem();
        LocalDate fecha = dpfechaCont.getValue();

        String contrato = "";

        if (rbDeterminado.isSelected()) {
            contrato = "Determinado";
        }

        if (rbIndeterminado.isSelected()) {
            contrato = "Indeterminado";
        }

        String beneficios = obtenerBeneficios();

        return new Colaborador(
                nombres,
                apellidos,
                usuario,
                contrasena,
                cargo,
                area,
                fecha,
                contrato,
                beneficios
        );
    }

    private String obtenerBeneficios() {

        StringBuilder beneficios = new StringBuilder();

        if (chkSeguro.isSelected()) {
            beneficios.append("Seguro médico");
        }

        if (chkAlimentacion.isSelected()) {

            if (!beneficios.isEmpty()) {
                beneficios.append(", ");
            }

            beneficios.append("Alimentación");
        }

        if (chkTransporte.isSelected()) {

            if (!beneficios.isEmpty()) {
                beneficios.append(", ");
            }

            beneficios.append("Transporte");
        }

        return beneficios.toString();
    }

    @FXML
    protected void guardarOnClick() {

        if (!validarCampos()) {
            return;
        }

        Colaborador colaborador = leerDatos();

        listado.agregar(colaborador);

        cargarTabla();
        limpiarCampos();

        mostrarInformacion("Colaborador guardado correctamente.");
    }

    @FXML
    protected void actualizarOnClick() {

        if (indiceSeleccionado == -1) {
            mostrarAlerta("Seleccione un colaborador para actualizar.");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        Colaborador colaborador = leerDatos();

        listado.actualizar(indiceSeleccionado, colaborador);

        cargarTabla();
        limpiarCampos();

        mostrarInformacion("Colaborador actualizado correctamente.");
    }

    @FXML
    protected void eliminarOnClick() {

        int indice = tbtablaRegistros
                .getSelectionModel()
                .getSelectedIndex();

        if (indice == -1) {
            mostrarAlerta("Seleccione un colaborador para eliminar.");
            return;
        }

        listado.eliminar(indice);

        cargarTabla();
        limpiarCampos();

        mostrarInformacion("Colaborador eliminado correctamente.");
    }

    @FXML
    protected void limpiarOnClick() {
        limpiarCampos();
    }

    private void cargarTabla() {

        ObservableList<Colaborador> colaboradores =
                FXCollections.observableArrayList(
                        listado.obternerRegistros()
                );

        tbtablaRegistros.setItems(colaboradores);

        lblRegistro.setText(
                "Registros guardados: "
                        + listado.obternerRegistros().size()
        );
    }

    private boolean validarCampos() {

        if (txtNombres.getText().trim().isEmpty()
                || txtApellidos.getText().trim().isEmpty()
                || txtUsuario.getText().trim().isEmpty()
                || txtContrasena.getText().isEmpty()
                || combCargo.getValue() == null
                || lvAreaTrabajo.getSelectionModel().getSelectedItem() == null
                || dpfechaCont.getValue() == null
                || grupoContrato.getSelectedToggle() == null) {

            mostrarAlerta("Ningún campo puede quedar vacío.");
            return false;
        }

        if (txtUsuario.getText().trim().length() < 5) {
            mostrarAlerta(
                    "El usuario debe tener al menos 5 caracteres."
            );
            return false;
        }

        if (txtContrasena.getText().length() < 8) {
            mostrarAlerta(
                    "La contraseña debe tener al menos 8 caracteres."
            );
            return false;
        }

        if (dpfechaCont.getValue().isAfter(LocalDate.now())) {
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
    private void tablaMouseClicked(MouseEvent event) {

        if (event.getClickCount() == 2) {

            Colaborador colaborador =
                    tbtablaRegistros
                            .getSelectionModel()
                            .getSelectedItem();

            if (colaborador != null) {

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

                if (colaborador
                        .getTipoContrato()
                        .equals("Determinado")) {

                    rbDeterminado.setSelected(true);

                } else {

                    rbIndeterminado.setSelected(true);
                }

                cargarBeneficios(
                        colaborador.getBeneficios()
                );
            }
        }
    }

    private void cargarBeneficios(String beneficios) {

        chkSeguro.setSelected(
                beneficios.contains("Seguro médico")
        );

        chkAlimentacion.setSelected(
                beneficios.contains("Alimentación")
        );

        chkTransporte.setSelected(
                beneficios.contains("Transporte")
        );
    }

    @FXML
    private void manejarTeclado(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            guardarOnClick();

        } else if (event.getCode() == KeyCode.ESCAPE) {

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

    private void mostrarAlerta(String mensaje) {

        Alert alert = new Alert(
                Alert.AlertType.WARNING
        );

        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}