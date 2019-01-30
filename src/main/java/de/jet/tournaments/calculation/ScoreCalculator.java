package de.jet.tournaments.calculation;

import java.util.function.Function;

import de.jet.tournaments.model.PlayerStatistic;

public interface ScoreCalculator extends Function<PlayerStatistic, PlayerStatistic>
{

}
