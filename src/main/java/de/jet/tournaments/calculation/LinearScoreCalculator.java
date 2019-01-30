package de.jet.tournaments.calculation;

import org.springframework.stereotype.Component;

import de.jet.tournaments.model.PlayerStatistic;

@Component
public class LinearScoreCalculator implements ScoreCalculator
{
	private final static Integer WIN_MULTIPLICATOR = 100;
	private final static Integer DEFEAT_MULTIPLICATOR = -10;
	private final static Integer DRAW_MULTIPLICATOR = 10;
	private final static Integer GOAL_MULTIPLICATOR = 5;
	private final static Integer COUNTER_GOAL_MULTIPLICATOR = -5;

	@Override
	public PlayerStatistic apply(PlayerStatistic playerStatistic)
	{
		PlayerStatistic mappedPlayerStatistic = playerStatistic.copy();

		mappedPlayerStatistic.setScore((mappedPlayerStatistic.getWins() * WIN_MULTIPLICATOR)
				+ (mappedPlayerStatistic.getDefeats() * DEFEAT_MULTIPLICATOR)
				+ (mappedPlayerStatistic.getDraws() * DRAW_MULTIPLICATOR)
				+ (mappedPlayerStatistic.getGoals() * GOAL_MULTIPLICATOR)
				+ (mappedPlayerStatistic.getCounterGoals() * COUNTER_GOAL_MULTIPLICATOR));

		return mappedPlayerStatistic;
	}
}