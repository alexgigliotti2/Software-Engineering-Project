package client.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.internal.matchers.GreaterOrEqual;

import client.map.Field;
import client.map.Map;
import client.map.MapGenerator;
import client.map.Position;
import client.map.Terrain;

public class MapGenerator_Tests_UsingJUnit5 {
	
	private MapGenerator mapgenerator;
	
	
	@BeforeEach
	public void setUp() {
		mapgenerator = new MapGenerator();
	}
	
	@Test
	public void UserRegistered_GenerateMap_MapNodeRightAmount() {
		HashMap<Position,Field> map = mapgenerator.generateMap();
		
		assertEquals(50, map.size());
	}
	
	@Test
	public void UserRegistered_GenerateMap_GrassNodeRightAmount() {
		HashMap<Position,Field> map = mapgenerator.generateMap();
		int grasscounter = 0;
		
		for(Field field : map.values()) {
			if(field.getTerrain()==Terrain.GRASS)
				grasscounter++;
		}
		
		assertThat(grasscounter, greaterThanOrEqualTo(24));
		
	}
	
	@Test
	public void UserRegistered_GenerateMap_WaterNodeRightAmount() {
		HashMap<Position,Field> map = mapgenerator.generateMap();
		int watercounter = 0;
		
		for(Field field : map.values()) {
			if(field.getTerrain()==Terrain.WATER)
				watercounter++;
		}
		
		assertThat(watercounter, greaterThanOrEqualTo(7));
		
	}
	
	@Test
	public void UserRegistered_GenerateMap_HillNodeRightAmount() {
		HashMap<Position,Field> map = mapgenerator.generateMap();
		int hillcounter = 0;
		
		for(Field field : map.values()) {
			if(field.getTerrain()==Terrain.WATER)
				hillcounter++;
		}
		
		assertThat(hillcounter, greaterThanOrEqualTo(5));
		
	}
	
	@ParameterizedTest
	@CsvSource({"3,2","9,4","0,0"})
	public void MapGenerated_CheckIfInHalfMap_IsInHalfMap(int x, int y) {
		Position pos = new Position(x,y);
		assertTrue(pos.checkIfInMap());
	}
}
