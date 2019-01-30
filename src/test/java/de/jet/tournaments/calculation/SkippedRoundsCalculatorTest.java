package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Team;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

public class SkippedRoundsCalculatorTest
{
	@Test
	public void calculateSkippedRoundsOfPlayer()
	{
		Player player1 = new Player().setName("Player 1").setId("1");
		Player player2 = new Player().setName("Player 2").setId("2");
		Player player3 = new Player().setName("Player 3").setId("3");
		Player player4 = new Player().setName("Player 4").setId("4");
		Player player5 = new Player().setName("Player 5").setId("5");
		Player player6 = new Player().setName("Player 6").setId("6");
		Player player7 = new Player().setName("Player 7").setId("7");
		Player player8 = new Player().setName("Player 8").setId("8");
		Player player9 = new Player().setName("Player 9").setId("9");
		Player player10 = new Player().setName("Player 10").setId("10");

		Team team1 = new Team();
		team1.setPlayer1(player1);
		team1.setPlayer2(player2);
		Team team2 = new Team();
		team2.setPlayer1(player3);
		team2.setPlayer2(player4);
		Team team3 = new Team();
		team3.setPlayer1(player5);
		team3.setPlayer2(player6);
		Team team4 = new Team();
		team4.setPlayer1(player7);
		team4.setPlayer2(player8);

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
		tournament.setId("42");
		tournament.setRounds(Arrays.asList(round));

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentById("42")).thenReturn(tournament);

		SkippedRoundsCalculator skippedRoundsCalculator = new SkippedRoundsCalculator(mockedTournamentDataStore);

		List<Player> playerWithSkippedRounds = skippedRoundsCalculator.calculateSkippedRounds(Arrays.asList(player1,
				player2, player3, player4, player5, player6, player7, player8, player9, player10), "42");

		assertThat(playerWithSkippedRounds.size(), is(2));
		assertThat(player1.getSkippedRounds(), is(0));
		assertThat(player2.getSkippedRounds(), is(0));
		assertThat(player3.getSkippedRounds(), is(0));
		assertThat(player4.getSkippedRounds(), is(0));
		assertThat(player5.getSkippedRounds(), is(0));
		assertThat(player6.getSkippedRounds(), is(0));
		assertThat(player7.getSkippedRounds(), is(0));
		assertThat(player8.getSkippedRounds(), is(0));
		assertThat(player9.getSkippedRounds(), is(1));
		assertThat(player10.getSkippedRounds(), is(1));
	}

	@Test
	public void emptyTournament()
	{
		Player player1 = new Player().setName("Player 1").setId("1");
		Player player2 = new Player().setName("Player 2").setId("2");
		Player player3 = new Player().setName("Player 3").setId("3");
		Player player4 = new Player().setName("Player 4").setId("4");
		Player player5 = new Player().setName("Player 5").setId("5");
		Player player6 = new Player().setName("Player 6").setId("6");
		Player player7 = new Player().setName("Player 7").setId("7");
		Player player8 = new Player().setName("Player 8").setId("8");
		Player player9 = new Player().setName("Player 9").setId("9");
		Player player10 = new Player().setName("Player 10").setId("10");

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentById("42")).thenReturn(null);

		SkippedRoundsCalculator skippedRoundsCalculator = new SkippedRoundsCalculator(mockedTournamentDataStore);

		List<Player> playerWithSkippedRounds = skippedRoundsCalculator.calculateSkippedRounds(Arrays.asList(player1,
				player2, player3, player4, player5, player6, player7, player8, player9, player10), "42");

		assertThat(playerWithSkippedRounds.size(), is(0));
		assertThat(player1.getSkippedRounds(), is(0));
		assertThat(player2.getSkippedRounds(), is(0));
		assertThat(player3.getSkippedRounds(), is(0));
		assertThat(player4.getSkippedRounds(), is(0));
		assertThat(player5.getSkippedRounds(), is(0));
		assertThat(player6.getSkippedRounds(), is(0));
		assertThat(player7.getSkippedRounds(), is(0));
		assertThat(player8.getSkippedRounds(), is(0));
		assertThat(player9.getSkippedRounds(), is(0));
		assertThat(player10.getSkippedRounds(), is(0));
	}
}
