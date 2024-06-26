package client.communication;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.map.Field;
import client.map.Map;
import client.map.Position;
import client.map.Terrain;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;

public class ClientConverter {
	
	public Map convertFullToMap(FullMap map) {
		HashMap<Position,Field> newmap = new HashMap<Position,Field>();
		Position fort = new Position();
		for(FullMapNode node : map.getMapNodes()) {
			Field currfield = new Field(convertTerrain(node.getTerrain()),convertFortState(node.getFortState()));
			
			if(node.getTreasureState()==ETreasureState.MyTreasureIsPresent) {
				currfield.setTreasure();
			}
			if(node.getFortState()==EFortState.MyFortPresent)
				fort = new Position(node.getX(),node.getY());
				
			newmap.put(new Position(node.getX(),node.getY()),currfield);
			
		}
		
		Map result = new Map(newmap,fort);
		
		return result;
	}
	
	public PlayerHalfMap convertHalfForServer(Map halfmap, String playerID) {
		List<PlayerHalfMapNode> maplist = new ArrayList<PlayerHalfMapNode>();
		
		for (Entry<Position, Field> entry : halfmap.getMap().entrySet()) {
			ETerrain terrain = null;
							
			switch(entry.getValue().getTerrain()) {
							
					case GRASS:
						terrain = ETerrain.Grass;
						break;
					case HILL:
						terrain = ETerrain.Mountain;
						break;
					case WATER:
						terrain = ETerrain.Water;
					
					
					
					}
					
					PlayerHalfMapNode node = new PlayerHalfMapNode(entry.getKey().getX(),entry.getKey().getY(),
							entry.getValue().getFort(),terrain);
					maplist.add(node);
				}
				
				PlayerHalfMap sendingmap = new PlayerHalfMap(playerID,maplist);
			
				return sendingmap;
				
	}
	
	
	public Terrain convertTerrain(ETerrain eterrain) {
		
		switch(eterrain) {
			case Grass:
			return Terrain.GRASS;
			case Mountain:
			return Terrain.HILL;
			default:
			return Terrain.WATER;
		
		}
		
		
		
	}
	
	public Boolean convertFortState(EFortState efortstate) {
		switch(efortstate) {
		case MyFortPresent:
			return true;
		default:
			return false;
		}
			
	}
	
}
