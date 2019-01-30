package de.jet.tournaments.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jet.tournaments.model.Table;

@Component
public class TableDataStore
{
	private final TableRepository tableRepository;

	@Autowired
	public TableDataStore(TableRepository tableRepository)
	{
		this.tableRepository = Objects.requireNonNull(tableRepository);
	}

	public Table postTable(Table table) throws JsonProcessingException
	{
		Table postedTable = this.tableRepository.save(table);

		return postedTable;
	}

	public Table putTable(Table table) throws JsonProcessingException
	{
		Table putTable = this.tableRepository.save(table);

		return putTable;
	}

	public List<Table> getTables()
	{
		return this.tableRepository.findAll();
	}
}
