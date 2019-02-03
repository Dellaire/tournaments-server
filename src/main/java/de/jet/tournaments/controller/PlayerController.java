package de.jet.tournaments.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.jet.tournaments.model.Player;
import de.jet.tournaments.persistence.PlayerDataStore;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
	
	private final PlayerDataStore playerDataStore;

	@Autowired
	public PlayerController(PlayerDataStore playerDataStore) {
		
		this.playerDataStore = Objects.requireNonNull(playerDataStore);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Player> postPlayer(@RequestBody Player player) {

		return new ResponseEntity<Player>(this.playerDataStore.postPlayer(player), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Player> putPlayer(@RequestBody Player player) {

		return new ResponseEntity<Player>(this.playerDataStore.putPlayer(player), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Player>> getPlayer() {
		
		return new ResponseEntity<List<Player>>(this.playerDataStore.getPlayers(), HttpStatus.OK);
	}
}
