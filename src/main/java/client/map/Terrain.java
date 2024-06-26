package client.map;

import java.util.Random;

public enum Terrain {
	HILL,GRASS,WATER;
	
	private static final Random random = new Random();
	
	public static Terrain getRandomTerrain() {
		int randomnumber = random.nextInt(0, 2);
		return Terrain.values()[randomnumber];
		
	}
}
