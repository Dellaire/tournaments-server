package de.jet.tournaments.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.jet.tournaments.model.Player;

public class PlayerPrioritizer {

	public static List<Player> prioritizePlayer(List<Player> playerToBePrioritized) {

		Integer maxSkippedRounds = playerToBePrioritized.stream()
				.max((player1, player2) -> ((Integer) player1.getSkippedRounds()).compareTo(player2.getSkippedRounds()))
				.map(player -> player.getSkippedRounds()).get();

		List<Player> playerWithMaxSkippedRounds = playerToBePrioritized.stream()
				.filter(player -> player.getSkippedRounds() == maxSkippedRounds).collect(Collectors.toList());
		Collections.shuffle(playerWithMaxSkippedRounds);
		List<Player> playerWithNotMaxSkippedRounds = playerToBePrioritized.stream()
				.filter(player -> player.getSkippedRounds() < maxSkippedRounds).collect(Collectors.toList());

		List<Player> prioritizedPlayer = new ArrayList<Player>();
		prioritizedPlayer.addAll(playerWithMaxSkippedRounds);
		prioritizedPlayer.addAll(playerWithNotMaxSkippedRounds);

		return prioritizedPlayer;
	}
}
