package de.jet.tournaments;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Team;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.PlayerRepository;
import de.jet.tournaments.persistence.TournamentRepository;

@Component
public class TestDataRunner implements CommandLineRunner
{
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Override
	public void run(String... args) throws Exception
	{
		this.playerRepository.insert(new Player().setName("Henry").setActive(true).setId("0").setScore(73));
		this.playerRepository.insert(new Player().setName("Huy").setActive(true).setId("1").setScore(75));
		this.playerRepository.insert(new Player().setName("Nick").setActive(true).setId("2").setScore(66));
		this.playerRepository.insert(new Player().setName("Mario").setActive(true).setId("3").setScore(63));
		this.playerRepository.insert(new Player().setName("Lina").setActive(true).setId("4").setScore(55));
		
		List<Player> players = Arrays.asList(new Player().setName("Henry").setActive(true).setId("0").setScore(73),
				new Player().setName("Huy").setActive(true).setId("1").setScore(75),
				new Player().setName("Nick").setActive(true).setId("2").setScore(66),
				new Player().setName("Mario").setActive(true).setId("3").setScore(63),
				new Player().setName("Lina").setActive(true).setId("4").setScore(55));
		
		Round round1 = new Round().setName("1").setMatches(Arrays.asList(
				new Match().setId("0-1").setTableName("Leonhard").setTeam1(
						new Team().setPlayer1(new Player().setName("Henry")).setPlayer2(new Player().setName("Huy"))).setTeam1Score("6")
				.setTeam2(
						new Team().setPlayer1(new Player().setName("Nick")).setPlayer2(new Player().setName("Mario"))).setTeam2Score("4")));
		
		Round round2 = new Round().setName("2").setMatches(Arrays.asList(
				new Match().setId("1-1").setTableName("Leonhard").setTeam1(
						new Team().setPlayer1(new Player().setName("Nick")).setPlayer2(new Player().setName("Mario"))).setTeam1Score("5")
				.setTeam2(
						new Team().setPlayer1(new Player().setName("Lina")).setPlayer2(new Player().setName("Huy"))).setTeam2Score("5")));
		
		Round round3 = new Round().setName("3").setMatches(Arrays.asList(
				new Match().setId("2-1").setTableName("Leonhard").setTeam1(
						new Team().setPlayer1(new Player().setName("Henry")).setPlayer2(new Player().setName("Mario")))
				.setTeam2(
						new Team().setPlayer1(new Player().setName("Nick")).setPlayer2(new Player().setName("Huy")))));
		
		List<Round> rounds = Arrays.asList(round1, round2, round3);
		
		Tournament tournament = new Tournament().setId("0").setName("DYP").setPlayer(players).setRounds(rounds);
		
		this.tournamentRepository.insert(tournament);
	}
}
