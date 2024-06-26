package client.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import client.exceptions.UnvalidNextMoveException;
import client.map.Position;
import client.move.ClientMove;

public class ClientMove_Tests_UsingJUnit5 {
	private ClientMove clientmove = new ClientMove();
	
	
	@Test
	public void GivenPath_FindNextMoveDirection_MoveDirectionFound() {
		Position current = new Position(3,3);
		Position goal = new Position(3,5);
		Stack<Position> path = new Stack<>();
		path.push(current);
		path.push(goal);
		clientmove.setUp(current, goal, path);
		
		assertThrows(UnvalidNextMoveException.class, () -> {clientmove.nextMove();});
	}
}
