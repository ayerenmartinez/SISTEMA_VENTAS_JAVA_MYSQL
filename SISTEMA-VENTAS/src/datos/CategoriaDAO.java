
package datos;

import database.Conexion;
import datos.interfaces.CrudSimpleInterface;
import entidades.Categoria;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ayeren
 */
public class CategoriaDAO implements CrudSimpleInterface<Categoria>{
 
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private Boolean resp = false;
    
    public CategoriaDAO(){
        CON = Conexion.getInstancia();
    }
    
    @Override
    public List<Categoria> listar(String texto) {
       List<Categoria> registros  = new ArrayList<>();
        try {
            ps = CON.conectar().prepareStatement("select * from categoria where nombre like ?");
            ps.setString(1,"%" + texto + "%");
            rs = ps.executeQuery();
            while(rs.next()){
                registros.add(new Categoria(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public Boolean insertar(Categoria obj) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO categoria (nombre, descripcion, activo) values(?,?,1)");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());
            if(ps.executeUpdate()>0){
                resp = true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public Boolean actualizar(Categoria obj) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE  categoria  SET nombre = ?, descripcion = ? WHERE id = ?");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDescripcion());
            ps.setInt(3, obj.getId());
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public Boolean desactivar(int id) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE  categoria  SET activo = 0 WHERE id = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public Boolean activar(int id) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE  categoria  SET activo = 1 WHERE id = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistros = 0;
        try {
            ps = CON.conectar().prepareStatement("SELECT COUNT(id) FROM categoria");
            rs = ps.executeQuery();
            while(rs.next()){
                totalRegistros = rs.getInt("COUNT(id)");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            rs = null;
            ps = null;
            CON.desconectar();
        }
        return totalRegistros;
    }

    @Override
    public Boolean existe(String texto) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT nombre FROM categoria WHERE nombre = ?");
            ps.setString(1, texto);
            rs = ps.executeQuery();
            //rs.last(); //se ubica en el ultimo registro
            if(rs.next()){
               if(rs.getRow()>0){ //si obtiene al menos una fila
                resp = true;
               } 
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }
    
}
