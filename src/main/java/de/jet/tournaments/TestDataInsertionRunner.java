package de.jet.tournaments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.jet.tournaments.controller.PlayerController;
import de.jet.tournaments.controller.TableController;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Table;
import de.jet.tournaments.persistence.PlayerRepository;
import de.jet.tournaments.persistence.TableRepository;

@Component
@Profile("testdata")
public class TestDataInsertionRunner implements CommandLineRunner {

	@Autowired
	private PlayerController playerController;
	
	@Autowired
	private TableController tableController;
	
	@Autowired
	private PlayerRepository playersRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public void run(String... args) throws Exception {

		Player player1 = new Player().setName("Henry").setScore(42).setElo(42).setActive(true);
		Player player2 = new Player().setName("Huy").setScore(42).setElo(42).setActive(true);
		Player player3 = new Player().setName("Hung").setScore(42).setElo(42).setActive(true);
		Player player4 = new Player().setName("Nick").setScore(42).setElo(42).setActive(true);

		this.playersRepository.deleteAll();
		this.playerController.postPlayer(player1);
		this.playerController.postPlayer(player2);
		this.playerController.postPlayer(player3);
		this.playerController.postPlayer(player4);
		
		Table table1 = new Table().setName("Leonhard").setActive(true);
		Table table2 = new Table().setName("Ullrich").setActive(true);
		Table table3 = new Table().setName("Lehmacher").setActive(true);
		Table table4 = new Table().setName("Lettner").setActive(true);
		
		this.tableRepository.deleteAll();
		this.tableController.postTable(table1);
		this.tableController.postTable(table2);
		this.tableController.postTable(table3);
		this.tableController.postTable(table4);
	}
}
