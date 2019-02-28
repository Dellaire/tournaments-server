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
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

public class GenerateNewRoundTest
{
	@Test
	public void generateNewRoundWithNoSkip()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1"));
		player.add(new Player().setName("P2"));
		player.add(new Player().setName("P3"));
		player.add(new Player().setName("P4"));
		player.add(new Player().setName("P5"));
		player.add(new Player().setName("P6"));
		player.add(new Player().setName("P7"));
		player.add(new Player().setName("P8"));

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table()));

		Tournament tournament = new Tournament();
		tournament.setPlayer(player);
		tournament.setTables(tables);

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentByName(Mockito.anyString())).thenReturn(tournament);

		RoundCalculator roundCalculator = new RoundCalculator(mockedTournamentDataStore,
				Mockito.mock(SkippedRoundsCalculator.class));
		Round round = roundCalculator.generateNewRound("42");

		assertThat(round.getMatches().size(), is(2));
	}

	@Test
	public void generateNewRoundWithSkip()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1"));
		player.add(new Player().setName("P2"));
		player.add(new Player().setName("P3"));
		player.add(new Player().setName("P4"));
		player.add(new Player().setName("P5"));
		player.add(new Player().setName("P6"));
		player.add(new Player().setName("P7"));

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table()));

		Tournament tournament = new Tournament();
		tournament.setPlayer(player);
		tournament.setTables(tables);

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentByName(Mockito.anyString())).thenReturn(tournament);

		RoundCalculator roundCalculator = new RoundCalculator(mockedTournamentDataStore,
				Mockito.mock(SkippedRoundsCalculator.class));
		Round round = roundCalculator.generateNewRound("42");

		assertThat(round.getMatches().size(), is(1));
	}

	@Test
	public void notEnoghTables()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1"));
		player.add(new Player().setName("P2"));
		player.add(new Player().setName("P3"));
		player.add(new Player().setName("P4"));
		player.add(new Player().setName("P5"));
		player.add(new Player().setName("P6"));
		player.add(new Player().setName("P7"));
		player.add(new Player().setName("P8"));

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42")));

		Tournament tournament = new Tournament();
		tournament.setPlayer(player);
		tournament.setTables(tables);

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentByName(Mockito.anyString())).thenReturn(tournament);

		RoundCalculator roundCalculator = new RoundCalculator(mockedTournamentDataStore,
				Mockito.mock(SkippedRoundsCalculator.class));
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
		assertThat(matchesWithTables.get(0).getTableName(), is("42"));
	}

	@Test
	public void ignoreInactiveTables()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1"));
		player.add(new Player().setName("P2"));
		player.add(new Player().setName("P3"));
		player.add(new Player().setName("P4"));
		player.add(new Player().setName("P5"));
		player.add(new Player().setName("P6"));
		player.add(new Player().setName("P7"));
		player.add(new Player().setName("P8"));

		List<Table> tables = new ArrayList<Table>(
				Arrays.asList(new Table().setName("42"), new Table().setName("43").setActive(false)));

		Tournament tournament = new Tournament();
		tournament.setPlayer(player);
		tournament.setTables(tables);

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentByName(Mockito.anyString())).thenReturn(tournament);

		RoundCalculator roundCalculator = new RoundCalculator(mockedTournamentDataStore,
				Mockito.mock(SkippedRoundsCalculator.class));
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
		assertThat(matchesWithTables.get(0).getTableName(), is("42"));
	}

	@Test
	public void ignoreInactivePlayer()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1"));
		player.add(new Player().setName("P2"));
		player.add(new Player().setName("P3"));
		player.add(new Player().setName("P4"));
		player.add(new Player().setName("P5"));
		player.add(new Player().setName("P6"));
		player.add(new Player().setName("P7"));
		player.add(new Player().setName("P8").setActive(false));

		List<Table> tables = new ArrayList<Table>(Arrays.asList(new Table().setName("42"), new Table().setName("43")));

		Tournament tournament = new Tournament();
		tournament.setPlayer(player);
		tournament.setTables(tables);

		TournamentDataStore mockedTournamentDataStore = Mockito.mock(TournamentDataStore.class);
		Mockito.when(mockedTournamentDataStore.getTournamentByName(Mockito.anyString())).thenReturn(tournament);

		RoundCalculator roundCalculator = new RoundCalculator(mockedTournamentDataStore,
				Mockito.mock(SkippedRoundsCalculator.class));
		Round round = roundCalculator.generateNewRound("42");

		List<Match> matchesWithTables = round.getMatches().stream().filter(match -> match.getTableName() != null)
				.collect(Collectors.toList());

		assertThat(matchesWithTables.size(), is(1));
	}
}
