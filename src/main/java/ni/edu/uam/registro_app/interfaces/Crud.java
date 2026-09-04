package ni.edu.uam.registro_app.interfaces;

import java.util.List;

public interface Crud<T> {

    void agregar(T entidad);

    void actualizar(int indice, T entidad);

    void eliminar(int indice);

    List<T> obtenerRegistros();
}