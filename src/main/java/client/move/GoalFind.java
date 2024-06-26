package client.move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import client.map.Map;
import client.map.Position;
import client.map.Terrain;
import jakarta.validation.Path;

public class GoalFind {
	private Position current;
	private Position goal;
	private Set<Position> visited = new HashSet<Position>();
	private Map halfmap = new Map();
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GoalFind.class);
	
	private static final Random random = new Random();
	
	public GoalFind(Position current, Map halfmap) {
		this.current = current;
		this.halfmap = halfmap;
	}
	
	public GoalFind() {}
	
	public Position findgoal() {
		
	logger.info("Finding a goal now...");
		Boolean goalfound = false;
		
		while(goalfound==false) {
			Position goalcand = new Position(random,halfmap.getMapdirection());
			if(visited.contains(goalcand)) {
				continue;
			}
			if(current.equals(goalcand)) {
				continue;
			}
			
			if(halfmap.getMap().get(goalcand).getTerrain()==Terrain.GRASS) {
				visited.add(goalcand);
				goalfound=true;
				goal=goalcand;
			}
				
				
			}
		
		return goal;
	}
	
	public Position findFortGoal() {
		
		logger.info("Finding a goal now...");
		
		Boolean goalfound = false;
		
		while(goalfound==false) {
		Position goalcand = new Position(random,halfmap.getMapdirection().reverseDirection(halfmap.getMapdirection()));
		if(visited.contains(goalcand)) {
			continue;
		}
		if(current.equals(goalcand)) {
			continue;
		}
		
		if(halfmap.getMap().get(goalcand).getTerrain()==Terrain.GRASS) {
			visited.add(goalcand);
			goalfound=true;
			goal=goalcand;
		}
			
			
		}
	
	return goal;
	}

	
	
	public Set<Position> getVisitedList() {
		return visited;
	}
	
	public Boolean isGoalReached() {
		if(current.equals(goal))
			return true;
		return false;
	}
	
	
	public void setGoal(Position goal) {
		this.goal = goal;
	}
	
	public void setUp(Position curr, Map map) {
		this.current = curr;
		this.halfmap = map;
		this.visited.add(current);
	}
	
	public void addVisited(Stack<Position> stack) {
		for(Position pos : stack) {
			visited.add(pos);
		}
	}
	
	public Position getGoal() {
		return goal;
	}
	
	public void setCurrent(Position curr) {
		this.current = curr;
	}
 }
