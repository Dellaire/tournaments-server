package de.jet.tournaments.calculation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.PlayerStatistic;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

@Component
public class PlayerStatisticCalculator
{
	private final TournamentDataStore tournamentDataStore;

	@Autowired
	public PlayerStatisticCalculator(TournamentDataStore tournamentDataStore)
	{
		this.tournamentDataStore = Objects.requireNonNull(tournamentDataStore);
	}

	public List<PlayerStatistic> calculatePlayerStaticstics(String tournamentId)
	{
		Tournament tournament = this.tournamentDataStore.getTournamentById(tournamentId);

		List<PlayerStatistic> playerStatistics = tournament.getPlayer().stream()
				.map(player -> new PlayerStatistic().setPlayerId(player.getId()).setPlayerName(player.getName()))
				.collect(Collectors.toList());

		List<Match> matches = tournament.getRounds().stream().map(round -> round.getMatches())
				.flatMap(matchList -> matchList.stream()).collect(Collectors.toList());

		matches.forEach(match -> this.updatePlayerStatistics(match, playerStatistics));

		return playerStatistics;
	}

	private void updatePlayerStatistics(Match match, List<PlayerStatistic> playerStatistics)
	{
		PlayerStatistic team1Player1Statistic = playerStatistics.stream()
				.filter(playerStatistic -> playerStatistic.getPlayerId().equals(match.getTeam1().getPlayer1().getId()))
				.findFirst().get();
		PlayerStatistic team1Player2Statistic = playerStatistics.stream()
				.filter(playerStatistic -> playerStatistic.getPlayerId().equals(match.getTeam1().getPlayer2().getId()))
				.findFirst().get();
		PlayerStatistic team2Player1Statistic = playerStatistics.stream()
				.filter(playerStatistic -> playerStatistic.getPlayerId().equals(match.getTeam2().getPlayer1().getId()))
				.findFirst().get();
		PlayerStatistic team2Player2Statistic = playerStatistics.stream()
				.filter(playerStatistic -> playerStatistic.getPlayerId().equals(match.getTeam2().getPlayer2().getId()))
				.findFirst().get();

		if (Integer.parseInt(match.getTeam1Score()) > Integer.parseInt(match.getTeam2Score()))
		{
			team1Player1Statistic.addWins(1);
			team1Player2Statistic.addWins(1);
			team2Player1Statistic.addDefeats(1);
			team2Player2Statistic.addDefeats(1);
		}
		else if (Integer.parseInt(match.getTeam1Score()) < Integer.parseInt(match.getTeam2Score()))
		{
			team1Player1Statistic.addDefeats(1);
			team1Player2Statistic.addDefeats(1);
			team2Player1Statistic.addWins(1);
			team2Player2Statistic.addWins(1);
		}
		else
		{
			team1Player1Statistic.addDraws(1);
			team1Player2Statistic.addDraws(1);
			team2Player1Statistic.addDraws(1);
			team2Player2Statistic.addDraws(1);
		}

		team1Player1Statistic.addGoals(Integer.parseInt(match.getTeam1Score()));
		team1Player1Statistic.addCounterGoals(Integer.parseInt(match.getTeam2Score()));

		team1Player2Statistic.addGoals(Integer.parseInt(match.getTeam1Score()));
		team1Player2Statistic.addCounterGoals(Integer.parseInt(match.getTeam2Score()));

		team2Player1Statistic.addGoals(Integer.parseInt(match.getTeam2Score()));
		team2Player1Statistic.addCounterGoals(Integer.parseInt(match.getTeam1Score()));

		team2Player2Statistic.addGoals(Integer.parseInt(match.getTeam2Score()));
		team2Player2Statistic.addCounterGoals(Integer.parseInt(match.getTeam1Score()));
	}
}
