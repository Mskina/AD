package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "dni")
public class EmpleadoFijo extends Empleado {

	private static final long serialVersionUID = 1L;
	private int salarioBase;
	private int trienios;

	// En la tabla la clave primaria y foránea es el dni del empleado

	public EmpleadoFijo() {
		super();
	}

	@Column(name = "salarioBase", nullable = false)
	public int getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(int salarioBase) {
		this.salarioBase = salarioBase;
	}

	@Column(name = "trienios", nullable = false)
	public int getTrienios() {
		return trienios;
	}

	public void setTrienios(int trienios) {
		this.trienios = trienios;
	}

	/**
	 * Se redefine por polimorfismo calculoNomina()
	 * 
	 */
	@Override
	public float calculoNomina() {
		return (salarioBase + trienios) - (salarioBase + trienios) * porcentaRetencion;
	}

	@Override
	public String toString() {
		return "EmpleadoFijo [salarioBase=" + salarioBase + ", trienios=" + trienios + ", dni=" + dni + ", nombre="
				+ nombre + ", teléfono=" + telefono + ", porcentaRetencion=" + porcentaRetencion + "]";
	}

}
