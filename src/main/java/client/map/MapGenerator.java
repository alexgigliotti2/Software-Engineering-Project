package client.map;

import java.util.AbstractMap.SimpleEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Random;

public class MapGenerator {
	private HashMap<Position,Field> result;
	
	private Position fort;
	
	private final static Logger logger = LoggerFactory.getLogger(MapGenerator.class);
	
	private static final Random random = new Random();
	
	private ValidateMap validator;
	
	public MapGenerator() {
		this.result = new HashMap<Position,Field>();
		this.validator = new ValidateMap();
	}
	
	public HashMap<Position,Field> generateMap() {
		
		this.setWaterFields();
		
		this.setGrassFields();
		
		this.setHillFields();
		
		this.setRandomFields();
		
		this.setFort();
		
		return this.result;
	
	}
	
	private void setWaterFields() {

		int waterset = 0;
		
		while(waterset<7) {
		Position randompos = new Position(random);
		if (result.containsKey(randompos)) {}
		else {
			if (validator.checkAroundWater(result,randompos)==false) {
				continue;
			}
			if(validator.checkBorderNumber(randompos)==false) {
				continue;
			}
			 
			result.put(randompos,new Field(Terrain.WATER,false));
			waterset++;
			}			
		}
		
		logger.info("The required 7 Water fields were set!");
	}
	
	private void setGrassFields() {
		int grassset = 0;
		
		while(grassset<24) {
			Position randompos = new Position(random);
			if (result.containsKey(randompos)) {}
			else {
				result.put(randompos,new Field(Terrain.GRASS,false));
				grassset++;
			}
		}
		logger.info("The required 24 Grass fields were set!");
	}
	
	private void setHillFields() {

		int hillset = 0;
		
		while(hillset<5) {
			Position randompos = new Position(random);
			if (result.containsKey(randompos)) {}
			else {
				result.put(randompos,new Field(Terrain.HILL,false));
				hillset++;
			}
		}
		logger.info("The required 5 Hill fields were set!");
	}
	
	private void setRandomFields() {
		int lefttoset = 0;
		
		while (lefttoset<14) {
			Position randompos = new Position(random);
			if (result.containsKey(randompos)) {}
			else {
				Terrain randomTerrain = Terrain.getRandomTerrain();
				result.put(randompos,new Field(randomTerrain,false));
				lefttoset++;
			}
			
		}
		logger.info("The additional 14 fields were set with a Random Terrain!");
	}
	
	private void setFort() {
		Boolean setFort = false;
		while (setFort==false) {
			Position randompos = new Position(random);
			if(result.get(randompos).getTerrain()==Terrain.GRASS) {
				result.get(randompos).setFort();
				setFort = true;
				fort = randompos;
			}
		}
		
		logger.info("Shhh! Your fort was set on a Grass field!");
		
	}
	
	public HashMap<Position,Field> getResultMap() {
		return result;
	}

	public Position getFort() {
		return fort;
	}

	
}
