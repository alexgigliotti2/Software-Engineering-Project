package client.move;

import java.util.Stack;

import client.map.Map;
import client.map.Position;
import client.map.Terrain;
import client.model.ClientState;

public class ClientKI {
	private GoalFind goalfinder = new GoalFind();
	private PathFind pathfinder = new PathFind();
	private ClientMove move = new ClientMove();
	private ClientState state;
	private Boolean extendedScan = false;
	private Stack<Position> extendedHillScan = new Stack<>();
	
	public ClientKI(ClientState state) {
		this.state = state;
	}
	
	public void setKI(Map fullmap) {
		goalfinder.setUp(state.getCurrent(), fullmap);
		
		goalfinder.findgoal();
		
		pathfinder.setUp(state.getCurrent(), goalfinder.getGoal(), fullmap);
		
		move.setUp(state.getCurrent(), goalfinder.getGoal(),pathfinder.findpath());
		
		goalfinder.addVisited(pathfinder.getPath());
		
	}
	
	public GoalFind getGoalFinder() {
		return this.goalfinder;
	}
	
	public PathFind getPathFinder() {
		return this.pathfinder;
	}
	
	public ClientMove getClientMove() {
		return this.move;
	}
	
	public void changeGoal(Map fullmap) {
		
		goalfinder.setGoal(state.getTreasurePosition());
		
		pathfinder.setUp(state.getCurrent(), goalfinder.getGoal(), fullmap);
		
		pathfinder.getPath().clear();
		
		pathfinder.getVisited().clear();
		
		if(this.checkifReachable(state.getTreasurePosition())) {
			
			if(extendedScan==true) {
				extendedScan=false;
				extendedHillScan.push(state.getTreasurePosition());
				move.setUp(state.getCurrent(), goalfinder.getGoal(),extendedHillScan);
			}
			else {
			Stack<Position> pathToTreasure = new Stack<Position>();
			pathToTreasure.push(state.getCurrent());
			pathToTreasure.push(state.getTreasurePosition());
			move.setUp(state.getCurrent(), goalfinder.getGoal(),pathToTreasure);
			}
			
		}
		else {
			move.setUp(state.getCurrent(), goalfinder.getGoal(),pathfinder.findpath());
		}
		
	}
	
	private Boolean checkifReachable(Position pos) {
		int xdif = state.getCurrent().getX() - pos.getX();
		int ydif = state.getCurrent().getY() - pos.getY();
		
		
		if(xdif==-1 && ydif==0) {
			return true;
		}
		if(xdif==1 && ydif==0) {
			return true;
		}
		if(xdif==0 && ydif==1) {
			return true;
		}
		if(xdif==0 && ydif==-1) {
			return true;
		}
		return extendedReachable(xdif,ydif);
		
	}
	
	private Boolean extendedReachable(int xdif, int ydif) {
		
		extendedHillScan.clear();
		extendedHillScan.push(state.getCurrent());
		extendedScan=true;
		
		if(xdif==1 && ydif==1) {
			Position postotreasure = new Position(state.getCurrent().getX()-1, state.getCurrent().getY());
			if (state.getFullMap().getMap().get(postotreasure).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(postotreasure);
				return true;
			}
			Position altpos = new Position(state.getCurrent().getX(), state.getCurrent().getY()-1);
			if (state.getFullMap().getMap().get(altpos).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(altpos);
				return true;
			}
		}
		
		if(xdif==-1 && ydif==1) {
			Position postotreasure = new Position(state.getCurrent().getX()+1, state.getCurrent().getY());
			if (state.getFullMap().getMap().get(postotreasure).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(postotreasure);
				return true;
			}
			Position altpos = new Position(state.getCurrent().getX(), state.getCurrent().getY()-1);
			if (state.getFullMap().getMap().get(altpos).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(altpos);
				return true;
			}
		}
		
		if(xdif==1 && ydif==-1) {
			Position postotreasure = new Position(state.getCurrent().getX(), state.getCurrent().getY()+1);
			if (state.getFullMap().getMap().get(postotreasure).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(postotreasure);
				return true;
			}
			Position altpos = new Position(state.getCurrent().getX()-1, state.getCurrent().getY());
			if (state.getFullMap().getMap().get(altpos).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(altpos);
				return true;
			}
		}
		

		if(xdif==-1 && ydif==-1) {
			Position pos = new Position(state.getCurrent().getX()+1, state.getCurrent().getY());
			if (state.getFullMap().getMap().get(pos).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(pos);
				return true;
			}
			Position altpos = new Position(state.getCurrent().getX(), state.getCurrent().getY()+1);
			if (state.getFullMap().getMap().get(altpos).getTerrain()!=Terrain.WATER) {
				extendedHillScan.push(altpos);
				return true;
			}
		}
		
		return false;
		
		
	}
	
	
	
	public void walkToOther(Map fullmap) {
		
		pathfinder.updateCurrent(state.getCurrent());
		
		Stack<Position> pathToHalf = pathfinder.pathOtherHalf();
		
		goalfinder.setGoal(pathToHalf.peek());
		
		move.setUp(state.getCurrent(), goalfinder.getGoal(), pathToHalf);
	}
	
	public void setFortKI(Map fullmap) {
		goalfinder.setUp(state.getCurrent(), fullmap);
		
		goalfinder.findFortGoal();
		
		pathfinder.setFortFind();
		
		pathfinder.setUp(state.getCurrent(), goalfinder.getGoal(), fullmap);
		
		move.setUp(state.getCurrent(), goalfinder.getGoal(),pathfinder.findpath());
		
		goalfinder.addVisited(pathfinder.getPath());
		
	}
	
	public void changeFortGoal(Map fullmap) {
		
		goalfinder.setGoal(state.getFortPosition());
		
		pathfinder.setUp(state.getCurrent(), goalfinder.getGoal(), fullmap);
		
		pathfinder.getPath().clear();
		
		pathfinder.getVisited().clear();
		
		if(this.checkifReachable(state.getFortPosition())) {
			
			if(extendedScan==true) {
				extendedScan=false;
				extendedHillScan.push(state.getFortPosition());
				move.setUp(state.getCurrent(), goalfinder.getGoal(),extendedHillScan);
			}
			else {
				Stack<Position> pathToFort = new Stack<Position>();
				pathToFort.push(state.getCurrent());
				pathToFort.push(state.getFortPosition());
				move.setUp(state.getCurrent(), goalfinder.getGoal(),pathToFort);
			}
			
		}
		else {
			move.setUp(state.getCurrent(), goalfinder.getGoal(),pathfinder.findpath());
		}
		
	}
	
	
	


}
