/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.servicio.departamento;

import java.util.List;
import modelo.Departamento;
import modelo.dao.departamento.DepartamentoNeoDatisDao;
import modelo.dao.departamento.IDepartamentoDao;
import modelo.exceptions.DuplicateInstanceException;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class ServicioDepartamento implements IServicioDepartamento {

    private IDepartamentoDao departamentoDao;

    public ServicioDepartamento() {
        departamentoDao = new DepartamentoNeoDatisDao();
    }

    @Override
    public long create(Departamento dept) throws DuplicateInstanceException {

        if (exists(dept.getDeptno())) {
            throw new DuplicateInstanceException(
                    "Ya existe un departamento con ese id. No se ha podido crear.",
                    dept.getDeptno(),
                    dept.getClass().toString());
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
    public boolean exists(Integer deptno) {
        return departamentoDao.exists(deptno);
    }

}
