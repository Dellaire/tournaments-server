package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Table;
import de.jet.tournaments.model.Team;
import de.jet.tournaments.persistence.PlayerDataStore;
import de.jet.tournaments.persistence.TableDataStore;

public class AddTablesToRoundTest
{
	@Test
	public void addTablesToAllRounds()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("P1"));
		team1.setPlayer2(new Player().setName("P2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("P3"));
		team2.setPlayer2(new Player().setName("P4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("P5"));
		team3.setPlayer2(new Player().setName("P6"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("P7"));
		team4.setPlayer2(new Player().setName("P8"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42"), new Table().setName("43")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(Mockito.mock(PlayerDataStore.class),
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round roundWithTables = roundCalculator.addTablesToRound(round);

		List<Match> matchesWithTables = roundWithTables.getMatches().stream()
				.filter(match -> match.getTableName() != null).collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(2));
	}

	@Test
	public void addTableToOneRound()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("P1"));
		team1.setPlayer2(new Player().setName("P2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("P3"));
		team2.setPlayer2(new Player().setName("P4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("P5"));
		team3.setPlayer2(new Player().setName("P6"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("P7"));
		team4.setPlayer2(new Player().setName("P8"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(Mockito.mock(PlayerDataStore.class),
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round roundWithTables = roundCalculator.addTablesToRound(round);

		List<Match> matchesWithTables = roundWithTables.getMatches().stream()
				.filter(match -> match.getTableName() != null).collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
	}

	@Test
	public void addTableToOneMoreRound()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("P1"));
		team1.setPlayer2(new Player().setName("P2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("P3"));
		team2.setPlayer2(new Player().setName("P4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("P5"));
		team3.setPlayer2(new Player().setName("P6"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("P7"));
		team4.setPlayer2(new Player().setName("P8"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		match1.setTableName("42");
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42"), new Table().setName("43")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(Mockito.mock(PlayerDataStore.class),
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round roundWithTables = roundCalculator.addTablesToRound(round);

		List<Match> matchesWithTables = roundWithTables.getMatches().stream()
				.filter(match -> match.getTableName() != null).collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(2));
	}

	@Test
	public void addSameTableToOneMoreRound()
	{
		Team team1 = new Team();
		team1.setPlayer1(new Player().setName("P1"));
		team1.setPlayer2(new Player().setName("P2"));
		Team team2 = new Team();
		team2.setPlayer1(new Player().setName("P3"));
		team2.setPlayer2(new Player().setName("P4"));
		Team team3 = new Team();
		team3.setPlayer1(new Player().setName("P5"));
		team3.setPlayer2(new Player().setName("P6"));
		Team team4 = new Team();
		team4.setPlayer1(new Player().setName("P7"));
		team4.setPlayer2(new Player().setName("P8"));

		Match match1 = new Match();
		match1.setTeam1(team1);
		match1.setTeam2(team2);
		match1.setTableName("42");
		Match match2 = new Match();
		match2.setTeam1(team3);
		match2.setTeam2(team4);

		Round round = new Round();
		round.addMatch(match1);
		round.addMatch(match2);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(Mockito.mock(PlayerDataStore.class),
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round roundWithTables = roundCalculator.addTablesToRound(round);

		List<Match> matchesWithTables = roundWithTables.getMatches().stream()
				.filter(match -> match.getTableName() != null).collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(2));
		assertThat(matchesWithTables.get(0).getTableName(), is("42"));
		assertThat(matchesWithTables.get(1).getTableName(), is("42"));
	}
}
