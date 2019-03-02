package de.jet.tournaments.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jet.tournaments.calculation.RoundCalculator;
import de.jet.tournaments.exceptions.TournamentException;
import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Table;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

@RestController
public class TournamentController
{
	private final TournamentDataStore tournamentDataStore;
	private final RoundCalculator roundCalculator;

	@Autowired
	public TournamentController(TournamentDataStore tournamentDataStore, RoundCalculator roundCalculator)
	{
		this.tournamentDataStore = tournamentDataStore;
		this.roundCalculator = roundCalculator;
	}

	@ExceptionHandler(TournamentException.class)
	public ResponseEntity<String> handleException(TournamentException exception, HttpServletResponse response)
	{
		return new ResponseEntity<String>(exception.getMessage(), exception.getHttpStatus());
	}

	@RequestMapping(value = "/tournaments", method = RequestMethod.POST)
	public ResponseEntity<Tournament> postTournament(@RequestBody @Validated Tournament tournament)
			throws JsonProcessingException
	{
		return new ResponseEntity<Tournament>(this.tournamentDataStore.postTournament(tournament), HttpStatus.OK);
	}

	@RequestMapping(value = "/tournaments/{id}", method = RequestMethod.HEAD)
	public ResponseEntity<String> headTournaments(@PathVariable String id)
	{
		Tournament tournament = this.tournamentDataStore.headTournamentById(id);
		if (tournament == null)
		{
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(HttpStatus.FOUND);
	}

	@RequestMapping(value = "/tournaments", method = RequestMethod.GET)
	public ResponseEntity<List<Tournament>> getTournaments()
	{
		return new ResponseEntity<List<Tournament>>(this.tournamentDataStore.getTournaments(), HttpStatus.OK);
	}

	@RequestMapping(value = "/tournaments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tournament> getTournamentById(@PathVariable String id)
	{
		return new ResponseEntity<Tournament>(this.tournamentDataStore.getTournamentById(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/tournaments", params = { "name" }, method = RequestMethod.GET)
	public ResponseEntity<Tournament> getTournamentByName(@RequestParam String name)
	{
		return new ResponseEntity<Tournament>(this.tournamentDataStore.getTournamentByName(name), HttpStatus.OK);
	}

	@RequestMapping(value = "/tournaments/{tournamentId}/rounds", method = RequestMethod.PUT)
	public ResponseEntity<Round> putRound(@PathVariable String tournamentId, @RequestBody Round round)
			throws JsonProcessingException
	{
		return new ResponseEntity<Round>(this.tournamentDataStore.addRound(tournamentId, round), HttpStatus.OK);
	}

	@GetMapping(value = "/tournaments/{tournamentName}/player")
	public List<Player> getPlayer(@PathVariable String tournamentName)
	{
		return this.tournamentDataStore.readPlayer(tournamentName);
	}

	@PutMapping(value = "/tournaments/{tournamentName}/player")
	public Player addPlayer(@PathVariable String tournamentName, @RequestBody Player player)
	{
		return this.tournamentDataStore.addPlayer(tournamentName, player);
	}

	@GetMapping(value = "/tournaments/{tournamentName}/tables")
	public List<Table> getTables(@PathVariable String tournamentName)
	{
		return this.tournamentDataStore.readTables(tournamentName);
	}

	@PutMapping(value = "/tournaments/{tournamentName}/tables")
	public Table addTables(@PathVariable String tournamentName, @RequestBody Table table)
	{
		return this.tournamentDataStore.addTable(tournamentName, table);
	}

	@RequestMapping(value = "/tournaments/{tournamentName}/rounds/generate", method = RequestMethod.PUT)
	public ResponseEntity<Round> putGeneratedRound(@PathVariable String tournamentName) throws JsonProcessingException
	{
		// TODO move number calculation to other place
		Optional<Integer> lastRoundNumber = tournamentDataStore.getTournamentByName(tournamentName).getRounds().stream()
				.map(round -> Integer.parseInt(round.getName())).max((x, y) -> Integer.compare(x, y));

		Round round = this.roundCalculator.generateNewRound(tournamentName);
		if (lastRoundNumber.isPresent())
		{
			round.setName(lastRoundNumber.get() + 1 + "");
		} else
		{
			round.setName(1 + "");
		}

		return new ResponseEntity<Round>(this.tournamentDataStore.addRound(tournamentName, round), HttpStatus.OK);
	}

	// @CrossOrigin
	// @RequestMapping(value = "/tournaments/{tournamentId}/rounds/addTables",
	// method = RequestMethod.PUT)
	// public ResponseEntity<Round> addTablesToRound(@RequestBody Round round)
	// throws AmqpException, JsonProcessingException
	// {
	// Round roundWithTables =
	// this.matchmakingService.addTablesToRound(round).getBody();
	//
	// return new
	// ResponseEntity<Round>(this.tournamentDataStore.addRound(tournamentId,
	// roundWithTables),
	// HttpStatus.OK);
	// }

	@RequestMapping(value = "/tournaments/{tournamentId}/rounds/{roundName}", method = RequestMethod.POST)
	public ResponseEntity<Match> addMatch(@PathVariable String tournamentId, @PathVariable String roundName,
			@RequestBody @Validated Match match)
	{
		return new ResponseEntity<Match>(this.tournamentDataStore.addMatch(tournamentId, roundName, match),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/tournaments/{tournamentId}/rounds/{roundName}/matches", method = RequestMethod.PUT)
	public ResponseEntity<Match> putMatch(@PathVariable String tournamentName, @PathVariable String roundName,
			@RequestBody Match match) throws JsonProcessingException
	{
		Match putMatch = this.tournamentDataStore.putMatch(tournamentName, roundName, match);

		Round roundWithoutTables = this.tournamentDataStore.getRoundById(tournamentName, roundName);
		Round roundWithTables = this.roundCalculator.addTablesToRound(tournamentName, roundWithoutTables);
		this.tournamentDataStore.putRound(tournamentName, roundWithTables);

		return new ResponseEntity<Match>(putMatch, HttpStatus.OK);
	}
}
