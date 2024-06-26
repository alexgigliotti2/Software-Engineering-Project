package client.tests;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;

import client.map.Field;
import client.map.Map;
import client.map.MapGenerator;
import client.map.Position;
import client.map.Terrain;
import client.move.GoalFind;

public class GoalFind_Tests_UsingJUnit5 {
	private GoalFind goalfinder;
	private MapGenerator mapgenerator;
	private Map map;
	
	
	@BeforeEach
	public void setUp() {
		mapgenerator = new MapGenerator();
		goalfinder = new GoalFind();
		map = new Map();
		map.updateMap(mapgenerator.generateMap());
		map.calcMapDirection();
		
		for(Entry<Position,Field> entry : map.getMap().entrySet()) {
			if(entry.getValue().getFort())
				map.setFort(entry.getKey());
		}
		
		goalfinder.setUp(map.getFort(), map);
	}
	
	
	@Test
	public void MapGenerated_FindGoal_NonWaterGoalFound() {
		Position temp = goalfinder.findgoal();
		
		assertThat(map.getMap().get(temp).getTerrain(), not(equalTo(Terrain.WATER)));
		
	}
	
	@Test
	public void MapGenerated_FindGoal_ReachableGoalFound() {
		Position temp = goalfinder.findgoal();
		assertTrue(temp.checkIfInMap());
	}
}
