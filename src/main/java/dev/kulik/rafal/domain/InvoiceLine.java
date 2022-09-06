package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.validation.BusinessRule;
import dev.kulik.rafal.common.validation.BusinessRuleSet;
import dev.kulik.rafal.common.validation.IBusinessRule;
import dev.kulik.rafal.common.validation.IBusinessRuleSet;
import dev.kulik.rafal.common.validation.ValidateDomainObject;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.ProductName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InvoiceLine extends ValidateDomainObject<InvoiceLine> {
	private final Money money;
	private final ProductName productName;

	@Override
	protected IBusinessRuleSet<InvoiceLine> rules() {
		return new BusinessRuleSet<>(List.of(ValidationRules.Money, ValidationRules.ProductName));
	}

	private static class ValidationRules {
		public static IBusinessRule<InvoiceLine> ProductName =
				new BusinessRule<>(
						Description.of("Product name should be specified"),
						line -> !line.productName.getValue().trim().isEmpty());
		public static IBusinessRule<InvoiceLine> Money =
				new BusinessRule<>(
						Description.of("Money should be valid"),
						line -> line.money != null && line.money.isValid());
	}
}
