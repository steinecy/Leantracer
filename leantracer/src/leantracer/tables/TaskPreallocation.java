package leantracer.tables;

import java.math.BigDecimal;

public class TaskPreallocation {
	
	private int standardaufgabe_id;
	private int benutzer_id;
	private BigDecimal zeitdauer;
	
	TaskPreallocation() {
		standardaufgabe_id = -1;
		benutzer_id = -1;
		zeitdauer = null;
	}
	
	TaskPreallocation(int standardaufgabe_id, int benutzer_id, BigDecimal zeitdauer) {
		this.standardaufgabe_id = standardaufgabe_id ;
		this.benutzer_id = benutzer_id;
		this.zeitdauer = zeitdauer;
	}
	
	
	public int getStandardaufgabe_id() {
		return standardaufgabe_id;
	}

	
	public void setStandardaufgabe_id(int standardaufgabe_id) {
		this.standardaufgabe_id = standardaufgabe_id;
	}
		
	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}


	public BigDecimal getZeitdauer() {
		return zeitdauer;
	}

	
	public void setZeitdauer(BigDecimal zeitdauer) {
		this.zeitdauer = zeitdauer;
	}
	
	
	@Override
	public String toString() {
		return String.format("TaskPreallocation [system_id=%s, landschaft_id=%s, zeitdauer=%s]",
			   standardaufgabe_id, benutzer_id, zeitdauer);
	}
}
