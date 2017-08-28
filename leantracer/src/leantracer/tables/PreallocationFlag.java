package leantracer.tables;

import java.util.Date;

public class PreallocationFlag {
	
	private int benutzer_id;
	private Date datum;
	
	PreallocationFlag() {
		benutzer_id = -1;
		datum = null;		
	}
	
	PreallocationFlag(int benutzer_id, Date datum) {
		this.benutzer_id = benutzer_id;
		this.datum = datum;		
	}
	
	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}
		
	
	public Date getDatum() {
		return datum;
	}

	
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	
	@Override
	public String toString() {
		return String.format("PreallocationFlag [benutzer_id=%s, datum=%s]",
			   benutzer_id, datum);
	}
}
