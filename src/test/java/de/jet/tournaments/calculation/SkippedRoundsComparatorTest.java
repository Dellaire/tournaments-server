package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.jet.tournaments.model.Player;

public class SkippedRoundsComparatorTest
{
	@Test
	public void sortDescending()
	{
		List<Player> player = Arrays.asList(new Player().setName("player1").setSkippedRounds(2),
				new Player().setName("player2").setSkippedRounds(3));

		player.sort(new SkippedRoundsComparator());

		assertThat(player.get(0).getName(), is("player2"));
		assertThat(player.get(1).getName(), is("player1"));
	}
}
