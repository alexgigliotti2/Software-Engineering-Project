package client.tests;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map.Entry;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import client.map.Field;
import client.map.Map;
import client.map.MapGenerator;
import client.map.Position;
import client.move.GoalFind;
import client.move.PathFind;

public class PathFind_Tests_UsingJUnit5 {
	private PathFind pathfind = new PathFind();
	private MapGenerator mapgenerator;
	private GoalFind goalfinder;
	Map map;
	
	
	@BeforeEach
	public void setUp() {
		System.out.println("This is weird");
		mapgenerator = new MapGenerator();
		goalfinder = new GoalFind();
		pathfind = new PathFind();
		map = new Map();
		map.updateMap(mapgenerator.generateMap());
		map.calcMapDirection();
		
		for(Entry<Position,Field> entry : map.getMap().entrySet()) {
			if(entry.getValue().getFort())
				map.setFort(entry.getKey());
		}
		if(map.getFort()==null)
			System.out.println("Mega confusion <3");
		System.out.println("Hello!");
		
		goalfinder.setUp(map.getFort(), map);
		goalfinder.findgoal();
		pathfind.setUp(map.getFort(), goalfinder.getGoal(), map);
		
	}
	
	
	@Test
	public void GoalSet_FindPathToGoal_PathFromCurrentToGoalExists() {
		Stack<Position> temp = pathfind.findpath();
		
		assertThat(temp.peek().getX(), equalTo(goalfinder.getGoal().getX()));
		assertThat(temp.peek().getY(), equalTo(goalfinder.getGoal().getY()));
		
	}
	
	
}
