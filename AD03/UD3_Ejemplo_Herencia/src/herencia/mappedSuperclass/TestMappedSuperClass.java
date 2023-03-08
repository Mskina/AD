package herencia.mappedSuperclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class TestMappedSuperClass {

	
	
	private static SessionFactory sessionFactory = null;
	
	
	private static Session session = null;

	
	private static Transaction tx = null;
	
	
    public static void main(String[] args) {
      sessionFactory = herencia.mappedSuperclass.SessionFactoryUtil.getSessionFactory();
    	
session = sessionFactory.openSession();
		
		tx = session.beginTransaction();
    	addEmployees();
    	
	tx.commit();
		
		session.close();
    }

	
	public static void addEmployees() {
		
		System.out.println("\n\n*** addEmployees ***\n");
		
		try {
			//Employee employee = new Employee("yo mismo");
			
//			try {
//				session.persist(employee);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	
		//	System.out.println("La clave del nuevo objeto es: " + employee.getId());
			
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
			externo.setName("Yo soy Externo");
			externo.setEmpresa("IBM");
			
			session.save(externo);
			System.out.println("La clave del nuevo objeto es: " + externo.getId());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
