package ni.edu.uam.registro_app.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PrivateKey;
import java.time.LocalDate;

public class Colaborador {
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    public class Colaborador {
        private String nombres;
        private  String apellidos;
        private String Usiario;
        private String contrasena;
        private String cargo;
        private String areaTrabajo;
        private LocalDate fechaContratacion;
        private String tipoContrato;
        private String beneficios;

        public String getNombreCompleto() {
            return nombres + " " + apellidos;
        }

    }
}
