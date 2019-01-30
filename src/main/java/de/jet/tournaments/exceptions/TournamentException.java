package de.jet.tournaments.exceptions;

import java.util.Objects;

import org.springframework.http.HttpStatus;

public class TournamentException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private final String message;
	private final HttpStatus httpStatus;

	public TournamentException(HttpStatus httpStatus, String message)
	{
		this.httpStatus = Objects.requireNonNull(httpStatus);
		this.message = Objects.requireNonNull(message);
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getMessage()
	{
		return message;
	}

	public HttpStatus getHttpStatus()
	{
		return httpStatus;
	}
}
