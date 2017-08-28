package leantracer.tables;

public class Systemlandscape {
	
	private int system_id;
	private int landschaft_id;
	
	Systemlandscape() {
		system_id = -1;
		landschaft_id = -1;		
	}
	
	Systemlandscape(int system_id, int landschaft_id) {
		this.system_id = system_id;
		this.landschaft_id = landschaft_id;		
	}
	
	
	public int getSystem_id() {
		return system_id;
	}

	
	public void setSystem_id(int system_id) {
		this.system_id = system_id;
	}
		
	
	public int getLandschaft_id() {
		return landschaft_id;
	}

	
	public void setLandschaft_id(int landschaft_id) {
		this.landschaft_id = landschaft_id;
	}

	
	@Override
	public String toString() {
		return String.format("Systemlandscape [system_id=%s, landschaft_id=%s]",
			   system_id, landschaft_id);
	}
}
