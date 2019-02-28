package de.jet.tournaments.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tournament
{
	@Id
	private String id;

	private String name;
	private List<Round> rounds = new ArrayList<Round>();
	private List<Player> player = new ArrayList<Player>();
	private List<Table> tables = new ArrayList<Table>();

	public Tournament()
	{

	}

	public String getId()
	{
		return id;
	}

	public Tournament setId(String id)
	{
		this.id = id;

		return this;
	}

	public List<Round> getRounds()
	{
		return rounds;
	}

	public Tournament setRounds(List<Round> rounds)
	{
		this.rounds.clear();
		this.rounds.addAll(rounds);

		return this;
	}

	public String getName()
	{
		return name;
	}

	public Tournament setName(String name)
	{
		this.name = name;

		return this;
	}

	public List<Player> getPlayer()
	{
		return this.player;
	}

	public Tournament setPlayer(List<Player> player)
	{
		this.player = player;

		return this;
	}

	public List<Table> getTables()
	{
		return tables;
	}

	public Tournament setTables(List<Table> tables)
	{
		this.tables = tables;

		return this;
	}
}
