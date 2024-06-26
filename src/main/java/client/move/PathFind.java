package client.move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.map.Map;
import client.map.Position;
import client.map.Terrain;
import messagesbase.messagesfromclient.EMove;

public class PathFind {
	private Position current;
	private Position goal;
	private Position lastvisited;
	private Stack<Position> path = new Stack<Position>();
	private Stack<Position> visited = new Stack<Position>();
	private Map map = new Map();
	private Boolean treasurefound=false;
	private Boolean findfort = false;
	int stuckcount = 0;
	private Boolean ignoreVisited = false;
	private Set<Position> backtrackedNodes = new HashSet<Position>();
	private final static Logger logger = LoggerFactory.getLogger(PathFind.class);

	
	
	public PathFind() {
		
	}
	
	public PathFind(Position curr, Map map) {
		this.current = curr;
		this.map = map;
	}
	
	public Stack<Position> findpath() {
		
		logger.info("Trying to find a path to the goal now...");
		
		stuckcount=0;
		this.ignoreVisited=false;
		backtrackedNodes.clear();
		
		path.add(current);
		visited.add(current);
		
		
		while(!current.equals(goal)) {
			
			
			if(trymove(current.getX()+1,current.getY())) {
				path.push(current);
				visited.push(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()-1)) {
				path.push(current);
				visited.push(current);
				continue;
			}
			
			
			if (trymove(current.getX()-1,current.getY())) {
				path.push(current);
				visited.push(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()+1)) {
				path.push(current);
				visited.push(current);
				continue;
			}
			
		
			backTrack();
				
		}
		
		logger.info("A path to the goal was created!");
		
		return path;
	}
	
	private boolean trymove(int x, int y) {
		Position nextpos = new Position(x,y);
		if(treasurefound==false) {
			if(!nextpos.checkIfInMyHalf(map.getMapdirection()))
				return false;
		}
		if(treasurefound==true && findfort==false) {
			if(!nextpos.checkifInFullMap(map.getMapdirection()))
				return false;
		}
		if(this.findfort==true) {
			if(nextpos.checkIfInMyHalf(map.getMapdirection()))
				return false;
			if(!nextpos.checkifInFullMap(map.getMapdirection()))
				return false;
		}
		
		if(map.getMap().get(nextpos).getTerrain()==Terrain.WATER)
			return false;
		
		if(backtrackedNodes.contains(nextpos))
			return false;
	
		if(this.ignoreVisited==false) {
			if(visited.contains(nextpos)) 
				return false;
		}
		else {
			if(path.contains(nextpos))
				return false;
		}
		
		if(nextpos.equals(lastvisited))
				return false;
		
		lastvisited = current;
		current = nextpos;
			return true;
		}
		
	
	private void backTrack() {
		stuckcount++;
		
		if(stuckcount>20) {
			this.ignoreVisited=true;
			stuckcount=0;
		}
		
		if(path.size()>1) {
		backtrackedNodes.add(visited.pop());
		lastvisited = path.pop();
		current = path.peek();
		}
		else {
		lastvisited = new Position(20,20);
		this.ignoreVisited=true;
		
		
		}
	}
	
	public void setUp(Position current, Position goal, Map map) {
		this.current = current;
		this.goal = goal;
		this.map = map;
	}
	
	public Position getCurrent() {
		return current;
	}
	
	public Stack<Position> getVisited() {
		return visited;
	}
	
	public void newPath(Position goal) {
		this.goal = goal;
		this.visited.clear();
		this.path.clear();
		
	}
	
	public Stack<Position> getPath() {
		return this.path;
	}
	
	public Stack<Position> pathOtherHalf() {
		
		logger.info("Trying to find a path to the other half now...");
		
		treasurefound=true;
		path.clear();
		backtrackedNodes.clear();
		visited.clear();
		visited.add(current);
		path.add(current);
		lastvisited = new Position(20,20);
		
		switch(map.getMapdirection()) {
		case HORRIGHT:
			prioritizeLeft();
			break;
		case HORLEFT:
			prioritizeRight();
			break;
		case VERDOWN:
			prioritizeUp();
			break;
		case VERUP:
			prioritizeDown();
			break;
		
		}
		
		return this.path;
		
	}
	
	private void prioritizeLeft() {
		while(current.checkIfInMyHalf(map.getMapdirection())) {
			if (trymove(current.getX()-1,current.getY())) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()-1)) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()+1)) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			
		}
	}
	
	private void prioritizeRight() {
		while(current.checkIfInMyHalf(map.getMapdirection())) {
			if(trymove(current.getX()+1,current.getY())) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()-1)) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if (trymove(current.getX(),current.getY()+1)) {
				path.push(current);
				visited.add(current);
				continue;
			}
		}
	}
	
	private void prioritizeUp() {
		while(current.checkIfInMyHalf(map.getMapdirection())) {
			if (trymove(current.getX(),current.getY()-1)) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if(trymove(current.getX()+1,current.getY())) {
				path.push(current);
				visited.add(current);
				continue;
			}
			
			if (trymove(current.getX()-1,current.getY())) {
				path.push(current);
				visited.add(current);
				continue;
			}
		}
		
	}
	
	private void prioritizeDown() {
		while(current.checkIfInMyHalf(map.getMapdirection())) {
		if (trymove(current.getX(),current.getY()+1)) {
			path.push(current);
			visited.add(current);
			continue;
		}
		
		if (trymove(current.getX()-1,current.getY())) {
			path.push(current);
			visited.add(current);
			continue;
		}
		
		if(trymove(current.getX()+1,current.getY())) {
			path.push(current);
			visited.add(current);
			continue;
		}
		
		}
		
	}
	
	public void updateCurrent(Position curr) {
		this.current = curr;
	}
	
	public void setFortFind() {
		this.findfort = true;
	}
	
	public void setTreasureFind() {
		this.treasurefound =true;
	}
	
	
	

	
}
