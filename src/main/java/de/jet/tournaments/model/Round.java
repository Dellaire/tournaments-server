package de.jet.tournaments.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Round
{
	private String name;
	private List<Match> matches = new ArrayList<Match>();

	public Round()
	{
	}

	public String getName()
	{
		return name;
	}

	public Round setName(String name)
	{
		this.name = name;

		return this;
	}

	public List<Match> getMatches()
	{
		return matches;
	}

	public Round setMatches(List<Match> matches)
	{
		this.matches = matches;

		return this;
	}

	public Round addMatch(Match match)
	{
		this.matches.add(match);

		return this;
	}

	@JsonIgnore
	public Set<Player> getPlayer()
	{
		Set<Player> allPlayer = new HashSet<Player>();
		this.matches.stream()
				.map(match -> Arrays.asList(match.getTeam1().getPlayer1(), match.getTeam1().getPlayer2(),
						match.getTeam2().getPlayer1(), match.getTeam2().getPlayer2()))
				.forEach(player -> allPlayer.addAll(player));

		return allPlayer;
	}
}
