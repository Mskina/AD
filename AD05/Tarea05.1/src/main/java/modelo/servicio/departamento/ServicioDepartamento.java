package modelo.servicio.departamento;

import java.util.List;

import modelo.Departamento;
import modelo.dao.departamento.DepartamentoEXistDao;
import modelo.dao.departamento.IDepartamentoDao;
import modelo.exceptions.DuplicateInstanceException;
import modelo.exceptions.InstanceNotFoundException;

public class ServicioDepartamento implements IServicioDepartamento {

	private IDepartamentoDao departamentoDao;

	public ServicioDepartamento() {
		departamentoDao = new DepartamentoEXistDao();
	}

	@Override
	public boolean create(Departamento dept) throws DuplicateInstanceException {

		boolean encontrado = false;

		try {
			read(dept.getDeptno());
			encontrado = true;
		} catch (InstanceNotFoundException infex) {
		}

		if (encontrado) {
			throw new DuplicateInstanceException("El departamento ya existe", dept.getDeptno(),
					Departamento.class.getName());
		}

		return departamentoDao.create(dept);

	}

	@Override
	public boolean delete(Departamento dept) {
		return departamentoDao.delete(dept);
	}

	@Override
	public boolean update(Departamento dept) {
		return departamentoDao.update(dept);
	}

	@Override
	public List<Departamento> findAll() {
		return departamentoDao.findAll();
	}

	@Override
	public Departamento read(long deptno) throws InstanceNotFoundException {
		return departamentoDao.read(deptno);
	}

}
