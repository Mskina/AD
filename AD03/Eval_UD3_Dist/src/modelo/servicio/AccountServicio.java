package modelo.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import exceptions.SaldoInsuficienteException;
import modelo.ud3.AccMovement;
import modelo.ud3.Account;
import modelo.ud3.Departamento;
import modelo.ud3.Empleado;
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

	@Override
	public AccMovement autoTransferir(int accNo, double cantidad) throws InstanceNotFoundException {

		Transaction tx = null;
		Session session = null;
		AccMovement movement = null;

		try {
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			session = sessionFactory.openSession();

			Account account = session.get(Account.class, accNo);
			if (account == null) {
				throw new InstanceNotFoundException(Account.class.getName() + " origen id:" + accNo);
			}
			BigDecimal cantidadBD = new BigDecimal(cantidad);

			tx = session.beginTransaction();

			account.setAmount(account.getAmount().add(cantidadBD));

			movement = new AccMovement();
			movement.setAmount(cantidadBD);
			movement.setDatetime(LocalDateTime.now());

			// Relación bidireccional
			movement.setAccountOrigen(account);
			movement.setAccountDestino(account);

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

	public Account saveOrUpdate(Account d) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.saveOrUpdate(d);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en saveOrUpdate Account: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
		return d;
	}

	/**
	 * Modifica la implementación para que permita eliminar una cuenta y todos sus
	 * movimientos en una transacción. Para probarlo, puedes crear movimientos
	 * modificando previamente el importe de la cuenta a través de la interfaz
	 * gráfica.
	 */
	public boolean delete(int accId) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean exito = false;

		try {
			tx = session.beginTransaction();
			Account account = session.get(Account.class, accId);
			if (account != null) {
				for (AccMovement mov : account.getAccMovementsForAccountDestId()) {
					session.remove(mov);
				}
				for (AccMovement mov : account.getAccMovementsForAccountDestId()) {
					session.remove(mov);
				}
				for (Empleado emp : account.getEmployees()) {
					emp.getAccounts().remove(account);
				}
				session.remove(account);
			} else {
				throw new InstanceNotFoundException(Account.class.getName() + " id: " + accId);
			}
			tx.commit();
			exito = true;
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en create Dept: " + ex.getMessage());
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
	 * Crea la consulta HQL que devuelve todas las cuentas que puede tener un
	 * empleado. Utiliza parámetros en la consulta.
	 */
	@Override
	public List<Account> getAccountsByEmpno(int empno) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		List<Account> cuentas = session
				.createQuery("select a from Account a join a.employees e where e.empno = :empno order by a.accountno")
				.setParameter("empno", empno).list();
		session.close();

		return cuentas;
	}

	/**
	 * Devuelve la lista de titulares de una cuenta por su id. Si no se encuentra la
	 * cuenta, lanzará una InstanceNotFoundException
	 */
	@Override
	public List<Empleado> getTitularesByAccountId(int accId) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Account account = session.get(Account.class, accId);

		if (account == null) {
			throw new InstanceNotFoundException(Account.class.getName());
		}

		List<Empleado> titulares = new ArrayList<Empleado>(account.getEmployees());
		session.close();

		return titulares;
	}

	/**
	 * Cree una nueva cuenta asociada a un empleado. Asegúrate de crear la relación
	 * de forma bidireccional antes de guardar los cambios. Utiliza una transacción.
	 */
	@Override
	public Account addAccountToEmployee(int empno, Account acc) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			Empleado empleado = session.get(Empleado.class, empno);
			if (empleado != null) {
				tx = session.beginTransaction();

				empleado.getAccounts().add(acc);

				session.saveOrUpdate(acc); // opcional?
				session.save(empleado);
				tx.commit();
			}

		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return acc;

	}

}