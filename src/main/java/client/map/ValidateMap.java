package client.map;

import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateMap {
	
	int bordertop;
	int borderbottom;
	int borderleft;
	int borderright;
	
	private final static Logger logger = LoggerFactory.getLogger(ValidateMap.class);
	
	public ValidateMap() {
	}
	
	public Boolean checkAroundWater(HashMap<Position,Field> map, Position pos) {
		int x = pos.getX();
	    int y = pos.getY();
	    
	    for (int i = x-1; i <= x+1; i++) {
	        for (int j = y-1; j <= y+1; j++) {
	        	
	        	if (i == x && j == y) {
	                continue;
	            }
	        	
	        	if(i>=0 && i<10 && j>=0 && j<5) {
	        		Position around = new Position(i,j);
	        		if(!map.containsKey(around)) {}
	        		else if (map.get(around).getTerrain()==Terrain.WATER) {
	        			logger.warn("Water found, retrying with new Position now!");
		        		return false;
		        		
		        	}
	        		
	        	}
		
	        }
	    }
	    
	    return true;
	}
	
	public Boolean checkBorderNumber(Position pos) {
		    String border = pos.getBorder();
		    switch(border) {
		        case "topleft":
		            if(bordertop<4 && borderleft<2) {
		            	bordertop++;
		            	borderleft++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "topright":
		        	if(bordertop<4 && borderright<2) {
		            	bordertop++;
		            	borderright++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "bottomleft":
		        	if(borderbottom<4 && borderleft<2) {
		            	borderbottom++;
		            	borderleft++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "bottomright":
		        	if(borderbottom<4 && borderright<2) {
		            	borderbottom++;
		            	borderright++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "top":
		        	if(bordertop<4) {
		            	bordertop++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "bottom":
		        	if(borderbottom<4) {
		            	borderbottom++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "left":
		        	if(borderleft<2) {
		            	borderleft++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        case "right":
		        	if(borderright<2) {
		            	borderright++;
		            	return true;
		            }
		            else {
		            	logger.warn("Border has max amount of Water already, retrying with new Position");
		            	return false;
		            }
		        default:
		        	return true;
		    }
		}

		
		
		
	

}
