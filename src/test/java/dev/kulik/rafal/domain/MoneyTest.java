package dev.kulik.rafal.domain;

import dev.kulik.rafal.domain.objects.Currencies;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {
	@Test
	void givenMoneyWithNonZeroValue_whenComparingWithSameValue_expectTrue() {
		// given
		var moneyOriginal = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));
		var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));

		// when
		var equal = Objects.equals(moneyComparing, moneyOriginal);

		//expect
		assertTrue(equal);
	}
	@Test
	void givenMoneyWithNonZeroValue_whenComparingWithDifferentValue_expectFalse() {
		// given
		var moneyOriginal = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));
		var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(43));

		// when
		var equal = Objects.equals(moneyComparing, moneyOriginal);

		//expect
		assertFalse(equal);
	}

	@Test
	void givenMoneyZero_whenComparingWithZero_expectTrue() {
		// given
		var moneyOriginal = Money.ZERO;
		var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(0));

		// when
		var equal = Objects.equals(moneyComparing, moneyOriginal);

		//expect
		assertTrue(equal);
	}

	@Test
	void givenMoney_whenAddingWithSameCurrency_expectSumOfAmounts() {
		// given
		var money1 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(10));
		var money2 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(20));

		var expected = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(30));

		// when
		var sum = money1.add(money2);

		//expect
		assertEquals(expected, sum);
	}

	@Test
	void givenMoney_whenAddingWithDifferenceCurrency_expectCurrencyOfFirstOne() {
		// given
		var money1 = new Money(Currencies.EUR.asCurrency(), new BigDecimal(10));
		var money2 = new Money(Currencies.USD.asCurrency(), new BigDecimal(20));

		var expected = Currencies.EUR.asCurrency();

		// when
		var sum = money1.add(money2);

		//expect
		assertEquals(expected, sum.getCurrency());
	}

	@Test
	void givenMoney_whenSubtractingWithSameCurrency_expectSumOfAmounts() {
		// given
		var money1 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(20));
		var money2 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(15));

		// when
		var sum = money1.subtract(money2);

		//expect
		assertEquals(Money.class, sum.getClass());
	}

	@Test
	void givenMoney_whenSubtractingWithDifferenceCurrency_expectCurrencyOfFirstOne() {
		// given
		var money1 = new Money(Currencies.EUR.asCurrency(), new BigDecimal(20));
		var money2 = new Money(Currencies.USD.asCurrency(), new BigDecimal(15));

		// when
		var diff = money1.subtract(money2);

		//expect
		assertEquals(Money.class, diff.getClass());
	}
}
