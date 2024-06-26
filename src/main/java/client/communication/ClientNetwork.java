package client.communication;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ERequestState;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.EPlayerGameState;
import messagesbase.messagesfromserver.GameState;
import messagesbase.messagesfromserver.PlayerState;
import reactor.core.publisher.Mono;

public class ClientNetwork {
	
	private WebClient baseWebClient;
	private String serverBaseUrl;
	private String playerID;
	private String gameID;
	private final static Logger logger = LoggerFactory.getLogger(ClientNetwork.class);
	
	
	public ClientNetwork(String serverBaseUrl, String gameID) {
		this.serverBaseUrl = serverBaseUrl;
		this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
			 .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
		
		this.gameID = gameID;
	
	}
	
	public void registerPlayer() throws Exception {
		PlayerRegistration playerReg = new PlayerRegistration("FirstName", "LastName",
				"Username");
	
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/players")
				.body(BodyInserters.fromValue(playerReg))
				.retrieve().bodyToMono(ResponseEnvelope.class);
		
		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

	
		if (resultReg.getState() == ERequestState.Error) {
			
			System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
		} else {
			UniquePlayerIdentifier uniqueID = resultReg.getData().get();
			this.playerID = uniqueID.getUniquePlayerID(); 
			System.out.println("My Player ID: " + uniqueID.getUniquePlayerID());
		
		}
		
		
	}
	
	public void sendHalfMap(PlayerHalfMap halfmap) throws Exception {
		
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/halfmaps")
				.body(BodyInserters.fromValue(halfmap))
				.retrieve().bodyToMono(ResponseEnvelope.class);
		
		ResponseEnvelope<?> resultReg = webAccess.block();
		
		
		if (resultReg.getState() == ERequestState.Error) {
			
			System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
			
		
		}
	}
	
	public void sendMovement(PlayerMove move) throws Exception {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/moves")
				.body(BodyInserters.fromValue(move))
				.retrieve().bodyToMono(ResponseEnvelope.class);
		
		ResponseEnvelope<?> resultReg = webAccess.block();
		
		
		if (resultReg.getState() == ERequestState.Error) {
			
			System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
			
		
		
		
		}
	}
	
	public GameState getStatus() throws Exception {
		

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
		.uri("/" + gameID + "/states/" + this.playerID).retrieve().bodyToMono(ResponseEnvelope.class); 
		ResponseEnvelope<GameState> requestResult = webAccess.block();
		
		
		Optional<GameState> gamestate = requestResult.getData();
		
		
		if(gamestate.isPresent()) {
			
			return gamestate.get();
		
		}
		
		if (requestResult.getState() == ERequestState.Error) {
			System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
		}
		
		throw new Exception("Gamestate not present!");
		
	}
	
	public String getPlayerID() {
		return this.playerID;
	}
	
	
	public GameState polling() throws Exception {
		while(true) {
			GameState gamestate = new GameState();
			try {
				gamestate = getStatus();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			Set<PlayerState> gamestatelist = gamestate.getPlayers();
			
			for(PlayerState currstate : gamestatelist) {
				if(currstate.getUniquePlayerID().equals(this.playerID)) {
					if(currstate.getState()==EPlayerGameState.Won) {
						logger.info("Congratulations, you won the game!");
						System.exit(0);
						return gamestate;
					}
					if(currstate.getState()==EPlayerGameState.Lost) {
						logger.info("The other player beat you! Next time it'll be a win!");
						System.exit(0);
						return gamestate;
					}
					if(currstate.getState()==EPlayerGameState.MustAct) {
						return gamestate;
					}
				}
			}
			
			
			Thread.sleep(400);
			
			
		}		
	}
	
}
