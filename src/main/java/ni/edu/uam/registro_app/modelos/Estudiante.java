package ni.edu.uam.registro_app.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor



public class Estudiante {
    private String nombre;
    private String apellido;
    private String carrera;
    private LocalDate fechaNacimiento;
    private Boolean tienBeca;
    private String genero;
    private String facultad;


}
