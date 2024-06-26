package client.communication;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.exceptions.UnvalidNextMoveException;
import client.map.Map;
import client.map.MapGenerator;
import client.model.ClientState;
import client.move.ClientKI;
import client.ui.Interface;
import messagesbase.messagesfromclient.PlayerMove;

public class ClientController {
	private ClientNetwork network;
	private MapGenerator mapgenerator;
	private Map halfmap;
	private Map fullmap;
	private ClientState state;
	private ClientConverter converter;
	private ClientKI clientki;
	private Interface ui;
	private final static Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	public ClientController(ClientNetwork network, MapGenerator mapgenerator, Map halfmap, ClientState state, ClientConverter converter) {
		this.network = network;
		this.mapgenerator = mapgenerator;
		this.halfmap = halfmap;
		this.state =  state;
		this.converter = converter;
		this.clientki = new ClientKI(state);
		this.ui = new Interface(state);
		state.addPropertyChangeListener(ui);
	}
	
	private void updateState() {
		try {
			state.updateState(network.polling());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		state.firePropertyChange("map", null, state.getFullMap());
		state.firePropertyChange("mycurrent", null, state.getCurrent());
		state.firePropertyChange("enemycurrent", null, state.getEnemyCurrent());
	}
	
	
	public void setup() {
		try {
			network.registerPlayer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Player registered successfully!");
		
		halfmap.updateMap(mapgenerator.generateMap());
		
		logger.info("Map generated successfully!");
		
	}
	
	public void sendMap() {
		
			this.updateState();
		
		try {
			network.sendHalfMap(converter.convertHalfForServer(halfmap, network.getPlayerID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.updateState();
		
		/*try {
			state.updateState(network.polling());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		logger.info("Map sent to Server successfully!");
	}
	
	public void playForTreasure() {
		
		Boolean treasurefound = false;
		
		this.fullmap = state.getFullMap();
		
		ui.showFullMap();
		
		clientki.setKI(fullmap);
		
		while(!state.getTreasureFound()) {
			if(clientki.getClientMove().getPath().isEmpty()) 
				clientki.setKI(fullmap);

			this.updateState();
			
			try {
			PlayerMove servermove = PlayerMove.of(network.getPlayerID(), clientki.getClientMove().nextMove());
			
			
			try {
				network.sendMovement(servermove);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.updateState();
			
			
			this.walkToField(servermove);
			}
			catch(UnvalidNextMoveException e) {
				System.err.println(e.getMessage());
				clientki.setKI(fullmap);
				continue;
			}
			
				if(state.getTreasurePosition()!=null && treasurefound==false) {
					logger.info("The treasure was spotted!");
					treasurefound=true;
					clientki.changeGoal(fullmap);
				}
			
			ui.showFullMap();
			
			}
		
		logger.info("The treasure was found!");
		}
	
	
	
	private void walkToField(PlayerMove servermove) {
		
		while(!state.getCurrent().equals(clientki.getClientMove().getCurrent())) {
			
			try {
				network.sendMovement(servermove);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.updateState();
		}
		
		
	}
	
	public void walkToOther() {
		
		logger.info("Now walking to the other half of the map...");
		clientki.walkToOther(fullmap);
		
		while(state.getCurrent().checkIfInMyHalf(fullmap.getMapdirection())) {
			
			this.updateState();
		
		try {
			PlayerMove servermove = PlayerMove.of(network.getPlayerID(), clientki.getClientMove().nextMove());
			

			try {
				network.sendMovement(servermove);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.updateState();
			
			this.walkToField(servermove);
			
			
		}
		catch(UnvalidNextMoveException e) {
			System.err.println(e.getMessage());
			clientki.setKI(fullmap);
			continue;
		}
		
		ui.showFullMap();
		
		
	}
		
		logger.info("You reached the other map half!");
	}
	
	
	public void playForFort() {
		
		Boolean fortfound = false;
		
		this.fullmap = state.getFullMap();
		
		ui.showFullMap();
		
		clientki.setFortKI(fullmap);	
		
		while(!state.getFortFound()) {
			if(clientki.getClientMove().getPath().isEmpty()) 
				clientki.setFortKI(fullmap);
			
			this.updateState();
			
			try {
				PlayerMove servermove = PlayerMove.of(network.getPlayerID(), clientki.getClientMove().nextMove());
				
				try {
					network.sendMovement(servermove);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				this.updateState();
				
				
				this.walkToField(servermove);
				
			}catch(UnvalidNextMoveException e) {
				System.err.println(e.getMessage());
				clientki.setKI(fullmap);
				continue;
			}
			
			

			if(state.getFortPosition()!=null && fortfound==false) {
				logger.info("The enemy fortress was spotted!");
				fortfound=true;
				clientki.changeFortGoal(fullmap);
			}
			
			ui.showFullMap();
		}
		
		logger.info("The enemy fortress was conquered! Game won!");
		
	}
}
			
			
	
	
	
	
   

