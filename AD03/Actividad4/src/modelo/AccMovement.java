package modelo;
// Generated 21:11:36, 10 de xan. de 2023 by Hibernate Tools 5.6.14.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * AccMovement generated by hbm2java
 */
public class AccMovement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097818257356487403L;
	private Integer accountMovId;
	private Account accountOrigen;
	private Account accountDestino;
	private BigDecimal amount;
	private Date datetime;

	public AccMovement() {
	}

	public AccMovement(Account accountOrigen, Account accountDestino, BigDecimal amount, Date datetime) {
		this.accountOrigen = accountOrigen;
		this.accountDestino = accountDestino;
		
		this.amount = amount;
		this.datetime = datetime;
	}

	public Integer getAccountMovId() {
		return this.accountMovId;
	}

	public void setAccountMovId(Integer accountMovId) {
		this.accountMovId = accountMovId;
	}




	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Account getAccountOrigen() {
		return accountOrigen;
	}

	public void setAccountOrigen(Account accountOrigen) {
		this.accountOrigen = accountOrigen;
	}

	public Account getAccountDestino() {
		return accountDestino;
	}

	public void setAccountDestino(Account accountDestino) {
		this.accountDestino = accountDestino;
	}


	

}