package de.jet.tournaments.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public Table postTable(Table table)
	{
		Table postedTable = this.tableRepository.save(table);

		return postedTable;
	}

	public Table putTable(Table table)
	{
		Table putTable = this.tableRepository.save(table);

		return putTable;
	}

	public List<Table> getTables()
	{
		return this.tableRepository.findAll();
	}
}
