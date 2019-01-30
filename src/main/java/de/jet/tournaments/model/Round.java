package de.jet.tournaments.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

public class Round
{
	private String name;

	@Valid
	private List<Match> matches = new ArrayList<Match>();

	public Round()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Match> getMatches()
	{
		return matches;
	}

	public void setMatches(List<Match> matches)
	{
		this.matches = matches;
	}

	public void addMatch(Match match)
	{
		this.matches.add(match);
	}

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
