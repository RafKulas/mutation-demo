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

@Getter
@AllArgsConstructor
public class Recipient extends ValidateDomainObject<Recipient> {
	private final Name name;
	private final Address address;

	@Override
	protected IBusinessRuleSet<Recipient> rules() {
		return new BusinessRuleSet<>(List.of(ValidationRules.Name, ValidationRules.Address));
	}

	private static class ValidationRules {
		public static IBusinessRule<Recipient> Name =
				new BusinessRule<>(
						Description.of("Recipient name should be specified"),
						recipient -> !recipient.getName().getValue().trim().isEmpty()
				);

		public static IBusinessRule<Recipient> Address =
				new BusinessRule<>(
						Description.of("Address should be valid"),
						recipient -> recipient != null && recipient.getAddress().isValid()
				);
	}
}
