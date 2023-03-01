package modelo.servicio;

import exceptions.InstanceNotFoundException;
import modelo.Emp;

public interface IEmpleadoServicio {
	public Emp find(int id) throws InstanceNotFoundException;

}
