package client.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import client.communication.ClientConverter;
import client.communication.ClientNetwork;
import client.map.Map;
import client.map.Position;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;

public class ClientState {
	private Map fullmap = new Map();
	private Position current;
	private Position enemycurrent;
	private Boolean treasurefound = false;
	private Boolean fortfound = false;
	private Position treasure;
	private Position fort;
	private ClientConverter converter = new ClientConverter();
	private ClientNetwork network;
	
	private PropertyChangeSupport propertychange;
	
	public ClientState(ClientNetwork network) {
		this.network = network;
		this.propertychange = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertychange.addPropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String name, Object oldvalue, Object newvalue) {
		propertychange.firePropertyChange(name, oldvalue, newvalue);
	}
	
	
	public void updateState(GameState state) {
		if(state.getMap()!=null) {
		this.fullmap = converter.convertFullToMap(state.getMap());
		for(FullMapNode node : state.getMap()) {
			if(node.getPlayerPositionState()==EPlayerPositionState.MyPlayerPosition || node.getPlayerPositionState()==EPlayerPositionState.BothPlayerPosition) {
				this.current = new Position(node.getX(),node.getY());
			}
			
			if(node.getPlayerPositionState()==EPlayerPositionState.EnemyPlayerPosition)
				this.enemycurrent = new Position(node.getX(),node.getY());
			
			if(node.getTreasureState()==ETreasureState.MyTreasureIsPresent) 
				treasure = new Position(node.getX(),node.getY());
			
			if(node.getFortState()==EFortState.EnemyFortPresent)
				fort = new Position(node.getX(),node.getY());
		}
		

		for(PlayerState playerstate : state.getPlayers()) {
			if(playerstate.getUniquePlayerID().equals(network.getPlayerID())) {
				if(playerstate.hasCollectedTreasure()==true)
					this.treasurefound=true;
				if(playerstate.getState()==EPlayerGameState.Won)
					this.fortfound=true;
			}
		}
	}
	}
	
	public Map getFullMap() {
		return this.fullmap;
	}
	
	public Boolean getTreasureFound() {
		return this.treasurefound;
	}
	
	public Position getCurrent() {
		return this.current;
	}
	
	public Position getEnemyCurrent() {
		return this.enemycurrent;
	}
	
	public Position getTreasurePosition() {
		return this.treasure;
	}
	
	public Boolean getFortFound() {
		return this.fortfound;
	}
	
	public Position getFortPosition() {
		return this.fort;
	}
	
}

