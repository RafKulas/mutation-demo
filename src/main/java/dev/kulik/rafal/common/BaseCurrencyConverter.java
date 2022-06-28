package dev.kulik.rafal.common;

import dev.kulik.rafal.domain.objects.Currency;

import java.math.BigDecimal;

public class BaseCurrencyConverter {
	public static BigDecimal Convert(Currency from, Currency to, BigDecimal amount) {
		return amount;
	}
}
