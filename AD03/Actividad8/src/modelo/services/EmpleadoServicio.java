package modelo.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Empleado;
import util.SessionFactoryUtil;

public class EmpleadoServicio implements IEmpleadoServicio {

	@Override
	public boolean crearEmpleado(Empleado empleado) {
		boolean creado = false;

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.saveOrUpdate(empleado); // Con la misma instrucción, se crea o actualiza
			tx.commit();
			creado = true;
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción durante la creación del empleado: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}

		return creado;
	}

	@Override
	public boolean eliminarEmpleado(String dni) {
		boolean exito = false;

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Intentamos obtener el empleado
			Empleado e = session.get(Empleado.class, dni);

			if (e != null) {
				// Si lo encontramos --> lo eliminamos
				session.remove(e);
				tx.commit();
				exito = true;
			}
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción durante la eliminación del empleado: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}

		return exito;

	}

}
