package modelo;
// Generated 20:00:38, 27 de feb. de 2023 by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * ContactInfo generated by hbm2java
 */
@Entity
@Table(name = "contactInfo", 
uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class ContactInfo implements java.io.Serializable {

	private int profesorId;
	private Profesor profesor;
	private String email;
	private String tlfMovil;

	public ContactInfo() {
	}

	public ContactInfo(Profesor profesor, String email) {
		this.profesor = profesor;
		this.email = email;
	}

	public ContactInfo(Profesor profesor, String email, String tlfMovil) {
		this.profesor = profesor;
		this.email = email;
		this.tlfMovil = tlfMovil;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "profesor"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "profesorId", unique = true, nullable = false)
	public int getProfesorId() {
		return this.profesorId;
	}

	public void setProfesorId(int profesorId) {
		this.profesorId = profesorId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "tlf_movil", length = 15)
	public String getTlfMovil() {
		return this.tlfMovil;
	}

	public void setTlfMovil(String tlfMovil) {
		this.tlfMovil = tlfMovil;
	}

}
