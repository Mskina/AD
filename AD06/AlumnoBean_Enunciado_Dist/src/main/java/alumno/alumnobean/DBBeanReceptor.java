package alumno.alumnobean;

import java.io.Serializable;


public class DBBeanReceptor implements Serializable, BDModificadaListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void capturarBDModificada(BDModificadaEvent ev) {
		System.out.println("Listener: Se ha modificado la BD");
		
	}
	
	

}
