package de.jet.tournaments.model.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import de.jet.tournaments.exceptions.TournamentException;
import de.jet.tournaments.model.Player;
import de.jet.tournaments.persistence.PlayerDataStore;

public class PlayerValidator implements ConstraintValidator<PlayerExists, Player>
{
	private final PlayerDataStore playerDataStore;

	@Autowired
	public PlayerValidator(PlayerDataStore playerDataStore)
	{
		this.playerDataStore = Objects.requireNonNull(playerDataStore);
	}

	@Override
	public void initialize(PlayerExists playerExists)
	{

	}

	@Override
	public boolean isValid(Player player, ConstraintValidatorContext context)
	{
		Player existingIngredient = this.playerDataStore.getPlayerByName(player.getName());
		if (existingIngredient == null)
		{
			throw new TournamentException(HttpStatus.BAD_REQUEST,
					"The player '" + player.getName() + "' can not be found.");
		}

		return true;
	}
}
