package main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.SessionFactoryUtil;

public class ConsultasAsociacionesHQL {

	public static void main(String[] args) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		{
			System.out.printf("\n \n");
			System.out.println(">> 1. Los nombres de los departamentos que no tengan empleados.");
			System.out.println(">>>>Los departamentos deben ser ordenados por nombre");

			List<String> listDatos = session.createQuery(
					"SELECT d.dname FROM Departamento d LEFT JOIN d.emps e WHERE e.ename = null ORDER BY d.dname")
					.list();
			// select d.dname FROM Departamento d where size(d.emps) =0 order by d.dname
			// select d.dname FROM Departamento d where d.emps is empty order by d.dname
			// SELECT d.dname FROM Departamento d LEFT JOIN d.emps e WHERE e is null ORDER BY d.dname

			for (String datos : listDatos) {
				System.out.println("Nombre del departamento: " + datos);
			}
		}

		{
			System.out.printf("\n \n");
			System.out.println(
					">> 2. Los nombres de los departamentos y de los empleados que tienen al menos 2 empleados.");
			System.out.println(">>>>El resultado debe ser ordenado por nombre de departamento");

			List<Object[]> listaDatos = session.createQuery(
					"SELECT d.dname, e.ename FROM Departamento d JOIN d.emps e WHERE size (d.emps) >= 2 ORDER BY d.dname")
					.list();

			for (Object[] fila : listaDatos) {
				System.out.println("Departamento: " + fila[0] + "; Empleado: " + fila[1]);
			}
		}

		{
			System.out.printf("\n \n");
			System.out.println(">> 3. Los identificadores de los empleados y el nº de cuentas por empleado");

			List<Object[]> listaDatos = session
					.createQuery("SELECT e.empno, count(a) FROM Emp e LEFT JOIN e.accounts a GROUP BY e.empno").list();
			// select e.empno, size(e.accounts) FROM Emp e

			for (Object[] fila : listaDatos) {
				System.out.println("ID empleado: " + fila[0] + "; Cuentas: " + fila[1]);
			}
		}

		{
			System.out.printf("\n \n");
			System.out.println(">> 4. Los identificadores de los empleados y el saldo de sus cuentas");
			List<Object[]> listaDatos = session
					.createQuery("SELECT e.empno, sum(a.amount) FROM Emp e LEFT JOIN e.accounts a GROUP BY e.empno")
					.list();

			for (Object[] fila : listaDatos) {
				System.out.println("ID empleado: " + fila[0] + "; Saldo total: " + fila[1]);
			}
		}

		{
			System.out.printf("\n \n");
			System.out.println(
					">> 5. El identificador de cada cuenta con el identificador del movimiento donde la cuenta es la cuenta origen");
			List<Object[]> listaDatos = session
					.createQuery("SELECT am.accountOrigen.accountno, am.accountMovId from AccMovement am").list();

			for (Object[] fila : listaDatos) {
				System.out.println("ID cuenta: " + fila[0] + "; ID movimiento: " + fila[1]);
			}

			System.out.println("");
		}

		{
			System.out.printf("\n \n");
			System.out.println(">> 6. El nº de movimientos por cada cuenta origen");
			List<Object[]> listaDatos = session
					.createQuery("SELECT a.accountno, size(a.accMovementsOrigen) from Account a").list();
			// select c.accountno, count(o) from Account c left join c.accMovementsOrigen o group by c.accountno

			for (Object[] fila : listaDatos) {
				System.out.println("ID cuenta: " + fila[0] + "; Movimientos: " + fila[1]);
			}

			System.out.println("");
		}

		{
			System.out.printf("\n \n");
			System.out.println(">> 7. El nombre de cada empleado con el de su jefe.");
			System.out.println(">>>> Ha de aparecer el nombre del empleado aunque no tenga jefe.");
			List<Object[]> listaDatos = session
					.createQuery("SELECT e.ename, e.emp.ename FROM Emp e LEFT JOIN e.emp jefe").list();
			// SELECT e.ename, e.emp.ename FROM Emp e
			// No válida porque omite los null -> No tienen jefe (empleador)

			for (Object[] fila : listaDatos) {
				System.out.println("Empleado: " + fila[0] + " - Jefe: " + fila[1]);
			}

			System.out.println("");
		}
		session.close();
		sessionFactory.close();
	}
}
