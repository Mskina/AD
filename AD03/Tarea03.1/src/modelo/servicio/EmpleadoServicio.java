package modelo.servicio;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import exceptions.InstanceNotFoundException;
import modelo.Departamento;
import modelo.Empleado;
import util.SessionFactoryUtil;

public class EmpleadoServicio implements IEmpleadoServicio {

	@Override
	public Empleado find(int id) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Empleado e = session.get(Empleado.class, id);
		session.close();
		if (e == null) {
			throw new InstanceNotFoundException(Departamento.class.getName() + " id: " + id);
		}
		return e;
	}

}
