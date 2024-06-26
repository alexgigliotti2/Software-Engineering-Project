package client.map;

public enum MapDirection {
	HORRIGHT,HORLEFT,VERUP,VERDOWN;
	
	public MapDirection reverseDirection(MapDirection direction) {
		switch(direction) {
		case HORRIGHT:
			return HORLEFT;
		case HORLEFT:
			return HORRIGHT;
		case VERUP:
			return VERDOWN;
		default:
			return VERUP;
		
		}
	}
}
