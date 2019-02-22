package de.jet.tournaments.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match
{

	private String id;
	private Team team1;
	private Team team2;

	private String team1Score = "---";
	private String team2Score = "---";
	private String tableName;

	public Match()
	{
	}

	public String getId()
	{
		return id;
	}

	public Match setId(String id)
	{
		this.id = id;

		return this;
	}

	public Team getTeam1()
	{
		return team1;
	}

	public Match setTeam1(Team team1)
	{
		this.team1 = team1;

		return this;
	}

	public Team getTeam2()
	{
		return team2;
	}

	public Match setTeam2(Team team2)
	{
		this.team2 = team2;

		return this;
	}

	public String getTeam1Score()
	{
		return team1Score;
	}

	public Match setTeam1Score(String team1Score)
	{
		this.team1Score = team1Score;

		return this;
	}

	public String getTeam2Score()
	{
		return team2Score;
	}

	public Match setTeam2Score(String team2Score)
	{
		this.team2Score = team2Score;

		return this;
	}

	public String getTableName()
	{
		return tableName;
	}

	public Match setTableName(String tableName)
	{
		this.tableName = tableName;

		return this;
	}
}
