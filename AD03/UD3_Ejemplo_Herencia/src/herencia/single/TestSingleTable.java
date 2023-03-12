package herencia.single;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TestSingleTable {

	private static SessionFactory sessionFactory = null;

	private static Session session = null;

	private static Transaction tx = null;

	public static void main(String[] args) {
		sessionFactory = herencia.single.SessionFactoryUtil.getSessionFactory();

		session = sessionFactory.openSession();

		tx = session.beginTransaction();
		addEmployees();
		findAll();

		tx.commit();

		session.close();
	}
	
	public static void findAll() {
		List<Employee> empleados = session.createQuery("select e from Employee e order by e.id").list();
		for (Employee e: empleados) {
			System.out.println(e);
		}
	}
	

	public static void addEmployees() {

		System.out.println("\n\n*** addEmployee ***\n");

		try {
			Employee employee = new Employee("yo mismo");

			try {
				session.save(employee);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("La clave del nuevo objeto es: " + employee.getId());

			Technician technician = new Technician();
			technician.setName("Yo soy el tecnico!!!");
			technician.setExperienceYears(24);
			session.save(technician);
			System.out.println("La clave del nuevo objeto es: " + technician.getId());

			Developer developer = new Developer();
			developer.setName("Yo soy el desarrollador!!!");
			developer.setExperienceYears(14);
			developer.setExpertLanguajes("Java");
			session.save(developer);
			System.out.println("La clave del nuevo objeto es: " + developer.getId());
			
			Externo externo = new Externo();
			externo.setName("Yo soy externo");
			externo.setEmpresa("Yo soy el empleador");
			session.save(externo);
			System.out.println("La clave del nuevo objeto es: " + externo.getId());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
