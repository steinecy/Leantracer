package leantracer.tables;

public class Category {
	
	private int kategorie_id;
	private String kategorie_bez;
	private int benutzer_id;
	
	Category() {
		kategorie_id = -1;
		kategorie_bez = null;	
		benutzer_id = -1;
	}
	
	Category(int kategorie_id, String kategorie_bez, int benutzer_id) {
		this.kategorie_id = kategorie_id;
		this.kategorie_bez = kategorie_bez;	
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

	
	public int getBenutzer_id() {
		return benutzer_id;
	}

	
	public void setBenutzer_id(int benutzer_id) {
		this.benutzer_id = benutzer_id;
	}

	
	@Override
	public String toString() {
		return String.format("Category [kategorie_id=%s, kategorie_bez=%s, benutzer_id=%s]",
				kategorie_id, kategorie_bez, benutzer_id);
	}
}
