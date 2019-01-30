package de.jet.tournaments.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Player;

@Component
public class PlayerDataStore
{
	private final PlayersRepository playerRepository;

	public PlayerDataStore(PlayersRepository playerRepository)
	{
		this.playerRepository = Objects.requireNonNull(playerRepository);
	}

	public Player postPlayer(Player player)
	{
		return this.playerRepository.save(player);
	}

	public Player putPlayer(Player player)
	{
		return this.playerRepository.save(player);
	}

	public List<Player> getPlayers()
	{
		return this.playerRepository.findAll();
	}

	public Player getPlayerById(String id)
	{
		return this.playerRepository.findOne(id);
	}

	public Player getPlayerByName(String name)
	{
		return this.playerRepository.findByName(name);
	}
}
