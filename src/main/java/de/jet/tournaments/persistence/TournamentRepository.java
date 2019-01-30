package de.jet.tournaments.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.jet.tournaments.model.Tournament;

public interface TournamentRepository extends MongoRepository<Tournament, String>
{
	public Tournament findByName(String name);
}
