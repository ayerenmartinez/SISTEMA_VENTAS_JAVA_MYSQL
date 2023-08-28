
package datos.interfaces;

import java.util.List;

/**
 *
 * @author ayeren
 */
public interface CrudSimpleInterface<T> {
    public List<T> listar(String texto);
    public Boolean insertar(T obj);
    public Boolean actualizar(T obj);
    public Boolean desactivar(int id);
    public Boolean activar(int id);
    public int total();
    public Boolean existe(String texto);
}
