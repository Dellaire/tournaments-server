package de.jet.tournaments.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Tournament;
import de.jet.tournaments.persistence.TournamentDataStore;

@Component
public class SkippedRoundsCalculator
{
	private final TournamentDataStore tournamentDataStore;

	@Autowired
	public SkippedRoundsCalculator(TournamentDataStore tournamentDataStore)
	{
		this.tournamentDataStore = Objects.requireNonNull(tournamentDataStore);
	}

	public List<Player> calculateSkippedRounds(List<Player> activePlayer, String tournamentId)
	{
		Tournament tournament = this.tournamentDataStore.getTournamentById(tournamentId);

		List<Player> multiplePlayerOfTournament = new ArrayList<Player>();
		if (tournament != null)
		{
			multiplePlayerOfTournament = tournament.getRounds().stream().map(round -> round.getPlayer())
					.flatMap(multiplePlayer -> multiplePlayer.stream()).collect(Collectors.toList());
		}

		int numberOfRounds = 0;
		if (tournament != null)
		{
			numberOfRounds = tournament.getRounds().size();
		}

		for (Player player : activePlayer)
		{
			int matchesOfPlayer = (int) multiplePlayerOfTournament.stream().filter(aPlayer -> aPlayer.equals(player))
					.count();
			player.setSkippedRounds(numberOfRounds - matchesOfPlayer);
		}

		return activePlayer.stream().filter(player -> player.getSkippedRounds() > 0).collect(Collectors.toList());
	}
}