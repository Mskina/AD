/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.empleado;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.OID;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import modelo.Empleado;
import modelo.dao.AbstractGenericDao;
import modelo.exceptions.InstanceNotFoundException;
import util.ConnectionFactory;
import util.Utils;

/**
 *
 * @author mfernandez
 */
public class EmpleadoNeoDatisDao extends AbstractGenericDao<Empleado> implements IEmpleadoDao {

	private ODB dataSource;

	public EmpleadoNeoDatisDao() {
		this.dataSource = ConnectionFactory.getConnection();
	}

	@Override
	public long create(Empleado entity) {
		OID oid = null;
		long oidlong = -1;
		try {
			oid = this.dataSource.store(entity);
			this.dataSource.commit();

		} catch (Exception ex) {

			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			this.dataSource.rollback();
			oid = null;
		}
		if (oid != null) {
			oidlong = oid.getObjectId();
		}
		return oidlong;
	}

	@Override
	public Empleado read(long id) throws InstanceNotFoundException {
		Empleado empleado = null;
		try {
			OID oid = OIDFactory.buildObjectOID(id);
			empleado = (Empleado) this.dataSource.getObjectFromId(oid);
		} catch (ODBRuntimeException ex) {

			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
//Suponemos que no lo encuentra
			throw new InstanceNotFoundException(id, getEntityClass());
		} catch (Exception ex) {

			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

		}
		return empleado;
	}

	@Override
	public boolean update(Empleado entity) {
		boolean exito = false;
		try {
			this.dataSource.store(entity);
			this.dataSource.commit();
			exito = true;
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			this.dataSource.rollback();

		}
		return exito; // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public float findAvgSalary() {
		BigDecimal media = BigDecimal.ZERO;
		ValuesCriteriaQuery valuesCriteriaQuery = new ValuesCriteriaQuery(Empleado.class);
		IValuesQuery ivc = valuesCriteriaQuery.avg("sal");
		Values values = this.dataSource.getValues(ivc);
		while (values.hasNext()) {
			ObjectValues objectValues = (ObjectValues) values.next();
			media = (BigDecimal) objectValues.getByIndex(0);

		}
		return media.floatValue();
	}

	/**
	 * CRUD - Delete
	 * 
	 * Genérico. Debemos leer un objeto guardado y, a continuación, lo eliminamos
	 * con la ayuda de métodos.
	 * 
	 * Método que elimina un Empleado dentro de una transacción. Devuelve true si se
	 * ha eliminado y false en caso contrario
	 */
	@Override
	public boolean delete(Empleado entity) {
		boolean exito = false;
		try {
			this.dataSource.delete(entity);
			this.dataSource.commit(); // En los apuntes no está, pero sí en los otros métodos
			exito = true;
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción en delete: " + ex.getMessage());
			this.dataSource.rollback();
		}
		return exito;
	}

	/**
	 * Método que devuelve una lista de todos los empleados
	 */
	@Override
	public List<Empleado> findAll() {
		// Creamos una lista
		List<Empleado> lista = null;

		try {
			// Intentamos obtener todos los objetos Empleado y los listamos
			Objects<Empleado> empleados = this.dataSource.getObjects(Empleado.class);
			lista = Utils.toList(empleados);
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción en findAll: " + ex.getMessage());
		}

		return lista;
	}

	/**
	 * Método que devuelve todos los empleados que tengan el mismo empleo
	 * 
	 * Inicialmente el parámetro era "job". En la solución está "puesto"
	 */
	@Override
	public List<Empleado> findByJob(String puesto) {
		// Creamos una lista
		List<Empleado> lista = null;

		try {
			// Consulta que busca un match entre "job" de Empleado y puesto del parámetro
			IQuery query = new CriteriaQuery(Empleado.class, Where.equal("job", puesto));

			// Intentamos obtener todos los Empleado que cumplan la consulta
			Objects<Empleado> empleados = this.dataSource.getObjects(query);
			lista = Utils.toList(empleados);
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción en findByJob: " + ex.getMessage());
		}

		return lista;
	}

	/**
	 * Devuelve true si ya existe un empleado con ese empno
	 */
	@Override
	public boolean exists(Integer empno) {

		// Consulta en la que field devuelve la columna de empno
		// Con Where.equal filtramos los valores "empno" que coincidan con el parámetro
		// recibido
		IValuesQuery query = new ValuesCriteriaQuery(Empleado.class, Where.equal("empno", empno)).field("empno");

		// Realizamos la consulta
		Values values = this.dataSource.getValues(query);

		// Se niega para que cumpla true = existe (si está Empty --> NO existe)
		return !values.isEmpty();
	}

	/**
	 * Devuelve todos los empleados contratados entre las fechas recibidas (incluidas)
	 */
	@Override
	public List<Empleado> findEmployeesByHireDate(Date from, Date to) {
		// Creamos una lista
		List<Empleado> lista = null;

		try {			
			// Criterio de búsqueda con And (se deben cumplir ambos criterios) comaprando con hiredate
			// ge (greater-equal, mayor o igual) que la fecha from de parámetro
			// le (lesser-equal, menor o igual) que la fecha to de parámetro
			ICriterion criterio = new And().add(Where.ge("hiredate", from)).add(Where.le("hiredate", to));

			// Creo la consulta y le paso el criterio de búsqueda
		    IQuery query = new CriteriaQuery(Empleado.class, criterio);

			// Intentamos obtener todos los Empleado que cumplan la condición
			Objects<Empleado> empleados = this.dataSource.getObjects(query);
			lista = Utils.toList(empleados);
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción en findEmployeesByHireDate: " + ex.getMessage());
		}

		return lista;
	}

}
