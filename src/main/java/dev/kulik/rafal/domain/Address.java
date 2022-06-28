package dev.kulik.rafal.domain;

import dev.kulik.rafal.domain.objects.*;
import dev.kulik.rafal.common.validation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Address extends ValidateDomainObject<Address> {
	private final AddressLine addressLine;
	private final City city;
	private final State state;
	private final Zip zip;

	@Override
	protected IBusinessRuleSet<Address> rules() {
		return new BusinessRuleSet<>(
				List.of(
						ValidationRules.AddressLine,
						ValidationRules.City,
						ValidationRules.Zip,
						ValidationRules.State
				)
		);
	}

	private static class ValidationRules {
		public static IBusinessRule<Address> City =
				new BusinessRule<>(
						Description.of("City should be specified"),
						address -> !address.city.getValue().trim().isEmpty()
				);
		public static IBusinessRule<Address> Zip =
				new BusinessRule<>(
						Description.of("Zip code should be specified"),
						address -> !address.zip.getValue().trim().isEmpty()
				);
		public static IBusinessRule<Address> State =
				new BusinessRule<>(
						Description.of("State should be properly specified"),
						address -> !address.state.getValue().trim().isEmpty()
				);
		public static BusinessRule<Address> AddressLine =
				new BusinessRule<>(
						Description.of("AddressLine should be specified"),
						address -> !address.addressLine.getValue().trim().isEmpty()
				);

	}
}
