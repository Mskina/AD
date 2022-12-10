/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2.controller;

import es.teis.ud2.exceptions.InstanceNotFoundException;
import es.teis.ud2.model.Departamento;
import es.teis.ud2.model.dao.departamento.DepartamentoSQLServerDao;
import es.teis.ud2.model.dao.departamento.IDepartamentoDao;
import es.teis.ud2.services.departamento.DepartamentoService;
import es.teis.ud2.services.departamento.IDepartamentoService;
import java.util.ArrayList;

/**
 *
 * @author maria
 */
public class DepartamentoController {

    private IDepartamentoDao departamentoDao;
    private IDepartamentoService departamentoService;

    public DepartamentoController() {
        this.departamentoDao = new DepartamentoSQLServerDao();
        this.departamentoService = new DepartamentoService(departamentoDao);
    }

    public ArrayList<Departamento> findAll() {
        return this.departamentoService.findAll();
    }

    public String verDetalles(int id) {
        Departamento departamento = null;
        String resultado;

        try {
            departamento = departamentoService.findById(id);

            resultado = "Departamento: " + departamento;

        } catch (InstanceNotFoundException ex) {
            //Para el sistema de gestión de errores
            System.err.println("Ha ocurrido una exception: " + ex.getMessage());
            //Para informar al usuario
            resultado = "No se ha encontrado el departamento";
        }
        return resultado;
    }

    public ArrayList<String> getDepartamentNamesByLoc(String ubicacion) {
        return this.departamentoService.getDepartamentNamesByLoc(ubicacion);
    }

}
