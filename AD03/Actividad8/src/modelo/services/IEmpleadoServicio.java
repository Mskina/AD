package modelo.services;

import modelo.Empleado;

public interface IEmpleadoServicio {

	public boolean crearEmpleado(Empleado empleado);

	public boolean eliminarEmpleado(String dni);

}
