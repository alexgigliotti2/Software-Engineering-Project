package client.map;

import java.util.Objects;
import java.util.Random;

public class Position {
	private int x;
	private int y;
	
	public Position() {
	}
	
	public Position(Random random) {
			this.x = random.nextInt(0,10);
			this.y = random.nextInt(0,5);
	}
	
	public Position(Random random, MapDirection direction) {
		
		switch(direction) {
		case HORLEFT:
			this.x = random.nextInt(0,10);
			this.y = random.nextInt(0,5);
			break;
		case VERUP:
			this.x = random.nextInt(0,10);
			this.y = random.nextInt(0,5);
			break;
		case HORRIGHT:
			this.x = random.nextInt(10,20);
			this.y = random.nextInt(0,5);
			break;
		case VERDOWN:
			this.x = random.nextInt(0,10);
			this.y = random.nextInt(5,10);
			break;
		}
		
		}

	 @Override
	    public boolean equals(Object obj) {
	        if (obj instanceof Position) {
	            Position other = (Position) obj;
	            return this.x == other.x && this.y == other.y;
	        }
	        return false;
	    }
	 
	 @Override
	    public int hashCode() {
	        return Objects.hash(x, y);
	    }
	 
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String getBorder() {
		if (x==0 && y == 0)
			return "topleft";
		else if(x==9 && y==0)
			return "topright";
		else if(x==0 && y==4)
			return "bottomleft";
		else if(x==9 && y==4)
			return "bottomright";
		else if(y==0) 
			return "top";
		else if(y==4)
			return "bottom";
		else if(x==0)
			return "left";
		else if(x==9)
			return "right";
		
		return "no";
		
	}
	
	public Boolean checkIfInMap() {
		if(this.x >= 0 && this.x <=9 && this.y >= 0 && this.y <=4)
			return true;
		return false;
	}
	
	public Boolean checkIfInMyHalf(MapDirection direction) {
		
		switch(direction) {
		
			case HORLEFT:
				if(this.x >= 0 && this.x <=9 && this.y >= 0 && this.y <=4)
					return true;
				return false;
			case HORRIGHT:
				if(this.x >= 10 && this.x <=19 && this.y >= 0 && this.y <=4)
					return true;
				return false;
			case VERUP:
				if(this.x >= 0 && this.x <=9 && this.y >= 0 && this.y <=4)
					return true;
				return false;
			default:
				if(this.x >= 0 && this.x <=9 && this.y >= 5 && this.y <=9)
					return true;
				return false;
				
		}
		
	}
	
	public Boolean checkifInFullMap(MapDirection direction) {
		switch(direction) {
			case HORLEFT:
				if(this.x>=0 && this.x<=19 && this.y>= 0 && this.y <= 4)
					return true;
				return false;
				
			case HORRIGHT:
				if(this.x>=0 && this.x<=19 && this.y>= 0 && this.y <= 4)
					return true;
				return false;
				
			case VERUP:
				if(this.x>=0 && this.x<=9 && this.y>= 0 && this.y <= 9)
					return true;
				return false;
				
			default:
				if(this.x>=0 && this.x<=9 && this.y>= 0 && this.y <= 9)
					return true;
				return false;	
		
		}
	}
	
	


}
