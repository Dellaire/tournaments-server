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
import de.jet.tournaments.persistence.PlayerDataStore;
import de.jet.tournaments.persistence.TableDataStore;

public class GenerateNewRoundTest
{
	@Test
	public void generateNewRoundWithNoSkip()
	{
		List<Player> players = new ArrayList<Player>();
		players.add(new Player().setName("P1"));
		players.add(new Player().setName("P2"));
		players.add(new Player().setName("P3"));
		players.add(new Player().setName("P4"));
		players.add(new Player().setName("P5"));
		players.add(new Player().setName("P6"));
		players.add(new Player().setName("P7"));
		players.add(new Player().setName("P8"));

		PlayerDataStore mockedPlayerDataStore = Mockito.mock(PlayerDataStore.class);
		Mockito.when(mockedPlayerDataStore.getPlayers()).thenReturn(players);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table()));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(mockedPlayerDataStore,
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round round = roundCalculator.generateNewRound("42");

		assertThat(round.getMatches().size(), is(2));
	}

	@Test
	public void generateNewRoundWithSkip()
	{
		List<Player> players = new ArrayList<Player>();
		players.add(new Player().setName("P1"));
		players.add(new Player().setName("P2"));
		players.add(new Player().setName("P3"));
		players.add(new Player().setName("P4"));
		players.add(new Player().setName("P5"));
		players.add(new Player().setName("P6"));
		players.add(new Player().setName("P7"));

		PlayerDataStore mockedPlayerDataStore = Mockito.mock(PlayerDataStore.class);
		Mockito.when(mockedPlayerDataStore.getPlayers()).thenReturn(players);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table()));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(mockedPlayerDataStore,
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round round = roundCalculator.generateNewRound("42");

		assertThat(round.getMatches().size(), is(1));
	}

	@Test
	public void notEnoghTables()
	{
		List<Player> players = new ArrayList<Player>();
		players.add(new Player().setName("P1"));
		players.add(new Player().setName("P2"));
		players.add(new Player().setName("P3"));
		players.add(new Player().setName("P4"));
		players.add(new Player().setName("P5"));
		players.add(new Player().setName("P6"));
		players.add(new Player().setName("P7"));
		players.add(new Player().setName("P8"));

		PlayerDataStore mockedPlayerDataStore = Mockito.mock(PlayerDataStore.class);
		Mockito.when(mockedPlayerDataStore.getPlayers()).thenReturn(players);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(mockedPlayerDataStore,
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
		assertThat(matchesWithTables.get(0).getTableName(), is("42"));
	}

	@Test
	public void ignoreInactiveTables()
	{
		List<Player> players = new ArrayList<Player>();
		players.add(new Player().setName("P1"));
		players.add(new Player().setName("P2"));
		players.add(new Player().setName("P3"));
		players.add(new Player().setName("P4"));
		players.add(new Player().setName("P5"));
		players.add(new Player().setName("P6"));
		players.add(new Player().setName("P7"));
		players.add(new Player().setName("P8"));

		PlayerDataStore mockedPlayerDataStore = Mockito.mock(PlayerDataStore.class);
		Mockito.when(mockedPlayerDataStore.getPlayers()).thenReturn(players);

		List<Table> tables = new ArrayList<Table>(
				Arrays.asList(new Table().setName("42"), new Table().setName("43").setActive(false)));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(mockedPlayerDataStore,
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
		assertThat(matchesWithTables.get(0).getTableName(), is("42"));
	}

	@Test
	public void ignoreInactivePlayer()
	{
		List<Player> players = new ArrayList<Player>();
		players.add(new Player().setName("P1"));
		players.add(new Player().setName("P2"));
		players.add(new Player().setName("P3"));
		players.add(new Player().setName("P4"));
		players.add(new Player().setName("P5"));
		players.add(new Player().setName("P6"));
		players.add(new Player().setName("P7"));
		players.add(new Player().setName("P8").setActive(false));

		PlayerDataStore mockedPlayerDataStore = Mockito.mock(PlayerDataStore.class);
		Mockito.when(mockedPlayerDataStore.getPlayers()).thenReturn(players);

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42"), new Table().setName("43")));

		TableDataStore tableDataStore = Mockito.mock(TableDataStore.class);
		Mockito.when(tableDataStore.getTables()).thenReturn(tables);

		RoundCalculator roundCalculator = new RoundCalculator(mockedPlayerDataStore,
				Mockito.mock(SkippedRoundsCalculator.class), tableDataStore);
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
	}
}
