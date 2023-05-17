package es.teis.beans;

import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

public class VetoableBeanFuente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Esta propiedad permite notificar a otros oyentes de que este componente
	 * (this) es la fuente del evento de cambio. Se puede hacer aquí como propiedad
	 * o en el constructor del bean (sin el modificador final)
	 */
	private final VetoableChangeSupport vcs = new VetoableChangeSupport(this);

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		this.vcs.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		this.vcs.removeVetoableChangeListener(listener);
	}

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String newValue) {
		String oldValue = this.value;

		try {
			this.vcs.fireVetoableChange("value", oldValue, newValue);
			this.value = newValue;
		} catch (PropertyVetoException pve) {
			pve.getStackTrace();
		}
	}

	// [...]
}
