/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.teis.ud2.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class AccountMovement {
    
    private int accountMovId;
    private Account accountOriginId;
    private Account accountDestId;
    private BigDecimal amount;
    private Timestamp datetime;

    public AccountMovement() {
    }

    public AccountMovement(int accountMovId, Account accountOriginId, Account accountDestId, BigDecimal amount, Timestamp datetime) {
        this.accountMovId = accountMovId;
        this.accountOriginId = accountOriginId;
        this.accountDestId = accountDestId;
        this.amount = amount;
        this.datetime = datetime;
    }

    public int getAccountMovId() {
        return accountMovId;
    }

    public Account getAccountOriginId() {
        return accountOriginId;
    }

    public Account getAccountDestId() {
        return accountDestId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setAccountMovId(int accountMovId) {
        this.accountMovId = accountMovId;
    }

    public void setAccountOriginId(Account accountOriginId) {
        this.accountOriginId = accountOriginId;
    }

    public void setAccountDestId(Account accountDestId) {
        this.accountDestId = accountDestId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "AccountMovement{" + "accountMovId=" + accountMovId + ", accountOriginId=" + accountOriginId.getAccountId() + ", accountDestId=" + accountDestId.getAccountId() + ", amount=" + amount + ", datetime=" + datetime + '}';
    }
    
    
}
