package leantracer.tables;

public class Role {
	
	private int role_id;
	private String role_bez;
	
	Role() {
		role_id = -1;
		role_bez = null;		
	}
	
	Role(int role_id, String role_bez) {
		this.role_id = role_id;
		this.role_bez = role_bez;		
	}
	
	
	public int getRole_id() {
		return role_id;
	}

	
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
		
	
	public String getRole_bez() {
		return role_bez;
	}

	
	public void setRole_bez(String role_bez) {
		this.role_bez = role_bez;
	}

	
	@Override
	public String toString() {
		return String.format("Role [role_id=%s, role_bez=%s]",
			   role_id, role_bez);
	}
}
