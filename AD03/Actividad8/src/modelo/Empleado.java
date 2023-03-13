package modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Empleado")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String dni; // Clave primaria.
	protected String nombre;
	protected String telefono;
	protected float porcentaRetencion;
	protected Empresa empresa;

	// En la tabla clave foránea es el cif de la empresa.

	public Empleado() {
	}

	@Id
	@Column(name = "dni", unique = true, nullable = false, length = 9)
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "telefono", nullable = false, length = 100)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "porcentaRetencion", nullable = false)
	public float getPorcentaRetencion() {
		return porcentaRetencion;
	}

	public void setPorcentaRetencion(float porcentaRetencion) {
		this.porcentaRetencion = porcentaRetencion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cif", nullable = true) /*CAMBIAR*/
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	// Tendrá un método abstracto calculoNomina()
	public abstract float calculoNomina();

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + ", teléfono=" + telefono + ", porcentaRetencion="
				+ porcentaRetencion + "]";
	}

}
