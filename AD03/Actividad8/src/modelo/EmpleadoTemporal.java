package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "dni")
public class EmpleadoTemporal extends Empleado {

	private static final long serialVersionUID = 1L;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private float pagoDia;
	private float suplemento;

	public EmpleadoTemporal() {
		super();
	}

	@Column(name = "fechaInicio", nullable = false)
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name = "fechaFin", nullable = false)
	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "pagoDia", nullable = false)
	public float getPagoDia() {
		return pagoDia;
	}

	public void setPagoDia(float pagoDia) {
		this.pagoDia = pagoDia;
	}

	@Column(name = "suplemento", nullable = false)
	public float getSuplemento() {
		return suplemento;
	}

	public void setSuplemento(float suplemento) {
		this.suplemento = suplemento;
	}

	/**
	 * Se refedine calculoNomina()
	 */
	@Override
	public float calculoNomina() {
		long diasTranscurridos = Duration.between(fechaInicio, fechaFin).toDays();
		return (pagoDia * diasTranscurridos) - ((pagoDia * diasTranscurridos) * porcentaRetencion) + suplemento;
	}

	@Override
	public String toString() {
		return "EmpleadoTemporal [fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", pagoDia=" + pagoDia
				+ ", suplemento=" + suplemento + ", dni=" + dni + ", nombre=" + nombre + ", teléfono=" + telefono
				+ ", porcentaRetencion=" + porcentaRetencion + "]";
	}

}
