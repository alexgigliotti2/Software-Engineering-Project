package client.move;

import java.util.Collections;
import java.util.Stack;

import client.exceptions.UnvalidNextMoveException;
import client.map.Position;
import messagesbase.messagesfromclient.EMove;

public class ClientMove {
	private Stack<Position> path = new Stack<Position>();
	private Position current;
	private Position goal;
	private Boolean foundtreasure;
	
	public ClientMove() {}
	
	public ClientMove(Stack<Position> path, Position current, Position goal) {
		Collections.reverse(path);
		this.path = path;
		this.current = current;
		this.goal = goal;
	}
	
	public EMove nextMove() {
		Position next = path.pop();
		int diffx = current.getX() - next.getX();
		int diffy = current.getY() - next.getY();
		
		current=next;
		
		if(diffx==-1 &&  diffy==0)
			return EMove.Right;
		
		if(diffx==1 && diffy==0)
			return EMove.Left;
		
		if(diffx==0 && diffy==-1)
			return EMove.Down;
		
		if(diffx==0 && diffy==1)
			return EMove.Up;
		
		throw new UnvalidNextMoveException("Die nächste Position im Path-Stack ist nicht erreichbar, der Move ist nicht valide!");
		
	}
	
	public Position getCurrent() {
		return current;
	}
	
	public Stack<Position> getPath() {
		return path;
	}
	
	public void newPath(Position goal, Stack<Position> stack) {
		this.goal = goal;
		Collections.reverse(stack);
		this.path = stack;
	}
	
	public void setUp(Position current, Position goal, Stack<Position> stack) {
		this.current = current;
		this.goal = goal;
		Collections.reverse(stack);
		stack.pop();
		this.path = stack;
	}
}
