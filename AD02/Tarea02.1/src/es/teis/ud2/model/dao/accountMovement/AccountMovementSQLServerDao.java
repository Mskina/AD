/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.teis.ud2.model.dao.accountMovement;

import es.teis.ud2.data.DBCPDataSourceFactory;
import es.teis.ud2.exceptions.InstanceNotFoundException;
import es.teis.ud2.model.Account;
import es.teis.ud2.model.AccountMovement;
import es.teis.ud2.model.dao.AbstractGenericDao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.DataSource;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class AccountMovementSQLServerDao extends AbstractGenericDao<AccountMovement> implements IAccountMovementDao {

    private DataSource dataSource;
    
    public AccountMovementSQLServerDao() {
        this.dataSource = DBCPDataSourceFactory.getDataSource();
    }

    @Override
    public AccountMovement read(int id) throws InstanceNotFoundException {

        int accountMovId;
        int accountOriginId;
        int accountDestId;
        BigDecimal amount;
        Timestamp datetime;
        AccountMovement accountMovement = null;
        
        try (
                Connection conexion = this.dataSource.getConnection();
                PreparedStatement sentencia
                = conexion.prepareStatement("SELECT [ACCOUNT_MOV_ID]\n"
                        + ",[ACCOUNT_ORIGIN_ID]\n"
                        + ",[ACCOUNT_DEST_ID]\n"
                        + ",[AMOUNT]\n"
                        + ",[DATETIME]\n"
                        + "FROM [dbo].[ACC_MOVEMENT]"
                        + "WHERE ACCOUNT_MOV_ID=?");) {
            sentencia.setInt(1, id);

            ResultSet result = sentencia.executeQuery();
            if (result.next()) {
                int contador = 0;

                accountMovId = result.getInt(++contador);
                accountOriginId = result.getInt(++contador);
                accountDestId = result.getInt(++contador);
                amount = result.getBigDecimal(++contador);
                datetime = result.getTimestamp(++contador);
                
                Account accountOrigin = new Account();
                accountOrigin.setAccountId(accountOriginId);

                Account accountDest = new Account();
                accountDest.setAccountId(accountDestId);
                
                
                accountMovement = new AccountMovement(accountMovId, accountOrigin, accountDest, amount, datetime);

            } else {
                throw new InstanceNotFoundException(id, getEntityClass());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
        return accountMovement;
    }

    @Override
    public AccountMovement create(AccountMovement entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(AccountMovement entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
