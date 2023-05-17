/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alumno.alumnobean;

import java.sql.Date;
import java.time.LocalDate;


/**
 *
 * @author mfernandez
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws ClassNotFoundException {
       AlumnoDaoBean alumnoDaoBean = new AlumnoDaoBean();
       alumnoDaoBean.setApellidos("Estévez Sabucedo");
       alumnoDaoBean.setDNI("44477431P");
       
       LocalDate localDate = LocalDate.of(1987, 5, 22);
       Date date = Date.valueOf(localDate);
       alumnoDaoBean.setFechaNac(date);
       alumnoDaoBean.setDireccion("Ourense");
       
       alumnoDaoBean.setNombre("Iván");
       
		

       DBBeanReceptor rec = new DBBeanReceptor();
       alumnoDaoBean.addBDModificadaListener(rec);
       alumnoDaoBean.insert();
       
       
	}

}
