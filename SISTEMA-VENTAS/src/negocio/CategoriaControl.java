
package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ayeren
 */
public class CategoriaControl {
    private final CategoriaDAO DATOS;
    private Categoria obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;
            
    public CategoriaControl(){
        this.DATOS = new CategoriaDAO();
        this.obj = new Categoria();
        this.registrosMostrados = 0;
    }
    
    public DefaultTableModel listar(String texto){
        List<Categoria> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));       
        String [] titulos = {"Id","Nombre","Descripción","Estado"};
        this.modeloTabla = new DefaultTableModel(null,titulos);
        String estado;
        String[] registro = new String[4];
        this.registrosMostrados = 0;
        for(Categoria item : lista){
            estado = item.getActivo() ? "Activo" : "Inactivo";
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            registro[3] = estado;
            this.modeloTabla.addRow(registro);
            this.registrosMostrados++;
        }
        return modeloTabla;
    }
    
    public String insertar(String nombre, String descripcion){
        if(DATOS.existe(nombre)){
            return "El registro ya existe";
        }else{
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            return DATOS.insertar(obj) ? "OK" : "Error en el registro";
        }
    }
    
    public String actualizar(int id, String nombre, String nombreAnt, String descripcion){
        if(nombre.equals(nombreAnt)){
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            return DATOS.actualizar(obj) ? "OK" : "Error en la actualización";
        }else{
             if(DATOS.existe(nombre)){
                 return "El registro ya existe";
             }else{
                 obj.setId(id);
                 obj.setNombre(nombre);
                 obj.setDescripcion(descripcion);
                 return DATOS.actualizar(obj) ? "OK" : "Error en la actualización";
             }
        }
    }
    
    public String desactivar(int id){
        return DATOS.desactivar(id) ? "OK" : "No se puede desactivar el registro";
    }
    
    public String activar(int id){
         return DATOS.activar(id) ? "OK" : "No se puede activar el registro";
    }
    
    public int total(){
        return DATOS.total();
    }
    
    public int totalMostrados(){
        return this.registrosMostrados;
    }
}
