package main;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.EmpleadoTemporal;
import util.SessionFactoryUtil;

public class Main {

	private static SessionFactory sessionFactory = null;
	private static Session session = null;
	private static Transaction tx = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		sessionFactory = SessionFactoryUtil.getSessionFactory();

		session = sessionFactory.openSession();

		tx = session.beginTransaction();

		EmpleadoTemporal et1 = new EmpleadoTemporal();
		et1.setDni("p");
		et1.setNombre("manuel");
		et1.setTelefono("988");
		et1.setFechaInicio(LocalDateTime.of(2023, 3, 1, 0, 0)); // Año, mes, día, hora, minuto
		et1.setFechaFin(LocalDateTime.of(2023, 3, 31, 23, 0));
		et1.setPagoDia(50);
		et1.setSuplemento(12);
		et1.setPorcentaRetencion(0.09f);		
		session.save(et1);

		//System.out.println(et1.calculoNomina());
		
		tx.commit();

		session.close();

	}

}
