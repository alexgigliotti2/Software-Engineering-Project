package client.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import client.communication.ClientController;
import client.communication.ClientConverter;
import client.communication.ClientNetwork;
import client.exceptions.GameIDException;
import client.map.Map;
import client.map.MapGenerator;
import client.model.ClientState;
import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ERequestState;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.EPlayerPositionState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
import reactor.core.publisher.Mono;

public class MainClient {

	
	private final static Logger logger = LoggerFactory.getLogger(MainClient.class);
	
	public static void main(String[] args) {
		
		
		String serverBaseUrl = args[1];
		String gameId = args[2];
		
		try {
		if(gameId==null ||  gameId.isEmpty() || gameId.length()!=5)
			throw new GameIDException("GameID is not in the right format!");
		}
		catch(GameIDException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
			
		ClientNetwork network = new ClientNetwork(serverBaseUrl,gameId);
		 
		MapGenerator mapgenerator = new MapGenerator();
		
		Map playermap = new Map();
		
		ClientState gamestate = new ClientState(network);
		
		ClientConverter converter = new ClientConverter();
		
		ClientController controller = new ClientController(network,mapgenerator,playermap,gamestate, converter);
		
		
		controller.setup();
		
		controller.sendMap();
		
		controller.playForTreasure();
		
		controller.walkToOther();
		
		controller.playForFort();
		
				
	}
	

}