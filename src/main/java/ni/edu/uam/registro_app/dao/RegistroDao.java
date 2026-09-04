package ni.edu.uam.registro_app.dao;

import ni.edu.uam.registro_app.interfaces.Crud;
import ni.edu.uam.registro_app.modelos.Colaborador;

import java.util.ArrayList;
import java.util.List;

public class RegistroDao implements Crud<Colaborador> {

    private final List<Colaborador> colaboradores;

    public RegistroDao() {
        colaboradores = new ArrayList<>();
    }

    @Override
    public void agregar(Colaborador entidad) {
        colaboradores.add(entidad);
    }

    @Override
    public void actualizar(int indice, Colaborador entidad) {

        if (indice >= 0 && indice < colaboradores.size()) {
            colaboradores.set(indice, entidad);
        }
    }

    @Override
    public void eliminar(int indice) {

        if (indice >= 0 && indice < colaboradores.size()) {
            colaboradores.remove(indice);
        }
    }

    @Override
    public List<Colaborador> obtenerRegistros() {
        return colaboradores;
    }
}