package leantracer.tables;

import java.math.BigDecimal;

public class User {
	private int benutzer_id;
	private String benutzername;
	private String vorname;
	private String nachname;
	private BigDecimal sollarbeitszeit;
	private int reihenfolge;
	
	public User() {
		benutzer_id = -1;
		benutzername = null;
		vorname = null;
		nachname = null;
		sollarbeitszeit = null;
		reihenfolge = -1;		
	}
	
	public User(int benutzer_id, String benutzername, String vorname, String nachname, 
				BigDecimal sollarbeitszeit, int reihenfolge) {
		this.benutzer_id = benutzer_id;
		this.benutzername = benutzername;
		this.vorname = vorname;
		this.nachname = nachname;
		this.sollarbeitszeit = sollarbeitszeit;
		this.reihenfolge = reihenfolge;
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
	
	
	public String getVorname() {
		return vorname;
	}

	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	
	public String getNachname() {
		return nachname;
	}
	
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	
	
	public BigDecimal getSollarbeitszeit() {
		return sollarbeitszeit;
	}
	
	
	public void setSollarbeitszeit(BigDecimal sollarbeitszeit) {
		this.sollarbeitszeit = sollarbeitszeit;
	}	
	
	public int getReihenfolge() {
		return reihenfolge;
	}
	
	
	public void setReihenfolge(int reihenfolge) {
		this.reihenfolge = reihenfolge;
	}
	
	
	@Override
	public String toString() {
		return String.format("User [benutzer_id=%s, benutzername=%s, vorname=%s, nachname=%s, "
							 + "sollarbeitszeit=%s, reihenfolge=%s]",
							 benutzer_id, benutzername, vorname, nachname, sollarbeitszeit, reihenfolge);
	}
}

