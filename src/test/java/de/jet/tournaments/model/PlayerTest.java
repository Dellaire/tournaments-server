package de.jet.tournaments.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class PlayerTest
{
	private Round round1 = this.createRound(new Player().setId("two"), new Player().setId("three"),
			new Player().setId("four"), new Player().setId("five"), "3", "6");
	private Round round2 = this.createRound(new Player().setId("one"), new Player().setId("three"),
			new Player().setId("four"), new Player().setId("five"), "5", "5");
	private Round round3 = this.createRound(new Player().setId("one"), new Player().setId("two"),
			new Player().setId("four"), new Player().setId("five"), "0", "6");
	private Round round4 = this.createRound(new Player().setId("one"), new Player().setId("two"),
			new Player().setId("three"), new Player().setId("five"), "6", "5");
	private Round round5 = this.createRound(new Player().setId("one"), new Player().setId("two"),
			new Player().setId("three"), new Player().setId("four"), "1", "6");

	private Tournament tournament = new Tournament().setRounds(Arrays.asList(round1, round2, round3, round4, round5));

	@Test
	public void retrieveUniquePlayerFromTournament()
	{
		assertThat(this.tournament.getPlayer().size(), is(5));
	}

	private Round createRound(Player player1Team1, Player player2Team1, Player player1Team2, Player player2Team2,
			String scoreTeam1, String scoreTeam2)
	{
		Team team1 = new Team().setPlayer1(player1Team1).setPlayer2(player2Team1);
		Team team2 = new Team().setPlayer1(player1Team2).setPlayer2(player2Team2);

		Match match = new Match().setTeam1(team1).setTeam2(team2).setTeam1Score(scoreTeam1).setTeam2Score(scoreTeam2);

		return new Round().addMatch(match);
	}
}
