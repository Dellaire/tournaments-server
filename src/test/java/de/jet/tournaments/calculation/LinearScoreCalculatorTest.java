package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.jet.tournaments.model.PlayerStatistic;

public class LinearScoreCalculatorTest
{
	@Test
	public void testRankingCalculation()
	{
		PlayerStatistic playerStatistic = new PlayerStatistic().setWins(1).setDefeats(2).setDraws(3).setGoals(4)
				.setCounterGoals(5);

		PlayerStatistic playerStatisticWithScore = new LinearScoreCalculator().apply(playerStatistic);

		assertThat(playerStatisticWithScore.getScore(), is(105));
	}
}
