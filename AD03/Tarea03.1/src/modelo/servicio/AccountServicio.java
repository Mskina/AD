package modelo.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;
import modelo.Departamento;
import exceptions.InstanceNotFoundException;
import util.SessionFactoryUtil;

public class AccountServicio implements IAccountServicio {

	@Override
	public Account findAccountById(int accId) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Account account = session.get(Account.class, accId);
		if (account == null) {
			throw new InstanceNotFoundException(Account.class.getName());
		}

		session.close();
		return account;
	}

	@Override
	public AccMovement transferir(int accOrigen, int accDestino, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException {

		Transaction tx = null;
		Session session = null;
		AccMovement movement = null;

		try {

			if (cantidad <= 0) {
				throw new UnsupportedOperationException();
			}
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Account accountOrigen = session.get(Account.class, accOrigen);
			if (accountOrigen == null) {
				throw new InstanceNotFoundException(Account.class.getName() + " origen id:" + accOrigen);
			}
			BigDecimal cantidadBD = new BigDecimal(cantidad);
			if (accountOrigen.getAmount().compareTo(cantidadBD) < 0) {
				throw new SaldoInsuficienteException("No hay saldo suficiente", accountOrigen.getAmount(), cantidadBD);
			}
			Account accountDestino = session.get(Account.class, accDestino);
			if (accountDestino == null) {
				throw new InstanceNotFoundException(Account.class.getName() + " destino id:" + accDestino);
			}

			tx = session.beginTransaction();

			accountOrigen.setAmount(accountOrigen.getAmount().subtract(cantidadBD));
			accountDestino.setAmount(accountDestino.getAmount().add(cantidadBD));

			movement = new AccMovement();
			movement.setAmount(cantidadBD);
			movement.setDatetime(LocalDateTime.now());

			// Relación bidireccional
			movement.setAccountOrigen(accountOrigen);
			movement.setAccountDestino(accountDestino);
			// Son prescindibles y no recomendables en navegación bidireccional porque una
			// Account puede tener numerosos movimientos
//					accountOrigen.getAccMovementsOrigen().add(movement);
//					accountDestino.getAccMovementsDest().add(movement);

//					session.saveOrUpdate(accountOrigen);
//					session.saveOrUpdate(accountDestino);
			session.save(movement);

			tx.commit();

		} catch (Exception ex) {
			System.out.println("Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return movement;

	}

	/**
	 * 5. Guardará o actualizará una cuenta en una transacción
	 */
	@Override
	public Account saveOrUpdate(Account d) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(d); // Con la misma instrucción, se crea o actualiza
			tx.commit();
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en saveOrUpdate: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}

		// Por validar: ¿al ser únicamente un cambio, se podría hacer sin transacción?
//		try {
//			session.saveOrUpdate(d);
//		} catch (Exception ex) {
//			System.out.println("Ha ocurrido una excepción en saveOrUpdate: " + ex.getMessage());
//			throw ex;
//		} finally {
//			session.close();
//		}

		return d;
	}

	/**
	 * 6- Modificará el saldo de la cuenta según la cantidad, que puede ser negativa
	 * o positiva. Será la diferencia entra la cantidad previa y la actual con el
	 * signo invertido. Creará además un AccMovement en una transacción. Si no
	 * encuentra la cuenta, lanzará una excepción InstanceNotFoundException
	 */
	@Override
	public AccMovement autoTransferir(int accountNo, double cantidad) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		AccMovement movimiento = null;

		try {
			Account cuenta = session.get(Account.class, accountNo);

			if (cuenta == null) {
				throw new InstanceNotFoundException(Account.class.getName());
			}
			
			BigDecimal transfer = BigDecimal.valueOf(cantidad);

			BigDecimal saldoInicial = cuenta.getAmount();
			BigDecimal saldoFinal = saldoInicial.add(transfer);
			
			cuenta.setAmount(saldoFinal);

			tx = session.beginTransaction();

			movimiento = new AccMovement(cuenta, cuenta, transfer, LocalDateTime.now());

			session.save(movimiento); // El movimiento siempre sería save, porque no existe (no se actualiza)

			tx.commit();
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en autoTransferir: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
		return movimiento;
	}

	/**
	 * 7- Se eliminará la cuenta y todos sus movimientos con la opción cascade
	 */
	@Override
	public boolean delete(int accId) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean exito = false;

		try {
			// Intento encontrar la cuennta
			Account ac = session.get(Account.class, accId);

			if (ac == null) {
				throw new InstanceNotFoundException(Account.class.getName() + " id: " + accId);
			}

			tx = session.beginTransaction();
			session.remove(ac);
			tx.commit();
			exito = true;
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en delete: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
		return exito;
	}

	/**
	 * 8- Busca todas las cuentas del empleado y las guarda en un List 
	 */
	@Override
	public List<Account> getAccountsByEmpno(int empno) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		List<Account> cuentas = session.createQuery("select a from Account a where a.emp.empno = :empno order by a.accountno").setParameter("empno", empno).list();
		session.close();

		return cuentas;
	}
}
