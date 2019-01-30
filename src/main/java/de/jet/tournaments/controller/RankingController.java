package de.jet.tournaments.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.jet.tournaments.calculation.PlayerStatisticCalculator;
import de.jet.tournaments.calculation.ScoreCalculator;
import de.jet.tournaments.model.PlayerStatistic;

@RestController
@RequestMapping("/ranking")
public class RankingController
{
	private final PlayerStatisticCalculator playerStatisticCalculator;
	private final ScoreCalculator scoreCalculator;

	@Autowired
	public RankingController(PlayerStatisticCalculator playerStatisticCalculator, ScoreCalculator scoreCalculator)
	{
		this.playerStatisticCalculator = Objects.requireNonNull(playerStatisticCalculator);
		this.scoreCalculator = Objects.requireNonNull(scoreCalculator);
	}

	@RequestMapping("/{tournamentId}")
	public ResponseEntity<List<PlayerStatistic>> getRanking(@PathVariable String tournamentId)
	{
		List<PlayerStatistic> unrankedPlayerStatistic = this.playerStatisticCalculator
				.calculatePlayerStaticstics(tournamentId);

		List<PlayerStatistic> scoredPlayerStatistic = unrankedPlayerStatistic.stream()
				.map(statistic -> this.scoreCalculator.apply(statistic)).collect(Collectors.toList());

		Collections.sort(scoredPlayerStatistic, (playerStatistic1, playerStatistic2) -> playerStatistic2.getScore()
				.compareTo(playerStatistic1.getScore()));

		return new ResponseEntity<List<PlayerStatistic>>(scoredPlayerStatistic, HttpStatus.OK);
	}
}
