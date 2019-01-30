package de.jet.tournaments.model;

public class PlayerStatistic
{
	private String playerId;
	private String playerName;
	private Integer wins = 0;
	private Integer defeats = 0;
	private Integer draws = 0;
	private Integer goals = 0;
	private Integer counterGoals = 0;
	private Integer score = 0;

	public PlayerStatistic()
	{

	}

	public PlayerStatistic copy()
	{
		return new PlayerStatistic().setPlayerId(this.playerId).setPlayerName(this.playerName).setWins(this.wins)
				.setDefeats(this.defeats).setDraws(this.draws).setGoals(this.goals).setCounterGoals(this.counterGoals)
				.setScore(this.score);
	}

	public String getPlayerId()
	{
		return playerId;
	}

	public PlayerStatistic setPlayerId(String playerName)
	{
		this.playerId = playerName;

		return this;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public PlayerStatistic setPlayerName(String playerName)
	{
		this.playerName = playerName;

		return this;
	}

	public Integer getWins()
	{
		return wins;
	}

	public PlayerStatistic setWins(Integer wins)
	{
		this.wins = wins;

		return this;
	}

	public PlayerStatistic addWins(Integer wins)
	{
		this.wins += wins;

		return this;
	}

	public Integer getDefeats()
	{
		return defeats;
	}

	public PlayerStatistic setDefeats(Integer defeats)
	{
		this.defeats = defeats;

		return this;
	}

	public PlayerStatistic addDefeats(Integer defeats)
	{
		this.defeats += defeats;

		return this;
	}

	public Integer getDraws()
	{
		return draws;
	}

	public PlayerStatistic setDraws(Integer draws)
	{
		this.draws = draws;

		return this;
	}

	public PlayerStatistic addDraws(Integer draws)
	{
		this.draws += draws;

		return this;
	}

	public Integer getGoals()
	{
		return goals;
	}

	public PlayerStatistic setGoals(Integer goals)
	{
		this.goals = goals;

		return this;
	}

	public PlayerStatistic addGoals(Integer goals)
	{
		this.goals += goals;

		return this;
	}

	public Integer getCounterGoals()
	{
		return counterGoals;
	}

	public PlayerStatistic setCounterGoals(Integer counterGoals)
	{
		this.counterGoals = counterGoals;

		return this;
	}

	public PlayerStatistic addCounterGoals(Integer counterGoals)
	{
		this.counterGoals += counterGoals;

		return this;
	}

	public Integer getScore()
	{
		return score;
	}

	public PlayerStatistic setScore(Integer score)
	{
		this.score = score;

		return this;
	}

	public PlayerStatistic addScore(Integer score)
	{
		this.score += score;

		return this;
	}
}
