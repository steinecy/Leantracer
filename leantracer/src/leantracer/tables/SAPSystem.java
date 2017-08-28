package leantracer.tables;

public class SAPSystem {
	
	private String system_id;
	private String system_bez;
	private int landschaft_id;
	private String landschaft_bez;
	
	public SAPSystem() {
		system_id = null;
		system_bez = null;
		landschaft_id = -1;
		landschaft_bez = null;	
	}
	
	public SAPSystem(String system_id, String system_bez, int landschaft_id, String landschaft_bez) {
		this.system_id = system_id;
		this.system_bez = system_bez;
		this.landschaft_id = landschaft_id;
		this.landschaft_bez = landschaft_bez;	
	}
	
	
	public String getSystem_id() {
		return system_id;
	}

	
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
		
	
	public String getSystem_bez() {
		return system_bez;
	}

	
	public void setSystem_bez(String system_bez) {
		this.system_bez = system_bez;
	}

	
	public int getLandschaft_id() {
		return landschaft_id;
	}

	
	public void setLandschaft_id(int landschaft_id) {
		this.landschaft_id = landschaft_id;
	}
		
	
	public String getLandschaft_bez() {
		return landschaft_bez;
	}

	
	public void setLandschaft_bez(String landschaft_bez) {
		this.landschaft_bez = landschaft_bez;
	}

		
	@Override
	public String toString() {
		return String.format("System [system_id=%s, system_bez=%s, landschaft_id=%s, landschaft_bez=%s]",
			   system_id, system_bez, landschaft_id, landschaft_bez);
	}
}
