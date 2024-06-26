package client.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map.Entry;

import client.map.Field;
import client.map.Map;
import client.map.Position;
import client.model.ClientState;

public class Interface implements PropertyChangeListener {
	
		private ClientState state;
		private Map fullmap;
		private Position mycurrent;
		private Position enemycurrent;
	

	    public Interface(ClientState state) {
	        this.state = state;
	    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("map"))
			this.fullmap = (Map) evt.getNewValue();
		
		if(evt.getPropertyName().equals("mycurrent"))
			this.mycurrent = (Position) evt.getNewValue();
		
		if(evt.getPropertyName().equals("enemycurrent"))
			this.enemycurrent = (Position) evt.getNewValue();
		
	}
	
	public void showFullMap() {
		int length = 0;
	    int width = 0;
	    
	    for(Entry<Position, Field> entry : fullmap.getMap().entrySet()) {
	    	if (entry.getKey().getX()==0)
	    		length++;
	    
	        if (entry.getKey().getY()== 0) 
	            width++;
	    }
	        System.out.println();
			for (int y = 0; y < length; y++) {
			    for (int x = 0; x < width; x++) {
			        Position position = new Position(x, y);
			        
			        
			        
			        if(fullmap.getMap().get(position).getFort()) {
			        	System.out.print("F ");
			        	continue;
			        }
			        
			        if(position.equals(mycurrent)) {
			        	System.out.print("P ");
			        	continue;
			        }
			        
			        if(position.equals(enemycurrent)) {
			        	System.out.print("E ");
			        	continue;
			        }
			        
		
			        Field field = fullmap.getMap().get(position);
			        char type = ' ';
			        switch (field.getTerrain()) {
			        case GRASS:
	                    System.out.print("\033[32mG\033[0m");
	                    break;
	                case WATER:
	                    System.out.print("\033[34mW\033[0m");
	                    break;
	                case HILL:
	                    System.out.print("\033[37mH\033[0m");
	                    break;
			        }
			        System.out.print(type);
			    }
			    System.out.println();
			}
			
			
	        
	    }
	    
	    
	}
		
	

