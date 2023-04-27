package Functions;
import java.sql.*;
import javax.swing.JOptionPane;

public class Functions {
    private String MySqlDriver = "com.mysql.cj.jdbc.Driver";
    private String MySqlDB = "jdbc:mysql://localhost:3306/tablajava?characterEncoding=utf8";
    private String SqlConsulta;
    private String user = "";
    private String password = "";
    
    public void setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public Functions() {
        try {
            Class.forName(this.MySqlDriver);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a Base de Datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al conectar a Base de Datos: " + e.getMessage());
            System.exit(0);
        }
    }
    
    private Connection Conexion;
    private ResultSet registros;
    
    public void conectar() {
        try {
            this.Conexion = DriverManager.getConnection(this.MySqlDB, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la conexion: " + e.getMessage());
            System.exit(0);
        }
    }
    
    public void desconectar() {
        try {
            this.Conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al desconectar: " + e.getMessage());
            System.exit(0);
        }
    }
    
    public ResultSet consultaEmpleado() {
        conectar();
        try {
            this.SqlConsulta = "select * from empleados";
            PreparedStatement sentencia = this.Conexion.prepareStatement(this.SqlConsulta);
            this.registros = sentencia.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return this.registros;
    }
    
    public void altaEmpleado(String nombre, String appat, String apmat, String domicilio, String rfc, String nss){
        conectar();
        try {
            this.SqlConsulta = "insert into empleados set nombre=?, appat=?, apmat=?, domicilio=?, rfc=?, nss=?, categoria=1";
            PreparedStatement sentencia = this.Conexion.prepareStatement(this.SqlConsulta);
            sentencia.setString(1, nombre);
            sentencia.setString(2, appat);
            sentencia.setString(3, apmat);
            sentencia.setString(4, domicilio);
            sentencia.setString(5, rfc);
            sentencia.setString(6, nss);
            sentencia.executeUpdate();
            // this.registros = sentencia.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la consulta: " + e.getMessage());
            System.exit(0);
        }
        desconectar();
    }
    
    public void updateEmpleado(String id, String nombre, String appat, String apmat, String domicilio, String rfc, String nss){
        conectar();
        try {
            this.SqlConsulta = "update empleados set nombre=?, appat=?, apmat=?, domicilio=?, rfc=?, nss=?, categoria=1 where idEmpleado=?";
            PreparedStatement sentencia = this.Conexion.prepareStatement(this.SqlConsulta);
            sentencia.setString(1, nombre);
            sentencia.setString(2, appat);
            sentencia.setString(3, apmat);
            sentencia.setString(4, domicilio);
            sentencia.setString(5, rfc);
            sentencia.setString(6, nss);
            sentencia.setString(7, id);
            sentencia.executeUpdate();
            // this.registros = sentencia.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la actualización: " + e.getMessage());
            System.exit(0);
        }
        desconectar();
    }
    
    public boolean obtenerEstado (String id) {
        conectar();
        String dato = "";
        try {
            this.SqlConsulta = "select status from empleados where idEmpleado=?";
            PreparedStatement sentencia = this.Conexion.prepareStatement(this.SqlConsulta);
            sentencia.setString(1, id);
            this.registros = sentencia.executeQuery();
            if (registros.next()) {
                dato = registros.getString("status");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la obtención de estado: " + e.getMessage());
        }
        return "1".equals(dato);
    }
    
    public void actualizarEstado(String id, String status) {
        conectar();
        try {
            this.SqlConsulta = "update empleados set status=? where idEmpleado=?";
            PreparedStatement sentencia = this.Conexion.prepareStatement(this.SqlConsulta);
            sentencia.setString(1, status);
            System.out.println("update empleados set status=" + status + " where idEmpleado=" + id);
            sentencia.setString(2, id);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en la actualización de estado: " + e.getMessage());
        }
        desconectar();
    }
}
