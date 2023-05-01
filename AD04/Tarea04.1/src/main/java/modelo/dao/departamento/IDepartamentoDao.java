/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.dao.departamento;

import modelo.Departamento;
import modelo.dao.IGenericDao;

/**
 * Crea la interfaz IDepartamentoDao.java para que extienda de IGenericDao<E>
 *
 * @author Iván Estévez Sabucedo
 */
public interface IDepartamentoDao extends IGenericDao<Departamento> {

    /**
     * Devolverá true o false en función de si ya existe un departamento con el
     * mismo deptno
     *
     * @param dept
     * @return
     */
    public boolean exists(Integer dept);
}
