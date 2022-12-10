/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2;

import es.teis.ud2.exceptions.InstanceNotFoundException;
import es.teis.ud2.exceptions.SaldoInsuficienteException;
import es.teis.ud2.model.AccountMovement;
import es.teis.ud2.model.dao.account.AccountSQLServerDao;
import es.teis.ud2.model.dao.accountMovement.AccountMovementSQLServerDao;
import es.teis.ud2.model.dao.empleado.EmpleadoSQLServerDao;
import es.teis.ud2.services.empleado.EmpleadoService;
import es.teis.ud2.services.empleado.IEmpleadoService;
import java.math.BigDecimal;

/**
 *
 * @author maria
 */
public class Main {

    public static void main(String[] args) {

        AccountMovement accMovement = transferirDineroEntreEmpleados(7369, 7499, new BigDecimal(-1500));
        if (accMovement != null) {
            System.out.println("Se ha creado el registro: " + accMovement);
        }
    }


    private static AccountMovement transferirDineroEntreEmpleados(int empnoOrigen, int empnoDestino, BigDecimal cantidad) {
        AccountMovement accMovement = null;
        //Completa para crear el servicio y llamar a su método transferir(int empnoOrigen, int empnoDestino, BigDecimal cantidad)
        
        IEmpleadoService empleadoService = new EmpleadoService(new EmpleadoSQLServerDao(), new AccountSQLServerDao(), new AccountMovementSQLServerDao());
        
        try {
            accMovement = empleadoService.transferir(empnoOrigen, empnoDestino, cantidad);
        } catch (SaldoInsuficienteException ex) {
            System.out.println("Lo siento, no tienes saldo suficente en la cuenta. " + ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            System.out.println("Lo sentimos, algo ha salido mal. " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            System.out.println("No se ha podido llevar a cabo esta operación. " + ex.getMessage());
        }
        
        return accMovement;
    }

}
