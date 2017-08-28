package leantracer.tables;

import java.math.BigDecimal;
import java.util.Date;

public class Stack {
	
	private int stackaufgabe_id;
	private String stackaufgabe_bez;
	private BigDecimal zeitdauer;
	private int benutzer_id;
	private int standardaufgabe_id;
	private int stack_nr;
	private Date datum;
	private int reihenfolge;
    
	public Stack () {
		this.stackaufgabe_id = -1;
		this.stackaufgabe_bez = null;
		this.zeitdauer = null;
		this.benutzer_id = -1;
		this.standardaufgabe_id = -1;
		this.stack_nr = -1;
		this.datum = null;
		this.reihenfolge = -1;
	}
	
	public Stack(int stackaufgabe_id, String stackaufgabe_bez, BigDecimal zeitdauer, int benutzer_id,
		int standardaufgabe_id, int stack_nr, Date datum) {
		super();
		this.stackaufgabe_id = stackaufgabe_id;
		this.stackaufgabe_bez = stackaufgabe_bez;
		this.zeitdauer = zeitdauer;
		this.benutzer_id = benutzer_id;
		this.standardaufgabe_id = standardaufgabe_id;
		this.stack_nr = stack_nr;
		this.datum = datum;
		this.reihenfolge = -1;
	}
	
	public int getStackaufgabe_id() {
		return stackaufgabe_id;
	}

	public void setStackaufgabe_id(int stackaufgabe_id) {
		this.stackaufgabe_id = stackaufgabe_id;
	}

	public String getStackaufgabe_bez() {
		return stackaufgabe_bez;
	}

	public void setStackaufgabe_bez(String stackaufgabe_bez) {
		this.stackaufgabe_bez = stackaufgabe_bez;
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

	public int getStandardaufgabe_id() {
		return standardaufgabe_id;
	}

	public void setStandardaufgabe_id(int standardaufgabe_id) {
		this.standardaufgabe_id = standardaufgabe_id;
	}
	
	public int getStack_nr() {
		return stack_nr;
	}

	public void setStack_nr(int stack_nr) {
		this.stack_nr = stack_nr;
	}
	
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getReihenfolge() {
		return reihenfolge;
	}

	public void setReihenfolge(int reihenfolge) {
		this.reihenfolge = reihenfolge;
	}
	
	@Override
	public String toString() {
		return String
				.format("Stack [stackaufgabe_id=%s, stackaufgabe_bez=%s, zeitdauer=%s, benutzer_id=%s, "
						+ "standardaufgabe_id=%s, stack_nr=%s, datum=%s, reihenfolge=%s]",
						stackaufgabe_id, stackaufgabe_bez, zeitdauer, benutzer_id, standardaufgabe_id,
						stack_nr, datum, reihenfolge);
	}
	
	
	
}
