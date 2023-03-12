package herencia.perClass;

import javax.persistence.Entity;

@Entity
public class Externo extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empresa;

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	@Override
	public String toString() {
		return "Externo { externoId=" + id + ", empresaContratante=" + empresa + " }";
	}

}