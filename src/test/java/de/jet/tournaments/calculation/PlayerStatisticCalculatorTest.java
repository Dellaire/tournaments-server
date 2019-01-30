package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;
import org.mockito.Mockito;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.PlayerStatistic;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Team;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

public class PlayerStatisticCalculatorTest
{
	private Player player1 = new Player().setId("1").setName("one");
	private Player player2 = new Player().setId("2").setName("two");
	private Player player3 = new Player().setId("3").setName("three");
	private Player player4 = new Player().setId("4").setName("four");
	private Player player5 = new Player().setId("5").setName("five");

	private Round round1 = this.createRound(this.player2, this.player3, this.player4, this.player5, "3", "6");
	private Round round2 = this.createRound(this.player1, this.player3, this.player4, this.player5, "5", "5");
	private Round round3 = this.createRound(this.player1, this.player2, this.player4, this.player5, "0", "6");
	private Round round4 = this.createRound(this.player1, this.player2, this.player3, this.player5, "6", "5");
	private Round round5 = this.createRound(this.player1, this.player2, this.player3, this.player4, "1", "6");

	private Tournament tournament = new Tournament().setRounds(Arrays.asList(round1, round2, round3, round4, round5));

	@Test
	public void calculatePlayerStatistic()
	{
		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentById("42")).thenReturn(this.tournament);

		List<PlayerStatistic> playerStatistics = new PlayerStatisticCalculator(mockedTournamentDataStore)
				.calculatePlayerStaticstics("42");

		assertThat(playerStatistics.size(), is(5));

		this.checkPlayerStatistic(playerStatistics, "1", 1, 2, 1, 12, 22);
		this.checkPlayerStatistic(playerStatistics, "2", 1, 3, 0, 10, 23);
		this.checkPlayerStatistic(playerStatistics, "3", 1, 2, 1, 19, 18);
		this.checkPlayerStatistic(playerStatistics, "4", 3, 0, 1, 23, 9);
		this.checkPlayerStatistic(playerStatistics, "5", 2, 1, 1, 22, 14);
	}

	private Round createRound(Player player1Team1, Player player2Team1, Player player1Team2, Player player2Team2,
			String scoreTeam1, String scoreTeam2)
	{
		Team team1 = new Team().setPlayer1(player1Team1).setPlayer2(player2Team1);
		Team team2 = new Team().setPlayer1(player1Team2).setPlayer2(player2Team2);

		Match match = new Match().setTeam1(team1).setTeam2(team2).setTeam1Score(scoreTeam1).setTeam2Score(scoreTeam2);

		return new Round().addMatch(match);
	}

	private void checkPlayerStatistic(List<PlayerStatistic> playerStatistics, String playerId, Integer wins,
			Integer defeats, Integer draws, Integer goals, Integer counterGoals)
	{
		assertThat(this.extractStatistic(playerStatistics, playerId, playerStatistic -> playerStatistic.getWins()),
				is(wins));
		assertThat(this.extractStatistic(playerStatistics, playerId, playerStatistic -> playerStatistic.getDefeats()),
				is(defeats));
		assertThat(this.extractStatistic(playerStatistics, playerId, playerStatistic -> playerStatistic.getDraws()),
				is(draws));
		assertThat(this.extractStatistic(playerStatistics, playerId, playerStatistic -> playerStatistic.getGoals()),
				is(goals));
		assertThat(
				this.extractStatistic(playerStatistics, playerId, playerStatistic -> playerStatistic.getCounterGoals()),
				is(counterGoals));
	}

	private Integer extractStatistic(List<PlayerStatistic> playerStatistics, String playerId,
			Function<PlayerStatistic, Integer> extractStatistic)
	{
		return extractStatistic.apply(playerStatistics.stream()
				.filter(playerStatistic -> playerStatistic.getPlayerId().equals(playerId)).findFirst().get());
	}
}
