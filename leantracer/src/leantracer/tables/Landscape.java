package leantracer.tables;

public class Landscape {
	
	private int landschaft_id;
	private String landschaft_bez;
	
	Landscape() {
		landschaft_id = -1;
		landschaft_bez = null;		
	}
	
	Landscape(int landschaft_id, String landschaft_bez) {
		this.landschaft_id = landschaft_id;
		this.landschaft_bez = landschaft_bez;		
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
		return String.format("Landscape [landschaft_id=%s, landschaft_bez=%s]",
			   landschaft_id, landschaft_bez);
	}
}
