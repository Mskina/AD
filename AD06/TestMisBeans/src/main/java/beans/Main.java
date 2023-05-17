/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beans;

import es.teis.beans.BeanFuente;
import es.teis.beans.BeanReceptor;
import es.teis.beans.VetoableBeanFuente;
import es.teis.beans.VetoableBeanReceptor;

/**
 *
 * @author mfernandez
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws ClassNotFoundException {
//       AlumnoBean ab = new AlumnoBean();
//       ab.addAlumno();
		testPropertiesVeto();

	}

	private static void testPropertiesLigadas() {
		BeanFuente bf = new BeanFuente();
		BeanReceptor br = new BeanReceptor();

		bf.addPropertyChangeListener(br);

		bf.setValue("new value");

	}

	private static void testPropertiesVeto() {
		VetoableBeanFuente vbf = new VetoableBeanFuente();
		VetoableBeanReceptor vbr = new VetoableBeanReceptor();
		
		VetoableBeanReceptor vbr2 = new VetoableBeanReceptor();

		vbf.addVetoableChangeListener(vbr);
		vbf.addVetoableChangeListener(vbr2);

		vbf.setValue("new value");

		vbf.setValue("ABORT");

		vbf.setValue("second value");

	}

}
