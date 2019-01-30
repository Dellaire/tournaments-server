package de.jet.tournaments.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class TournamentTest
{
	@Test
	public void getPlayersOfTournament()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("Player 1").setId("1"));
		team1.setPlayer2(new Player().setName("Player 2").setId("2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("Player 3").setId("3"));
		team2.setPlayer2(new Player().setName("Player 4").setId("4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("Player 1").setId("1"));
		team3.setPlayer2(new Player().setName("Player 3").setId("3"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("Player 2").setId("2"));
		team4.setPlayer2(new Player().setName("Player 4").setId("4"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		Tournament tournament = new Tournament();
		tournament.setRounds(Arrays.asList(round));

		assertThat(tournament.getPlayer().size(), is(4));
	}
}
