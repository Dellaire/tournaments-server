package de.jet.tournaments.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import de.jet.tournaments.model.Player;

@Component
public interface PlayersRepository extends MongoRepository<Player, String>
{
	public Player findByName(String name);
}