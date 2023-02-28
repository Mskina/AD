package modelo;
// Generated 31 ene 2023 11:04:06 by Hibernate Tools 5.6.14.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Profesor generated by hbm2java
 */
public class Profesor implements java.io.Serializable {

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", nombre=" + nombre + ", ape1=" + ape1 + ", ape2=" + ape2 + ", tipoFuncionario="
				+ tipoFuncionario + "]";
	}

	private Integer id;
	private String nombre;
	private String ape1;
	private String ape2;
	private String tipoFuncionario;
	private Set modulos = new HashSet(0);

	public Profesor() {
	}

	public Profesor(String nombre, String ape1, String ape2) {
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
	}

	public Profesor(String nombre, String ape1, String ape2, String tipoFuncionario, Set modulos) {
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
		this.tipoFuncionario = tipoFuncionario;
		this.modulos = modulos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApe1() {
		return this.ape1;
	}

	public void setApe1(String ape1) {
		this.ape1 = ape1;
	}

	public String getApe2() {
		return this.ape2;
	}

	public void setApe2(String ape2) {
		this.ape2 = ape2;
	}

	public String getTipoFuncionario() {
		return this.tipoFuncionario;
	}

	public void setTipoFuncionario(String tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}

	public Set getModulos() {
		return this.modulos;
	}

	public void setModulos(Set modulos) {
		this.modulos = modulos;
	}

}
