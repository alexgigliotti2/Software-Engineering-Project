package client.map;

public class Field {
	private Terrain fieldtype;
	private Boolean fortpresent;
	private Boolean treasurepresent =false;
	
	public Field() {}
	
	public Field(Terrain terrain, Boolean fort) {
		this.fieldtype = terrain;
		this.fortpresent = fort;
	}
	
	public Terrain getTerrain() {
		return this.fieldtype;
		
	}
	
	public void setFort() {
		fortpresent = true;
	}
	
	public Boolean getFort() {
		return fortpresent;
	}
	
	public void setTreasure() {
		this.treasurepresent=true;
	}
	
	public Boolean getTreasure() {
		return this.treasurepresent;
	}
}
