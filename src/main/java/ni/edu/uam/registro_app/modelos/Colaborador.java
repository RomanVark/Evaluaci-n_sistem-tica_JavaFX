package ni.edu.uam.registro_app.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Colaborador {

    private String cargo;
    private String nombres;
    private String usuario;
    private String apellidos;
    private String contrasena;
    private String areaTrabajo;
    private LocalDate fechaContratacion;
    private String tipoContrato;
    private String beneficios;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}