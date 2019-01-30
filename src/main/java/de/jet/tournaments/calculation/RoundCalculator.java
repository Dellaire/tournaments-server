package de.jet.tournaments.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Table;
import de.jet.tournaments.model.Team;
import de.jet.tournaments.persistence.PlayerDataStore;
import de.jet.tournaments.persistence.TableDataStore;

@Component
public class RoundCalculator
{
	private final PlayerDataStore playerDataStore;
	private final SkippedRoundsCalculator skippedRoundsCalculator;
	private final TableDataStore tableDataStore;

	@Autowired
	public RoundCalculator(PlayerDataStore playerDataStore, SkippedRoundsCalculator skippedRoundsCalculator,
			TableDataStore tableDataStore)
	{
		this.playerDataStore = Objects.requireNonNull(playerDataStore);
		this.skippedRoundsCalculator = skippedRoundsCalculator;
		this.tableDataStore = tableDataStore;
	}

	public Round generateNewRound(String tournamentId)
	{
		List<Player> activePlayer = this.playerDataStore.getPlayers().stream().filter(player -> player.isActive())
				.collect(Collectors.toList());

		this.skippedRoundsCalculator.calculateSkippedRounds(activePlayer, tournamentId);

		List<Player> prioritizedPlayer = PlayerPrioritizer.prioritizePlayer(activePlayer);

		Round round = new Round();
		int i = 0;
		while (prioritizedPlayer.size() >= 4)
		{
			Match match = this.generateMatch(prioritizedPlayer);
			match.setId("" + i++);
			round.addMatch(match);
		}

		return this.addTablesToRound(round);
	}

	public Round addTablesToRound(Round round)
	{
		List<Table> activeTables = this.tableDataStore.getTables().stream().filter(table -> table.isActive())
				.collect(Collectors.toList());
		Collections.shuffle(activeTables);

		List<Match> matchesWithoutRounds = round.getMatches().stream().filter(match -> match.getTableName() == null)
				.collect(Collectors.toList());
		for (Match match : matchesWithoutRounds)
		{
			if (activeTables.size() >= 1)
			{
				match.setTableName(activeTables.remove(0).getName());
			}
		}

		return round;
	}

	private Match generateMatch(List<Player> players)
	{
		List<Player> playersToDistribute = new ArrayList<Player>(
				Arrays.asList(players.remove(0), players.remove(0), players.remove(0), players.remove(0)));
		Collections.shuffle(playersToDistribute);

		Team team1 = new Team();
		team1.setPlayer1(playersToDistribute.remove(0));
		team1.setPlayer2(playersToDistribute.remove(0));

		Team team2 = new Team();
		team2.setPlayer1(playersToDistribute.remove(0));
		team2.setPlayer2(playersToDistribute.remove(0));

		Match match = new Match();
		match.setTeam1(team1);
		match.setTeam2(team2);

		return match;
	}
}
