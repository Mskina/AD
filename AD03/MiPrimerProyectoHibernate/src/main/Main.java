package main;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Departamento;
import modelo.Emp;
import util.SessionFactoryUtil;

public class Main {

	public static void main(String[] args) {

		// createDepartamento();
		// findDepartamento(41);
		// deleteDepartamento(41);
		//updateDepartamento(42);
		createEmpleado(42);
//		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;

	}

	private static void createDepartamento() {
		Departamento dept = new Departamento();
		dept.setDname("RRHH");
		dept.setLoc("Vigo");

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;

		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();

			int deptId = (int) session.save(dept);
			tx.commit();

			System.out.println("Dept id: " + dept.getDeptno());

		} catch (Exception ex) {
			System.err.println(">> Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}

		}

	}

	private static void findDepartamento(int id) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;

		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();

			Departamento dept = session.get(Departamento.class, id);

			if (dept == null) {
				System.out.println("No se ha encontrado el dept con id: " + id);
			} else {
				tx.commit();

				System.out.println("Dept name: " + dept.getDname() + " Dept loc: " + dept.getLoc());
				Set<Emp> emps = dept.getEmps();
				for (Emp emp : emps) {
					System.out.println("Emp name: " + emp.getEname() + " Puesto: " + emp.getJob());
				}

			}

		} catch (Exception ex) {
			System.err.println(">> Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public static void deleteDepartamento(int id) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;

		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();

			Departamento dept = session.get(Departamento.class, id);

			if (dept == null) {
				System.out.println("No se ha encontrado el dept con id: " + id);
			} else {
				session.delete(dept);
				tx.commit();
				System.out.println("Eliminado el departamento: " + id);
			}

		} catch (Exception ex) {
			System.err.println(">> Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public static void updateDepartamento(int id) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;

		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();

			Departamento dept = session.get(Departamento.class, id);

			if (dept == null) {
				System.out.println("No se ha encontrado el dept con id: " + id);
			} else {
				dept.setDname("Recursos Humanos");
				dept.setLoc("A Coruña");

				session.saveOrUpdate(dept);
				// session.update(dept);
				tx.commit();
				System.out.println("Actualizado el departamento: " + id);
			}

		} catch (Exception ex) {
			System.err.println(">> Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	public static void createEmpleado(int id) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;

		try (Session session = sessionFactory.openSession();) {
			tx = session.beginTransaction();

			Departamento dept = session.get(Departamento.class, id);

			if (dept == null) {
				System.out.println("No se ha encontrado el dept con id: " + id);
			} else {
				Emp empleado = new Emp();
				empleado.setEname("Juan");
				empleado.setJob("Recruiter");
				empleado.setSal(new BigDecimal(30000));
				empleado.setComm(new BigDecimal(0.5f));
				empleado.setDept(dept);
				
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date date = simpleDateFormat.parse("2023-01-01");
				empleado.setHiredate(date);
				
				session.save(empleado);

				tx.commit();
				System.out.println("Creado el empleado");
			}

		} catch (Exception ex) {
			System.err.println(">> Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		}
	}

}
