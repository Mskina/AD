/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.ud2.controller;

import es.teis.ud2.model.Empleado;
import es.teis.ud2.services.empleado.IEmpleadoServicio;

/**
 *
 * @author maria
 */
public class EmpleadoController {
    
    private IEmpleadoServicio empleadoServicio;

    public EmpleadoController(IEmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }
    
    
    public Empleado create(Empleado empleado){
        return this.empleadoServicio.create(empleado);
    }
    
    
}
