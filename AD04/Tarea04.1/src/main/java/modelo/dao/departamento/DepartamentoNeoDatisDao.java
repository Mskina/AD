package modelo.dao.departamento;

import java.util.List;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;
import modelo.Departamento;
import modelo.dao.AbstractGenericDao;
import modelo.exceptions.InstanceNotFoundException;
import util.ConnectionFactory;
import util.Utils;

/**
 * Clase DepartamentoNeoDatisDao.java: Implementa con NeoDatis todas las
 * operaciones CRUD, findAll y el método public boolean exists(Integer dept)
 *
 * @author mfernandez
 */
public class DepartamentoNeoDatisDao extends AbstractGenericDao<Departamento> implements IDepartamentoDao {

    private ODB dataSource;

    public DepartamentoNeoDatisDao() {
        this.dataSource = ConnectionFactory.getConnection();
    }

    @Override
    public long create(Departamento entity) {
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
    public Departamento read(long id) throws InstanceNotFoundException {
        Departamento departamento = null;
        try {
            OID oid = OIDFactory.buildObjectOID(id);
            departamento = (Departamento) this.dataSource.getObjectFromId(oid);
        } catch (ODBRuntimeException ex) {

            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
            //Suponemos que no lo encuentra
            throw new InstanceNotFoundException(id, getEntityClass());
        } catch (Exception ex) {

            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

        }
        return departamento;
    }

    @Override
    public boolean update(Departamento entity) {
        boolean exito = false;

        try {
            this.dataSource.store(entity);
            this.dataSource.commit();
            exito = true;
        } catch (Exception ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
            this.dataSource.rollback();
        }
        return exito;
    }

    /**
     * CRUD - Delete
     *
     * Genérico. Debemos leer un objeto guardado y, a continuación, lo
     * eliminamos con la ayuda de métodos.
     *
     * Método que elimina un Departamento dentro de una transacción. Devuelve
     * true si se ha eliminado y false en caso contrario
     */
    @Override
    public boolean delete(Departamento entity) {
        boolean exito = false;
        try {
            this.dataSource.delete(entity);
            this.dataSource.commit(); // En los apuntes no estaba, pero sí en los otros métodos
            exito = true;
        } catch (Exception ex) {
            System.err.println("Ha ocurrido una excepción en delete: " + ex.getMessage());
            this.dataSource.rollback();
        }
        return exito;
    }

    /**
     * Método que devuelve una lista de todos los Departamentos
     */
    @Override
    public List<Departamento> findAll() {
        // Creamos una lista
        List<Departamento> lista = null;

        try {
            // Intentamos obtener todos los objetos Departamento y los listamos
            Objects<Departamento> departamento = this.dataSource.getObjects(Departamento.class);
            lista = Utils.toList(departamento);
        } catch (Exception ex) {
            System.err.println("Ha ocurrido una excepción en findAll: " + ex.getMessage());
        }

        return lista;
    }

    /**
     * Devolverá true o false en función de si ya existe un departamento con el
     * mismo deptno
     *
     * @param deptno
     * @return
     */
    @Override
    public boolean exists(Integer deptno) {
        // Consulta en la que field devuelve la columna de deptno
        // Con Where.equal filtramos los valores "deptno" que coincidan
        // con el parámetro recibido
        IValuesQuery query = new ValuesCriteriaQuery(Departamento.class, Where.equal("deptno", deptno)).field("deptno");

        // Realizamos la consulta
        Values values = this.dataSource.getValues(query);

        // Se niega para que cumpla true = existe (si está Empty --> NO existe)
        return !values.isEmpty();
    }
}
