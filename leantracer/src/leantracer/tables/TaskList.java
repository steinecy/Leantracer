package leantracer.tables;

import java.math.BigDecimal;
import java.util.Date;

public class TaskList {
	
	private int aufgabe_id;
	private String aufgabe_bez;
	private Date datum;
	private BigDecimal zeitdauer;
	private int benutzer_id;
	private int kategorie_id;
	private String kategorie_bez;
	private int projekt_id;
	private String projekt_bez;
	private String system_id;
	private int standardaufgabe_id;

	public TaskList () {
		this.aufgabe_id = -1;
		this.aufgabe_bez = null;
		this.datum = null;
		this.zeitdauer = null;
		this.benutzer_id = -1;
		this.kategorie_id = -1;
		this.kategorie_bez = null;
		this.projekt_id = -1;
		this.projekt_bez = null;
		this.system_id = null;
		this.standardaufgabe_id = -1;		
	}

	public TaskList (int aufgabe_id, String aufgabe_bez, Date datum, BigDecimal zeitdauer, int benutzer_id,
		             int kategorie_id, String kategorie_bez, int projekt_id, String projekt_bez, 
		             String system_id, int standardaufgabe_id) {
		//super();
		this.aufgabe_id = aufgabe_id;
		this.aufgabe_bez = aufgabe_bez;
		this.datum = datum;
		this.zeitdauer = zeitdauer;
		this.benutzer_id = benutzer_id;
		this.kategorie_id = kategorie_id;
		this.kategorie_bez = kategorie_bez;
		this.projekt_id = projekt_id;
		this.projekt_bez = projekt_bez;
		this.system_id = system_id;
		this.standardaufgabe_id = standardaufgabe_id;
	}
	
	public int getAufgabe_id() {
		return aufgabe_id;
	}

	public void setAufgabe_id(int aufgabe_id) {
		this.aufgabe_id = aufgabe_id;
	}

	public String getAufgabe_bez() {
		return aufgabe_bez;
	}

	public void setAufgabe_bez(String aufgabe_bez) {
		this.aufgabe_bez = aufgabe_bez;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public BigDecimal getZeitdauer() {
		return zeitdauer;
	}

	public void setZeitdauer(BigDecimal zeitdauer) {
		this.zeitdauer = zeitdauer;
	}

	public int getBenutzer_id() {
		return benutzer_id;
	}

	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
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

	public void setProjekt_bez(String projekt_bez) {
		this.projekt_bez = projekt_bez;
	}	
	
	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	
	public int getStandardaufgabe_id() {
		return standardaufgabe_id;
	}

	public void setStandardaufgabe_id(int standardaufgabe_id) {
		this.standardaufgabe_id = standardaufgabe_id;
	}

	@Override
	public String toString() {
		return String.format("TaskList [aufgabe_id=%s, aufgabe_bez=%s, zeitdauer=%s, benutzer_id=%s, kategorie_id=%s "
			   + "kategorie_bez=%s, projekt_id=%s, projekt_bez=%s, system_id=%s, standardaufgabe_id=%s]",
			   aufgabe_id, aufgabe_bez, zeitdauer, benutzer_id, kategorie_id, kategorie_bez, projekt_id, 
			   projekt_bez, system_id, standardaufgabe_id);
	}
	
}

