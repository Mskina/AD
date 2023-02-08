package modelo;
// Generated 22:38:40, 2 de feb. de 2023 by Hibernate Tools 6.1.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Dept generated by hbm2java
 */
public class Departamento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709022754445027322L;
	private Integer deptno;
	private String dname;
	private String loc;
	private Set emps = new HashSet(0);

	public Departamento() {
	}

	public Departamento(String dname, String loc, Set emps) {
		this.dname = dname;
		this.loc = loc;
		this.emps = emps;
	}

	public Integer getDeptno() {
		return this.deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Set getEmps() {
		return this.emps;
	}

	public void setEmps(Set emps) {
		this.emps = emps;
	}

	@Override
	public String toString() {
		return "Departamento [deptno=" + deptno + ", dname=" + dname + ", loc=" + loc + "]";
	}
	
	

}
