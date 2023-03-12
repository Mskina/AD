package herencia.perClass;

import javax.persistence.Entity;

@Entity
public class Technician extends Employee {

	private static final long serialVersionUID = -4347462420357080179L;
	private int experienceYears = 0;

	public int getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(int experienceYears) {
		this.experienceYears = experienceYears;
	}

	@Override
	public String toString() {
		return "Technician { technicianId=" + id + ", experienceYears=" + experienceYears + " }";
	}
}
