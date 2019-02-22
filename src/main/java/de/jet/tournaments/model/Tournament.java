package de.jet.tournaments.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tournament
{
	@Id
	private String id;

	private String name;
	private List<Round> rounds = new ArrayList<Round>();

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
		List<Match> allMatches = new ArrayList<Match>();
		this.rounds.stream().map(round -> round.getMatches()).forEach(matches -> allMatches.addAll(matches));

		Set<Player> allPlayer = new HashSet<Player>();
		allMatches.stream()
				.map(match -> Arrays.asList(match.getTeam1().getPlayer1(), match.getTeam1().getPlayer2(),
						match.getTeam2().getPlayer1(), match.getTeam2().getPlayer2()))
				.forEach(player -> allPlayer.addAll(player));

		return new ArrayList<Player>(allPlayer);
	}
}
