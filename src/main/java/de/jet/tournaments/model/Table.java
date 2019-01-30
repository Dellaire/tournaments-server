package de.jet.tournaments.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Table
{
	@Id
	private String id;

	private String name;
	private boolean active = true;

	public Table()
	{

	}

	public String getId()
	{
		return id;
	}

	public Table setId(String id)
	{
		this.id = id;
		
		return this;
	}

	public String getName()
	{
		return name;
	}

	public Table setName(String name)
	{
		this.name = name;
		
		return this;
	}

	public boolean isActive()
	{
		return active;
	}

	public Table setActive(boolean active)
	{
		this.active = active;
		
		return this;
	}
}
