//package modelo.ud3;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.Table;
//
///**
// * Añade las anotaciones necesarias para que se implemente con una tabla por
// * cada entidad con sus atributos propios, de forma que la columna account_id
// * sea la clave primaria de la tabla llamada TERM_DEPOSIT_ACCOUNT y a su vez,
// * sea clave foránea hacia la clave primaria de ACCOUNT
// * 
// * @author Iván Estévez
// *
// */
//@Entity
//@Table(name="TERM_DEPOSIT_ACCOUNT")  
//@PrimaryKeyJoinColumn(name = "account_id")
//public class TermDepositAccount extends Account {
//	
//	private float interes;
//	private int plazo_meses;
//	
//	@Column(name="INTERES")
//	public float getInteres() {
//		return interes;
//	}
//	
//	public void setInteres(float interes) {
//		this.interes = interes;
//	}
//	
//	@Column(name="PLAZOMESES")
//	public int getPlazo_meses() {
//		return plazo_meses;
//	}
//	
//	public void setPlazo_meses(int plazo_meses) {
//		this.plazo_meses = plazo_meses;
//	}
//
//	
//	
//}