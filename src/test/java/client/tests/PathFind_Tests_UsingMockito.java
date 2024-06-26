package client.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.HashMap;
import java.util.Stack;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import client.map.Field;
import client.map.Map;
import client.map.MapDirection;
import client.map.Position;
import client.map.Terrain;
import client.move.PathFind;

public class PathFind_Tests_UsingMockito {
	private Map mockedmap = new Map();
	private PathFind pathfind = new PathFind();
	private Position current;
	private Field mockedfield;
	private HashMap<Position,Field> mockedhash = new HashMap<>();
	private Position mockedposition;
	
	
	@Test
	public void TreasureFound_ReachOtherHalf_OtherHalfffHORLEFTtReached() {
		current = new Position(3,3);
		
		mockedmap = Mockito.mock(Map.class);
		mockedfield = Mockito.mock(Field.class);
		mockedhash = Mockito.mock(HashMap.class);
		mockedposition = Mockito.mock(Position.class);
		
		Mockito.when(mockedmap.getMap()).thenReturn(mockedhash);
		Mockito.when(mockedmap.getMap().get(any(Position.class))).thenReturn(mockedfield);
		Mockito.when(mockedmap.getMap().get(mockedposition).getTerrain()).thenReturn(Terrain.WATER,Terrain.WATER,Terrain.GRASS);
		
		Mockito.when(mockedmap.getMapdirection()).thenReturn(MapDirection.HORLEFT);
		
		pathfind = new PathFind(current,mockedmap);
		pathfind.setTreasureFind();
		Stack<Position> temp = pathfind.pathOtherHalf();
		
		assertTrue(!temp.peek().checkIfInMyHalf(MapDirection.HORLEFT));
		
	}
	
	@Test
	public void TreasureFound_ReachOtherHalf_OtherHalfOfHORRIGHTReached() {
		current = new Position(12,2);
		
		mockedmap = Mockito.mock(Map.class);
		mockedfield = Mockito.mock(Field.class);
		mockedhash = Mockito.mock(HashMap.class);
		mockedposition = Mockito.mock(Position.class);
		
		Mockito.when(mockedmap.getMap()).thenReturn(mockedhash);
		Mockito.when(mockedmap.getMap().get(any(Position.class))).thenReturn(mockedfield);
		Mockito.when(mockedmap.getMap().get(mockedposition).getTerrain()).thenReturn(Terrain.WATER,Terrain.WATER,Terrain.GRASS,Terrain.WATER,Terrain.GRASS);
		
		Mockito.when(mockedmap.getMapdirection()).thenReturn(MapDirection.HORRIGHT);
		
		pathfind = new PathFind(current,mockedmap);
		pathfind.setTreasureFind();
		Stack<Position> temp = pathfind.pathOtherHalf();
		
		assertTrue(!temp.peek().checkIfInMyHalf(MapDirection.HORRIGHT));
	}
	
	@Test
	public void TreasureFound_ReachOtherHalf_OtherHalfOfVERUPReached() {
		current = new Position(3,3);
		
		mockedmap = Mockito.mock(Map.class);
		mockedfield = Mockito.mock(Field.class);
		mockedhash = Mockito.mock(HashMap.class);
		mockedposition = Mockito.mock(Position.class);
		
		Mockito.when(mockedmap.getMap()).thenReturn(mockedhash);
		Mockito.when(mockedmap.getMap().get(any(Position.class))).thenReturn(mockedfield);
		Mockito.when(mockedmap.getMap().get(mockedposition).getTerrain()).thenReturn(Terrain.WATER,Terrain.WATER,Terrain.GRASS);
		
		Mockito.when(mockedmap.getMapdirection()).thenReturn(MapDirection.VERUP);
		
		pathfind = new PathFind(current,mockedmap);
		pathfind.setTreasureFind();
		Stack<Position> temp = pathfind.pathOtherHalf();
		
		assertTrue(!temp.peek().checkIfInMyHalf(MapDirection.VERUP));
	}
	
	@Test
	public void TreasureFound_ReachOtherHalf_OtherHalfOfVERDOWNReached() {
		current = new Position(3,6);
		
		mockedmap = Mockito.mock(Map.class);
		mockedfield = Mockito.mock(Field.class);
		mockedhash = Mockito.mock(HashMap.class);
		mockedposition = Mockito.mock(Position.class);
		
		Mockito.when(mockedmap.getMap()).thenReturn(mockedhash);
		Mockito.when(mockedmap.getMap().get(any(Position.class))).thenReturn(mockedfield);
		Mockito.when(mockedmap.getMap().get(mockedposition).getTerrain()).thenReturn(Terrain.WATER,Terrain.WATER,Terrain.GRASS);
		
		Mockito.when(mockedmap.getMapdirection()).thenReturn(MapDirection.VERDOWN);
		
		pathfind = new PathFind(current,mockedmap);
		pathfind.setTreasureFind();
		Stack<Position> temp = pathfind.pathOtherHalf();
		
		assertTrue(!temp.peek().checkIfInMyHalf(MapDirection.VERDOWN));
	}
	
}
