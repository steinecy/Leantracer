package leantracer.tables;

public class Project {
	
	private int projekt_id;
	private String projekt_bez;
	private int benutzer_id;
	
	Project() {
		projekt_id = -1;
		projekt_bez = null;	
		benutzer_id = -1;
	}
	
	Project(int projekt_id, String projekt_bez, int benutzer_id) {
		this.projekt_id = projekt_id;
		this.projekt_bez = projekt_bez;	
		this.benutzer_id = benutzer_id;
	}
	
	
	public int getProjekt_id() {
		return projekt_id;
	}

	
	public void setProjekt_id(int projekt_id) {
		this.projekt_id = projekt_id;
	}
		
	
	public String getProjekt_bez() {
		return projekt_bez;
	}

	
	public void setProjekt_bez(String projekt_bez) {
		this.projekt_bez = projekt_bez;
	}

	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}

	
	@Override
	public String toString() {
		return String.format("Project [projekt_id=%s, projekt_bez=%s, benutzer_id=%s]",
			   projekt_id, projekt_bez, benutzer_id);
	}
}
