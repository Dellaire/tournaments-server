package de.jet.tournaments.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jet.tournaments.model.Match;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.model.Round;
import de.jet.tournaments.model.Table;
import de.jet.tournaments.model.Tournament;

@Component
public class TournamentDataStore
{
	private final TournamentRepository tournamentRepository;
	private final MongoTemplate mongoTemplate;

	@Autowired
	public TournamentDataStore(TournamentRepository tournamentRepository, MongoTemplate mongoTemplate)
	{
		this.tournamentRepository = Objects.requireNonNull(tournamentRepository);
		this.mongoTemplate = Objects.requireNonNull(mongoTemplate);
	}

	public Tournament postTournament(Tournament tournament) throws JsonProcessingException
	{
		Tournament postedTournament = this.tournamentRepository.save(tournament);

		return postedTournament;
	}

	public Tournament headTournamentById(String id)
	{
		return this.tournamentRepository.findOne(id);
	}

	public List<Tournament> getTournaments()
	{
		return this.tournamentRepository.findAll();
	}

	public Tournament getTournamentById(String id)
	{
		return this.tournamentRepository.findOne(id);
	}

	public Tournament getTournamentByName(String name)
	{
		return this.tournamentRepository.findByName(name);
	}

	public List<Player> readPlayer(String tournamentName)
	{
		return this.mongoTemplate.find(Query.query(Criteria.where("name").is(tournamentName)), Tournament.class)
				.stream().findFirst().get().getPlayer();
	}

	public Player addPlayer(String tournamentName, Player player)
	{
		this.mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(tournamentName)),
				new Update().push("player", player), Tournament.class).getUpsertedId();

		return player;
	}

	public List<Table> readTables(String tournamentName)
	{
		return this.mongoTemplate.find(Query.query(Criteria.where("name").is(tournamentName)), Tournament.class)
				.stream().findFirst().get().getTables();
	}

	public Table addTable(String tournamentName, Table table)
	{
		this.mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(tournamentName)),
				new Update().push("tables", table), Tournament.class).getUpsertedId();

		return table;
	}

	public Round addRound(String tournamentId, Round round) throws JsonProcessingException
	{
		this.mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(tournamentId)),
				new Update().push("rounds", round), Tournament.class);

		return round;
	}

	public Round putRound(String tournamentId, Round round)
	{
		this.mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(tournamentId)),
				new Update().pull("rounds", Query.query(Criteria.where("name").is(round.getName()))), Tournament.class);

		this.mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(tournamentId)),
				new Update().push("rounds", round), Tournament.class);

		return round;
	}

	public Round getRoundById(String tournamentName, String roundId)
	{
		MatchOperation matchTournament = Aggregation.match(new Criteria("name").is(tournamentName));
		UnwindOperation unwind = Aggregation.unwind("rounds");
		ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("rounds");
		MatchOperation matchMatch = Aggregation.match(new Criteria("name").is(roundId));

		Aggregation aggregation = Aggregation.newAggregation(matchTournament, unwind, replaceRoot, matchMatch);

		return mongoTemplate.aggregate(aggregation, Tournament.class, Round.class).getUniqueMappedResult();
	}

	/**
	 * <pre>
	 * db.tournament.aggregate
	 *	(
	 * 		[
	 * 			{
	 *				$match:
	 *				{
	 *					"_id": "DYP_08_08_2017"
	 *				}
	 *			},
	 *			{
	 *				$unwind: "$rounds"
	 *			},
	 *			{
	 *				$replaceRoot:
	 *				{
	 *					newRoot: "$rounds"
	 *				}
	 *			},
	 *			{
	 *				$match:
	 *				{
	 *					matches:
	 *					{
	 *						$elemMatch:
	 *						{
	 *							_id: ObjectId("5988903a24aa9a0005c4de77")
	 *						}
	 *					}
	 *				}
	 *			}
	 *		]
	 *	)
	 * </pre>
	 */
	public Round getRoundByMatchId(String tournamentId, String matchId)
	{
		MatchOperation matchTournament = Aggregation.match(new Criteria("id").is(tournamentId));
		UnwindOperation unwind = Aggregation.unwind("rounds");
		ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("rounds");
		MatchOperation matchMatch = Aggregation
				.match(new Criteria("matches").elemMatch(new Criteria("id").is(matchId)));

		Aggregation aggregation = Aggregation.newAggregation(matchTournament, unwind, replaceRoot, matchMatch);

		AggregationResults<Round> round = mongoTemplate.aggregate(aggregation, Tournament.class, Round.class);

		return round.getUniqueMappedResult();
	}

	public Match addMatch(String tournamentId, String roundName, Match match)
	{
		this.mongoTemplate.updateFirst(
				Query.query(
						Criteria.where("id").is(tournamentId).andOperator(Criteria.where("rounds.name").is(roundName))),
				new Update().push("rounds.$.matches", match), Tournament.class);

		return match;
	}

	/**
	 * <pre>
	 * db.tournament.update
	 *	(
	 *		{
	 *			"_id": "DYP",
	 *			"rounds.name": "4"
	 *		},
	 *		{
	 *			$pull:
	 *			{
	 *				"rounds.$.matches":
	 *				{
	 *					"_id": <matchId>
	 *				}
	 *			}
	 *		},
	 *		{
	 *			multi: true
	 *		}
	 *	)
	 * </pre>
	 * 
	 * @throws JsonProcessingException
	 * @throws AmqpException
	 */
	public Match putMatch(String tournamentId, String roundId, Match match) throws JsonProcessingException
	{
		this.mongoTemplate.updateFirst(
				Query.query(
						Criteria.where("id").is(tournamentId).andOperator(Criteria.where("rounds.name").is(roundId))),
				new Update().pull("rounds.$.matches", Query.query(Criteria.where("id").is(match.getId()))),
				Tournament.class);

		this.mongoTemplate.updateFirst(
				Query.query(
						Criteria.where("id").is(tournamentId).andOperator(Criteria.where("rounds.name").is(roundId))),
				new Update().push("rounds.$.matches", match), Tournament.class);

		return match;
	}
}
