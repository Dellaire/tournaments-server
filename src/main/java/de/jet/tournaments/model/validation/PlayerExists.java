package de.jet.tournaments.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlayerValidator.class)
public @interface PlayerExists
{
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
