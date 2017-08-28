package leantracer.tables;

public class StandardTask {
	
	private int standardaufgabe_id;
	private String standardaufgabe_bez;
	private int kategorie_id;
	private String kategorie_bez;
	private int projekt_id;
	private String projekt_bez;
	private String system_id;
	private int benutzer_id;
	private String benutzername;
	
	public StandardTask() {
		standardaufgabe_id = -1;
		standardaufgabe_bez = null;
		kategorie_id = -1;
		kategorie_bez = null;
		projekt_id = -1;
		projekt_bez = null;
		system_id = null;
		benutzer_id = -1;
		benutzername = null;		
	}
	
	public StandardTask(int standardaufgabe_id, String standardaufgabe_bez, int kategorie_id,
						String kategorie_bez, int projekt_id, String projekt_bez, String system_id,
						int benutzer_id, String benutzername) {
		this.standardaufgabe_id = standardaufgabe_id;
		this.standardaufgabe_bez = standardaufgabe_bez;
		this.kategorie_id = kategorie_id;
		this.kategorie_bez = kategorie_bez;
		this.projekt_id = projekt_id;
		this.projekt_bez = projekt_bez;
		this.system_id = system_id;
		this.benutzer_id = benutzer_id;
		this.benutzername = benutzername;
	}
	
	
	public int getStandardaufgabe_id() {
		return standardaufgabe_id;
	}

	
	public void setStandardaufgabe_id(int standardaufgabe_id) {
		this.standardaufgabe_id = standardaufgabe_id;
	}

	
	public String getStandardaufgabe_bez() {
		return standardaufgabe_bez;
	}

	
	public void setStandardaufgabe_bez(String standardaufgabe_bez) {
		this.standardaufgabe_bez = standardaufgabe_bez;
	}


	public int getKategorie_id() {
		return kategorie_id;
	}

	
	public void setKategorie_id(int kategorie_id) {
		this.kategorie_id = kategorie_id;
	}



	public String getKategorie_bez() {
		return kategorie_bez;
	}

	
	public void setKategorie_bez(String kategorie_bez) {
		this.kategorie_bez = kategorie_bez;
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

	
	public void setProjekt_id(String projekt_bez) {
		this.projekt_bez = projekt_bez;
	}
	

	public String getSystem_id() {
		return system_id;
	}

	
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	
	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}
	
	
	public String getBenutzername() {
		return benutzername;
	}

	
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}	

	
	@Override
	public String toString() {
		return String.format("StandardTask [standardaufgabe_id=%s, standardaufgabe_bez=%s, kategorie_id=%s,"
			   + " kategorie_bez=%s, projekt_id=%s, projekt_bez=%s, system_id=%s, benutzer_id=%s,"
			   + " benutzername=%s]",
			   standardaufgabe_id, standardaufgabe_bez, kategorie_id, kategorie_bez, projekt_id, 
			   projekt_bez, system_id, benutzer_id, benutzername);
	}
}
