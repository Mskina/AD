package herencia.joined;

import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "employeeId")
public class Developer extends Technician {

	private static final long serialVersionUID = -4257960483561161187L;
	private String expertLanguajes = null;

	public String getExpertLanguajes() {
		return expertLanguajes;
	}

	public void setExpertLanguajes(String expertLanguajes) {
		this.expertLanguajes = expertLanguajes;
	}

	@Override
	public String toString() {
		return "Developer { developerId=" + id + ", expertLanguajes=" + expertLanguajes + " }";
	}
}
