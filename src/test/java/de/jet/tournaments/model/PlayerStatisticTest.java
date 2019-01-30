package de.jet.tournaments.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlayerStatisticTest
{
	@Test
	public void copyContainsAllAttributes()
	{
		PlayerStatistic playerStatistic = new PlayerStatistic().setPlayerId("playerId").setPlayerName("playerName")
				.setWins(1).setDefeats(2).setDraws(3).setGoals(4).setCounterGoals(5).setScore(6);

		PlayerStatistic copiedPlayerStatistic = playerStatistic.copy();

		assertThat(copiedPlayerStatistic.getPlayerId(), is("playerId"));
		assertThat(copiedPlayerStatistic.getPlayerName(), is("playerName"));
		assertThat(copiedPlayerStatistic.getWins(), is(1));
		assertThat(copiedPlayerStatistic.getDefeats(), is(2));
		assertThat(copiedPlayerStatistic.getDraws(), is(3));
		assertThat(copiedPlayerStatistic.getGoals(), is(4));
		assertThat(copiedPlayerStatistic.getCounterGoals(), is(5));
		assertThat(copiedPlayerStatistic.getScore(), is(6));
	}
}
