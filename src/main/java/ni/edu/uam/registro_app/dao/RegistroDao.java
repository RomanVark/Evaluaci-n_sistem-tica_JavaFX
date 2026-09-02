package ni.edu.uam.registro_app.dao;

import ni.edu.uam.registro_app.interfaces.Crud;
import ni.edu.uam.registro_app.modelos.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class RegistroDao implements Crud<Estudiante> {

     List<Estudiante> estudiantes;

     public RegistroDao(){
         estudiantes=new ArrayList<>();
     }
    @Override
    public void agregar(Estudiante entidad) {
         estudiantes.add(entidad);

    }

    @Override
    public List<Estudiante> obternerRegistros() {

         return estudiantes;
    }
}
