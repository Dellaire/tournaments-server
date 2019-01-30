package de.jet.tournaments.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tournament
{
	@Id
	private String id;

	private String name;

	@Valid
	private List<Round> rounds = new ArrayList<Round>();

	public Tournament()
	{

	}

	public String getId()
	{
		return id;
	}

	public List<Round> getRounds()
	{
		return rounds;
	}

	public void setRounds(List<Round> rounds)
	{
		this.rounds.clear();
		this.rounds.addAll(rounds);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
