package de.jet.tournaments.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.jet.tournaments.model.Table;
import de.jet.tournaments.persistence.TableDataStore;

@RestController
@RequestMapping(value = "/tables")
public class TableController
{
	private final TableDataStore tableDataStore;

	@Autowired
	public TableController(TableDataStore tableDataStore)
	{
		this.tableDataStore = Objects.requireNonNull(tableDataStore);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Table> postTable(@RequestBody Table table)
	{
		HttpHeaders httpHeaders = new HttpHeaders();

		return new ResponseEntity<Table>(this.tableDataStore.postTable(table), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Table> putTable(@RequestBody Table table)
	{
		HttpHeaders httpHeaders = new HttpHeaders();

		return new ResponseEntity<Table>(this.tableDataStore.putTable(table), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Table>> getTable()
	{
		return new ResponseEntity<List<Table>>(this.tableDataStore.getTables(), HttpStatus.OK);
	}
}
