package es.teis.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class BeanReceptor implements Serializable, PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Valor anterior: " + evt.getOldValue());
		System.out.println("Valor actual: " + evt.getNewValue());

	}

}
