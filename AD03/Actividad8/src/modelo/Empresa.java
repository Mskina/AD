package modelo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cif;
	private String nombre;
	private String direccion;
	private String telefono;
	private Set<Empleado> empleados;

	public Empresa() {
		super();
	}

	@Id
	@Column(name = "cif", unique = true, nullable = false, length = 9)
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "direccion", nullable = false, length = 100)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", nullable = false, length = 100)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	@Override
	public String toString() {
		return "Empresa [cif=" + cif + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
				+ ", empleados=" + empleados + "]";
	}

}
