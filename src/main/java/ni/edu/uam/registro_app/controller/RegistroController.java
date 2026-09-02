package ni.edu.uam.registro_app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ni.edu.uam.registro_app.dao.RegistroDao;

import java.time.LocalDate;

public class RegistroController {
    RegistroDao listado = new RegistroDao();
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCarrera;
    @FXML
    private DatePicker dpfechaNac;
    @FXML
    private CheckBox chktieneBeca;
    @FXML
    private Label lblRegistro;
    @FXML
    private ImageView imgLogoUam;
    @FXML
    private ComboBox<String> combGenero;
    @FXML
    private ComboBox<String> combFacultad;
    @FXML
    private TableView<Estudiante> tbtablaRegistros;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, String> colApellido;
    @FXML
    private TableColumn<Estudiante, String> colCarrera;
    @FXML
    private TableColumn<Estudiante, String> colFacultad;

    public void cargarTabla() {
        ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList(listado.obternerRegistros());
        tbtablaRegistros.setItems(estudiantes);
    }




    public void initialize() {

        combGenero.getItems().addAll("Masculino", "Femenino", "Otro");

        combFacultad.getItems().addAll("UAM College", "Facultad de Odontología",
                "Facultad de Ciencias Médicas", "Facultad de Marketing, Diseño y Ciencias de la Comunicación",
                "Facultad de Ciencias Administrativas y Económicas",
                "Facultad de Ingeniería y Arquitectura",
                "Facultad de Ciencias Jurídicas, Humanidades y Relaciones Internacionales",
                "Language Center");

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colFacultad.setCellValueFactory(new PropertyValueFactory<>("facultad"));

        Image imagen = new Image(getClass().getResourceAsStream("/ni/edu/uam/registro_app/images/LogoUAM.png"));
        imgLogoUam.setImage(imagen);
    }

    @FXML
    protected void guardarOnClick(){
        leerDatos();

    }

    private void  leerDatos(){
        String nombre = txtNombres.getText();
        String apellido = txtApellidos.getText();
        String carrera = txtCarrera.getText();
        LocalDate fechaNa = dpfechaNac.getValue();
        Boolean beca = chktieneBeca.isSelected();
        String genero = combGenero.getValue();
        String facultad = combFacultad.getValue();
        agregarDatos(new Estudiante(nombre,apellido,carrera,fechaNa,beca, genero, facultad));
    }

    private void agregarDatos(Estudiante estudiante) {

        listado.agregar(estudiante);
        lblRegistro.setText("Registros Guardados: " + listado.obternerRegistros().size());
        cargarTabla();
    }
}