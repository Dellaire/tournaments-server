package de.jet.tournaments.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;

import de.jet.tournaments.model.Table;

@Controller
public interface TableRepository extends MongoRepository<Table, String>
{
	public Table findByName(String name);
}