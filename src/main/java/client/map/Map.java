package client.map;

import java.util.HashMap;
import java.util.Map.Entry;

public class Map {
	private HashMap<Position,Field> map = new HashMap<Position,Field>();
	private MapDirection mapdirection; 
	private Position fort;
	
	
	public Map() {
		this.map = new HashMap<Position,Field>();
	}
	
	public Map(HashMap<Position,Field> map) {
		this.map = new HashMap<Position,Field>(map);
	}
	
	public Map(HashMap<Position,Field> map, Position fort) {
		this.map = map;
		this.fort = fort;
		this.calcMapDirection();
	}
	
	public HashMap<Position,Field> getMap() {
		return map;
	}
	
	public void updateMap(HashMap<Position,Field> map) {
		this.map = map;
	}
	
	public void calcMapDirection() {
		Position fortpos = new Position();
		for(Entry<Position,Field> entry : this.map.entrySet()) {
			if(entry.getValue().getFort())
				fortpos = new Position(entry.getKey().getX(),entry.getKey().getY());
		}
		
		int length = 0;
	    int width = 0;
	    
	    for(Entry<Position, Field> entry : map.entrySet()) {
	    	if (entry.getKey().getX()==0)
	    		length++;
	    
	        if (entry.getKey().getY()== 0) 
	            width++;
	    }
	    
	    if(length==10) {
	    	if(fortpos.getY()<=4)
	    		mapdirection = MapDirection.VERUP;
	    	else
	    		mapdirection = MapDirection.VERDOWN;
	    }
	    else if(length==5) {
	    	if(fortpos.getX()>9)
	    		mapdirection = MapDirection.HORRIGHT;
	    	else
	    		mapdirection = MapDirection.HORLEFT;
	    }
		
	}

	public MapDirection getMapdirection() {
		return mapdirection;
	}

	public Position getFort() {
		return fort;
	}

	public void setFort(Position fort) {
		this.fort = fort;
	}
}
