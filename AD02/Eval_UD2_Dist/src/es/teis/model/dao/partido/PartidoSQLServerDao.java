/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.model.dao.partido;

import es.teis.data.exceptions.InstanceNotFoundException;
import es.teis.db.DBCPDataSourceFactory;
import es.teis.model.Partido;
import es.teis.model.dao.AbstractGenericDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author dominator
 */
public class PartidoSQLServerDao extends AbstractGenericDao<Partido> implements IPartidoDao {

    private DataSource dataSource;

    public PartidoSQLServerDao() {
        this.dataSource = DBCPDataSourceFactory.getDataSource();
    }

    @Override
    public Partido create(Partido entity) {
        try (
                 Connection conexion = this.dataSource.getConnection();  PreparedStatement pstmt = conexion.prepareStatement(
                "INSERT INTO [dbo].[PARTIDO06]\n"
                + "           ([NOMBRE]\n"
                + "           ,[PORCENTAJE]\n"
                + "           ,[NUMERO_VOTOS])\n"
                + "     VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );) {

            pstmt.setString(1, entity.getNombre());
            pstmt.setFloat(2, entity.getPorcentaje());
            pstmt.setInt(3, entity.getVotos());

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            int resultado = pstmt.executeUpdate();

            ResultSet clavesResultado = pstmt.getGeneratedKeys();

            if (clavesResultado.next()) {
                int partidoId = clavesResultado.getInt(1);
                entity.setId(partidoId);
            } else {
                entity = null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
            entity = null;
        }
        return entity;
    }

    @Override
    public Partido read(int id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Partido entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean partidoExists(String nombre) {
        boolean existe = false;
        try (
                 Connection conexion = this.dataSource.getConnection();  PreparedStatement pstmt = conexion.prepareStatement("SELECT * "
                + "FROM PARTIDO06 WHERE NOMBRE LIKE ?");) {

            pstmt.setString(1, nombre);

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                existe = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
        return existe;
    }

    @Override
    public boolean transferirVotos(String nombreOrigen, String nombreDestino, int cantidadVotos) {

        boolean exito = false;
        Connection con = null;
        PreparedStatement updateOrigen = null;
        PreparedStatement updateDestino = null;
        try {
            con = this.dataSource.getConnection();

            updateOrigen = con.prepareStatement("UPDATE [dbo].[PARTIDO06]\n"
                    + "   SET [numero_votos] = (numero_votos - ?) \n"
                    + " WHERE nombre = ?");
            updateDestino = con.prepareStatement("UPDATE [dbo].[PARTIDO06]\n"
                    + "   SET [numero_votos] = (numero_votos + ?) \n"
                    + " WHERE nombre = ?");          
            
            con.setAutoCommit(false);
            
            updateOrigen.setInt(1, cantidadVotos);
            updateOrigen.setString(2, nombreOrigen);
            updateOrigen.executeUpdate();
            
            System.out.println("Eliminados " +cantidadVotos + " votos de " + nombreDestino);

            updateDestino.setInt(1, cantidadVotos);
            updateDestino.setString(2, nombreDestino);
            updateDestino.executeUpdate();
            
            System.out.println("Añadidos " +cantidadVotos + " votos a " + nombreOrigen);
            
            
            con.commit();
            exito = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha habido una excepción. " + ex.getMessage());
            
            try {
                con.rollback();
            } catch (SQLException exr) {
                ex.printStackTrace();
                System.err.println("Ha habido una excepción haciendo rollback: " + exr.getMessage());
            }
            
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    
                    if (updateOrigen != null) {
                        updateOrigen.close();
                    }
                    if (updateDestino != null) {
                        updateDestino.close();
                    }
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.err.println("Ha habido una excepción cerrando los recursos: " + ex.getMessage());
                }
            }
        }
        return exito;
    }

}
