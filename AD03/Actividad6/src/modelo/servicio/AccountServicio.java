package modelo.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;
import exceptions.InstanceNotFoundException;
import util.SessionFactoryUtil;

public class AccountServicio implements IAccountServicio {

	@Override
	public Account findAccountById(int accId) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Account ac = session.get(Account.class, accId);
		// IMPORTANTE cerrar: en este caso puede ir ya aquí porque no usamos la sesión.
		session.close();

		if (ac == null) {
			throw new InstanceNotFoundException(Account.class.getName());
		}

		return ac;
	}

	@Override
	public AccMovement transferir(int accOrigen, int accDestino, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException {

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		AccMovement am = null;

		try {
			// A. Comprobar que cantidad es mayor que cero.
			// En caso contrario lanzará una excepción UnsupportedOperationException

			if (cantidad <= 0) {
				throw new UnsupportedOperationException();
			}

			// AQUÍ se deberían iniciar tanto sessionFactory como session

			BigDecimal cantidadBigDecimal = BigDecimal.valueOf(cantidad);
			// También valdría BigDecimal cantidadBD = new BigDecimal(cantidad);

			// B-1. Recuperar un objeto Account origen
			Account origen = session.get(Account.class, accOrigen);
			if (origen == null) {
				throw new InstanceNotFoundException(Account.class.getName());
			}

			// B-2. Si encuentra el objeto Account y no hay saldo suficiente lanzará
			// SaldoInsuficienteException.
			if (origen.getAmount().compareTo(cantidadBigDecimal) < 0) {
				// number greater than zero if a > b, 0 if a == b, and less than zero if a < b
				// Si devuelve menos que cero --> No hay saldo suficiente
				throw new SaldoInsuficienteException("No hay saldo suficiente", origen.getAmount(), cantidadBigDecimal);
			}

			// AQUÍ se debería iniciar la transacción (tx)

			// B3. Si tiene suficiente saldo para hacer la transferencia, recuperará un
			// objeto Account destino y realizará las modificaciones en Account origen y
			// destino.
			Account destino = session.get(Account.class, accDestino);
			if (destino == null) {
				throw new InstanceNotFoundException(Account.class.getName());
			}

			// MAL: BigDecimal no es mutable, por lo que tenemos que hacer la operación por
			// un lado y setear el resultado por otro
//			origen.getAmount().subtract(cantidadBigDecimal);
//			destino.getAmount().add(cantidadBigDecimal);

			origen.setAmount(origen.getAmount().subtract(cantidadBigDecimal));
			destino.setAmount(destino.getAmount().add(cantidadBigDecimal));

			// C. Deberá crear un objeto AccMovement y lo guardará en BD.
			// Devolverá el objeto AccMovement

			am = new AccMovement(origen, destino, cantidadBigDecimal, LocalDateTime.now());

			// Guarda cambios individuales (comprobado en ejecución)
//			session.saveOrUpdate(origen);
//			session.saveOrUpdate(destino);
			session.save(am);
			tx.commit();

		} catch (Exception ex) {
			System.err.println("Excepción al transferir: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
			// Si al inicio no abriésemos la sesión, deberíamos comprobar
			// if session != null --> session.close()
		}

		return am;
	}

}
