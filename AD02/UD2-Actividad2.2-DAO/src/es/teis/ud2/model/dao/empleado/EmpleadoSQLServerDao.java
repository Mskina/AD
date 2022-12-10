/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2.model.dao.empleado;

import es.teis.ud2.data.DBCPDataSourceFactory;
import es.teis.ud2.exceptions.InstanceNotFoundException;
import es.teis.ud2.model.Empleado;
import es.teis.ud2.model.dao.AbstractGenericDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author mfernandez
 */
public class EmpleadoSQLServerDao extends AbstractGenericDao<Empleado>
        implements IEmpleadoDao {

    private DataSource dataSource;

    public EmpleadoSQLServerDao() {
        this.dataSource = DBCPDataSourceFactory.getDataSource();
    }

    @Override
    public Empleado create(Empleado entity) {
        try (
                 Connection conexion = this.dataSource.getConnection();  PreparedStatement pstmt = conexion.prepareStatement(
                "INSERT INTO [dbo].[EMP]\n"
                + "           ([ENAME]\n"
                + "           ,[JOB]\n"
                + "           ,[MGR]\n"
                + "           ,[HIREDATE]\n"
                + "           ,[SAL]\n"
                + "           ,[COMM]\n"
                + "           ,[DEPTNO])\n"
                + "     VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );) {

            pstmt.setString(1, entity.getNombre());
            pstmt.setString(2, entity.getPuesto());
            pstmt.setInt(3, entity.getJefe().getEmpleadoId());
            pstmt.setDate(4, Date.valueOf(entity.getFechaContratacion()));
            pstmt.setBigDecimal(5, entity.getSalario());
            pstmt.setBigDecimal(6, entity.getComision());
            pstmt.setInt(7, entity.getDepartamento().getDeptno());

            // Devolverá 0 para las sentencias SQL que no devuelven nada o el número de filas afectadas
            int result = pstmt.executeUpdate();

            ResultSet clavesResultado = pstmt.getGeneratedKeys();

            if (clavesResultado.next()) {
                int empleadoId = clavesResultado.getInt(1);
                entity.setEmpleadoId(empleadoId);
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
    public Empleado read(int id) throws InstanceNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Empleado entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
