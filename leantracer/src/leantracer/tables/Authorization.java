package leantracer.tables;

public class Authorization {
	
	private int benutzer_id;
	private int rolle_id;
	
	Authorization() {
		benutzer_id = -1;
		rolle_id = -1;		
	}
	
	Authorization(int benutzer_id, int rolle_id) {
		this.benutzer_id = benutzer_id;
		this.rolle_id = rolle_id;		
	}
	
	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}
		
	
	public int getRolle_id() {
		return rolle_id;
	}

	
	public void setRolle_id(int rolle_id) {
		this.rolle_id = rolle_id;
	}

	
	@Override
	public String toString() {
		return String.format("Authorization [benutzer_id=%s, rolle_id=%s]",
				benutzer_id, rolle_id);
	}
}
