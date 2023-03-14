package modelo.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Empresa;
import util.SessionFactoryUtil;

public class EmpresaServicio implements IEmpresaServicio {

	@Override
	public boolean crearEmpresa(Empresa empresa) {
		boolean creada = false;

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.saveOrUpdate(empresa); // Con la misma instrucción, se crea o actualiza
			tx.commit();
			creada = true;
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción durante la creación de la empresa: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}

		return creada;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> listarEmpresas() {
		List<Empresa> lista = null;

		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();		

		try {
			lista = session.createQuery("SELECT e FROM Empresa e ORDER BY e.nombre").list();
		} catch (Exception ex) {
			System.out.println("Eror al listar empresas" + ex.getMessage());
		} finally {
			session.close();
		}

		return lista;
	}

}
