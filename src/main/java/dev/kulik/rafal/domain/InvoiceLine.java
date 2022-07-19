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
import java.util.Objects;

@Getter
@AllArgsConstructor
public class InvoiceLine extends ValidateDomainObject<InvoiceLine> implements Cloneable {
	private final Money money;
	private final ProductName productName;

	@Override
	protected IBusinessRuleSet<InvoiceLine> rules() {
		return new BusinessRuleSet<>(List.of(ValidationRules.Money, ValidationRules.ProductName));
	}

	@Override
	public InvoiceLine clone() {
		return new InvoiceLine(
				new Money(this.getMoney().getCurrency(), this.getMoney().getAmount()),
				new ProductName(this.getProductName().getValue())
		);
	}

	private static class ValidationRules {
		public static IBusinessRule<InvoiceLine> ProductName =
				new BusinessRule<>(
						Description.of("Product name should be specified"),
						line -> !Objects.equals(line.getProductName(), null) &&
								!line.productName.getValue().trim().isEmpty());
		public static IBusinessRule<InvoiceLine> Money =
				new BusinessRule<>(
						Description.of("Money should be valid"),
						line -> !Objects.equals(line.getMoney(), null) &&
								line.money != null && line.money.isValid());
	}
}
