package de.jet.tournaments.calculation;

import java.util.Comparator;

import de.jet.tournaments.model.Player;

public class SkippedRoundsComparator implements Comparator<Player>
{
	@Override
	public int compare(Player player1, Player player2)
	{
		return player2.getSkippedRounds() - player1.getSkippedRounds();
	}
}
