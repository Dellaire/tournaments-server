package de.jet.tournaments.model;

import java.util.Objects;

public class RoundDTO
{
	private final String tournamentId;
	private final Round round;
	private final Operation operation;

	public RoundDTO(String tournamentId, Round round, Operation operation)
	{
		this.tournamentId = Objects.requireNonNull(tournamentId);
		this.round = Objects.requireNonNull(round);
		this.operation = Objects.requireNonNull(operation);
	}

	public enum Operation
	{
		ADD, REMOVE, UPDATE
	}

	public String getTournamentId()
	{
		return tournamentId;
	}

	public Round getRound()
	{
		return round;
	}

	public Operation getOperation()
	{
		return operation;
	}
}
