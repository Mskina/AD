package main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import modelo.Departamento;
import modelo.Emp;
import util.SessionFactoryUtil;

public class ConsultasHQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		System.out.println("--- Consultas con HQL y createQuery que obtengan: ");

		{
			System.out.println("\\n>> 1: El nombre, el puesto de trabajo y el salario de todos los empleados");

			List<Object[]> empleados = session.createQuery(" select e.ename, e.job, e.sal FROM Emp e ").list();

			for (Object[] emp : empleados) {
				System.out.println("Nombre: " + emp[0] + "; Puesto: " + emp[1] + "; Salario: " + emp[2]);
			}
		}

		{
			System.out.println("\n>> 2. La media del salario de todos los empleados");

			Double media = (Double) session.createQuery(" select AVG(e.sal) FROM Emp e ").uniqueResult();

			System.out.println("La media es: " + media.intValue() + " euros.");
		}

		{
			System.out.println("\n>> 3. Los empleados que tengan un salario mayor que la media de todos los empleados");

			List<Emp> empleados = session
					.createQuery(" select e1 FROM Emp e1 WHERE e1.sal > (select AVG(e2.sal) FROM Emp e2)").list();

			for (Emp emp : empleados) {
				System.out.println("Empleado: " + emp);
			}
		}
		
		{
			System.out.println("\n>> 4. El departamento con un determinado id con parámetros nominales y posicionales");
			
			int numDepartamento = 40;
			String nombreDepartamento = "A%";
			List<Departamento> departamentos = session
					.createQuery(" select d FROM Departamento d WHERE d.deptno = :id OR d.dname LIKE ?0").setParameter("id", numDepartamento).setParameter(0, nombreDepartamento).list();

			for (Departamento departamento : departamentos) {
				System.out.println("Departamento: " + departamento);
			}
		}
		
		{
			System.out.println("\n>> 5. Los nombres de los departamentos cuyos ids sean el 10 o el 20. Usa una lista parametrizada");
			
			List<Integer> idsDepartamentos = new ArrayList<Integer>();
			idsDepartamentos.add(10);
			idsDepartamentos.add(20);

			List<String> departamentos = session
					.createQuery(" select d.dname FROM Departamento d WHERE d.deptno in :ids").setParameterList("ids", idsDepartamentos).list();

			for (String departamento : departamentos) {
				System.out.println("Departamento: " + departamento);
			}
		}
		
		{
			System.out.println("\n>> 6. El número de empleados por departamento");
			
			List<Object[]> empleados = session
					.createQuery(" select e.dept.deptno, count(e) FROM Emp e GROUP BY e.dept.deptno").list();

			for (Object[] e : empleados) {
				System.out.println("El departamento: " + e[0] + " tiene " + e[1] + " empleados.");
			}
		}
		
		System.out.println("\n>> Fin Actividad 3.2 ");
		
		session.close();
		sessionFactory.close();

	}

}
