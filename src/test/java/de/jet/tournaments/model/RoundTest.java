package de.jet.tournaments.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RoundTest
{
	@Test
	public void getPlayersOfRound()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("Player 1").setId("1"));
		team1.setPlayer2(new Player().setName("Player 2").setId("2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("Player 3").setId("3"));
		team2.setPlayer2(new Player().setName("Player 4").setId("4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("Player 5").setId("5"));
		team3.setPlayer2(new Player().setName("Player 6").setId("6"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("Player 7").setId("7"));
		team4.setPlayer2(new Player().setName("Player 8").setId("8"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		assertThat(round.getPlayer().size(), is(8));
	}
}
