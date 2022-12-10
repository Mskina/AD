/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2.services.empleado;

import es.teis.ud2.exceptions.InstanceNotFoundException;
import es.teis.ud2.exceptions.SaldoInsuficienteException;
import es.teis.ud2.model.Account;
import es.teis.ud2.model.AccountMovement;
import es.teis.ud2.model.Empleado;
import es.teis.ud2.model.dao.account.IAccountDao;
import es.teis.ud2.model.dao.accountMovement.IAccountMovementDao;
import es.teis.ud2.model.dao.empleado.IEmpleadoDao;
import java.math.BigDecimal;

/**
 *
 * @author maria
 */
public class EmpleadoService implements IEmpleadoService {

    private IEmpleadoDao empleadoDao;
    private IAccountDao accountDao;
    private IAccountMovementDao accountMovementDao;

    public EmpleadoService(IEmpleadoDao empleadoDao, IAccountDao accountDao, IAccountMovementDao accountMovementDao) {
        this.empleadoDao = empleadoDao;
        this.accountDao = accountDao;
        this.accountMovementDao = accountMovementDao;
    }

    @Override
    public Empleado create(Empleado empleado) {
        return this.empleadoDao.create(empleado);
    }

    @Override
    public AccountMovement transferir(int empnoOrigen, int empnoDestino, BigDecimal cantidad) throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException {

        // Comprobamos que la cantidad es mayor que cero. En caso contrario,
        // lanzamos excepción
        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UnsupportedOperationException("Debes transferir una cantidad mayor que 0.");
        }

        //Recuperamos un objeto Account desde el número de empleado de origen.
        Account cuentaOrigen = this.accountDao.getAccountByEmpleadoId(empnoOrigen);

        // Si lo encuentra y no hay saldo suficiente, lanza excepción
        if (cantidad.compareTo(cuentaOrigen.getMontante()) >= 0) {
            throw new SaldoInsuficienteException("Tienes %.2f e intentas transferir %.2f", cuentaOrigen.getMontante(), cantidad);
        }

        // Si hay saldo, recuperamos un Account por el empleado de destino
        Account cuentaDestino = this.accountDao.getAccountByEmpleadoId(empnoDestino);

        // Con los ID de las cuentas origen y destino, llamamos a accountDao.transferir
        int transaccionId = this.accountDao.transferir(cuentaOrigen.getAccountId(), cuentaDestino.getAccountId(), cantidad);

        // Con el ID de transacción, obtenemos un AccountMovement.
        // Si no lo encuentra, lanza excepción (ya es por defecto)   
        return this.accountMovementDao.read(transaccionId);
    }

}
