package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.validation.BusinessRule;
import dev.kulik.rafal.common.validation.BusinessRuleSet;
import dev.kulik.rafal.common.validation.IBusinessRule;
import dev.kulik.rafal.common.validation.IBusinessRuleSet;
import dev.kulik.rafal.common.validation.ValidateDomainObject;
import dev.kulik.rafal.domain.objects.Currencies;
import dev.kulik.rafal.domain.objects.Currency;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.common.BaseCurrencyConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Money extends ValidateDomainObject<Money> {
	public static Money ZERO = new Money(BigDecimal.ZERO);

	private final Currency currency;
	private final BigDecimal amount;

	public Money(BigDecimal amount) {
		this(Currencies.DEFAULT.asCurrency(), amount);
	}

	public Money add(Money money) {
		var newAmount = this.amount.add(BaseCurrencyConverter.Convert(money.currency, this.currency, money.amount));
		return new Money(this.currency, newAmount);
	}

	public Money subtract(Money money) {
		var newAmount = this.amount.subtract(BaseCurrencyConverter.Convert(money.currency, this.currency, money.amount));
		return new Money(this.currency, newAmount);
	}

	@Override
	public String toString() {
		return "%s %s".formatted(amount.toPlainString(), currency.getValue());
	}

	@Override
	protected IBusinessRuleSet<Money> rules() {
		return new BusinessRuleSet<>(List.of(ValidationRules.Currency, ValidationRules.Amount));
	}

	private static class ValidationRules {
		public static IBusinessRule<Money> Currency =
				new BusinessRule<>(
						new Description("Currency should be specified"),
						money -> !Objects.equals(money.getCurrency(), null) &&
								!money.currency.getValue().isEmpty());
		public static IBusinessRule<Money> Amount =
				new BusinessRule<>(
						new Description("Amount shouldn't be null"),
						money -> !Objects.equals(money.getAmount(), null));
	}
}
