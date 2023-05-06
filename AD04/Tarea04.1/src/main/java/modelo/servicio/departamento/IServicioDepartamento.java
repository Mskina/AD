package modelo.servicio.departamento;

import java.util.List;
import modelo.Departamento;
import modelo.exceptions.DuplicateInstanceException;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public interface IServicioDepartamento {

    /**
     * El método create deberá comprobar antes de crear si ya existe un
     * departamento con el mismo deptno. Si existe, lanza la excepción
     * DuplicateInstanceException. En caso contrario intenta crear el
     * departamento.
     *
     * @param dept
     * @return oid si éxito, excepción en caso contrario
     * @throws DuplicateInstanceException
     */
    public long create(Departamento dept) throws DuplicateInstanceException;

    public boolean delete(Departamento dept);
    
    public boolean update(Departamento dept);

    public List<Departamento> findAll();

    public boolean exists(Integer deptno);

}
