package es.teis.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

public class VetoableBeanReceptor implements Serializable, VetoableChangeListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

		// Se lanza Excepción si no se aprueba el cambio
		if ((evt.getOldValue() != null) && evt.getNewValue().equals("ABORT")) {
			/*
			 * En función de unas condiciones, se aprueba o no el cambio. En este caso, es
			 * si el nuevo valor es igual a la cadena "ABORT"
			 */
			System.out.println("Abortando notificación cambio...");
			throw new PropertyVetoException("Mensaje", evt);
		} else {
			System.out.println("Antiguo valor: " + evt.getOldValue() + "=> Nuevo valor: " + evt.getNewValue());
		}

	}

}
