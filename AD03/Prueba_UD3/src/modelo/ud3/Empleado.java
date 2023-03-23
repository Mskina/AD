package modelo.ud3;
// Generated 19:13:31, 23 de mar. de 2023 by Hibernate Tools 4.3.6.Final

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "EMPLOYEE")
public class Empleado implements java.io.Serializable {

	private int empno;
	private Departamento department;
	private Empleado employee;
	private String ename;
	private String job;
	private LocalDate hiredate;
	private BigDecimal sal;
	private BigDecimal comm;
	private Set<Empleado> employees = new HashSet(0);
	private Set<Account> accounts = new HashSet(0);

	public Empleado() {
	}

	public Empleado(int empno) {
		this.empno = empno;
	}

	public Empleado(int empno, Departamento department, Empleado employee, String ename, String job, LocalDate hiredate,
			BigDecimal sal, BigDecimal comm, Set<Empleado> employees, Set<Account> accounts) {
		this.empno = empno;
		this.department = department;
		this.employee = employee;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.employees = employees;
		this.accounts = accounts;
	}

	@Id

	@Column(name = "EMPLOYEENO", unique = true, nullable = false)
	public int getEmpno() {
		return this.empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENTNO")
	public Departamento getDepartment() {
		return this.department;
	}

	public void setDepartment(Departamento department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MGR")
	public Empleado getEmployee() {
		return this.employee;
	}

	public void setEmployee(Empleado employee) {
		this.employee = employee;
	}

	@Column(name = "ENAME", length = 20)
	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "JOB", length = 20)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "HIREDATE", length = 10)
	public LocalDate getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}

	@Column(name = "SAL", scale = 4)
	public BigDecimal getSal() {
		return this.sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}

	@Column(name = "COMM", scale = 4)
	public BigDecimal getComm() {
		return this.comm;
	}

	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<Empleado> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Empleado> employees) {
		this.employees = employees;
	}

	// TODO: 4f ???
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EMPLOYEE_ACCOUNT", joinColumns = {
			@JoinColumn(name = "EMPLOYEENO", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ACCOUNTNO", nullable = false, updatable = false) })
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
