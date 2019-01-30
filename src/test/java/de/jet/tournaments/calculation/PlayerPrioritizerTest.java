package de.jet.tournaments.calculation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.jet.tournaments.model.Player;

public class PlayerPrioritizerTest
{
	@Test
	public void playerWithMostSkippedRoundsArePreferred()
	{
		List<Player> player = new ArrayList<Player>();
		player.add(new Player().setName("P1").setSkippedRounds(2));
		player.add(new Player().setName("P2").setSkippedRounds(3));
		player.add(new Player().setName("P3").setSkippedRounds(3));
		player.add(new Player().setName("P4").setSkippedRounds(2));
		player.add(new Player().setName("P5").setSkippedRounds(3));
		player.add(new Player().setName("P6").setSkippedRounds(3));
		player.add(new Player().setName("P7").setSkippedRounds(2));

		List<Player> prioritizedPlayer = PlayerPrioritizer.prioritizePlayer(player);

		assertThat(prioritizedPlayer.get(0).getSkippedRounds(), is(3));
		assertThat(prioritizedPlayer.get(1).getSkippedRounds(), is(3));
		assertThat(prioritizedPlayer.get(2).getSkippedRounds(), is(3));
		assertThat(prioritizedPlayer.get(3).getSkippedRounds(), is(3));
		assertThat(prioritizedPlayer.get(4).getSkippedRounds(), is(2));
		assertThat(prioritizedPlayer.get(5).getSkippedRounds(), is(2));
		assertThat(prioritizedPlayer.get(6).getSkippedRounds(), is(2));
	}
}
