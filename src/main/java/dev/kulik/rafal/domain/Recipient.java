package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.validation.BusinessRule;
import dev.kulik.rafal.common.validation.BusinessRuleSet;
import dev.kulik.rafal.common.validation.IBusinessRule;
import dev.kulik.rafal.common.validation.IBusinessRuleSet;
import dev.kulik.rafal.common.validation.ValidateDomainObject;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Recipient extends ValidateDomainObject<Recipient> implements Cloneable {
	private final Name name;
	private final Address address;

	@Override
	protected IBusinessRuleSet<Recipient> rules() {
		return new BusinessRuleSet<>(List.of(ValidationRules.Name, ValidationRules.Address));
	}

	@Override
	public Recipient clone() {
		return new Recipient(
				new Name(this.getName().getValue()),
				this.getAddress().clone()
		);
	}

	private static class ValidationRules {
		public static IBusinessRule<Recipient> Name =
				new BusinessRule<>(
						Description.of("Recipient name should be specified"),
						recipient -> !Objects.equals(recipient.getName(), null) &&
								!recipient.name.getValue().trim().isEmpty()
				);

		public static IBusinessRule<Recipient> Address =
				new BusinessRule<>(
						Description.of("Address should be valid"),
						recipient -> !Objects.equals(recipient.getAddress(), null) &&
								recipient.getAddress().isValid()
				);
	}
}
