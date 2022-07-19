package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.Currencies;
import dev.kulik.rafal.domain.objects.Currency;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {

	@Test
	void givenMoneyAndItsStringRepresentation_whenComparingToString_expectTrue() {
		// given
		var money = new Money(Currencies.EUR.asCurrency(), new BigDecimal(20.5));
		var expectedStringRepresentation = "20.5 EUR";

		// when
		var stringRepresentation = money.toString();

		//expect
		assertEquals(expectedStringRepresentation, stringRepresentation);
	}

	@Nested
	class Operations {

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

			var expected = new Money(Currencies.EUR.asCurrency(), new BigDecimal(30));

			// when
			var sum = money1.add(money2);

			//expect
			assertEquals(expected, sum);
		}

		@Test
		void givenMoney_whenSubtractingWithSameCurrency_expectSumOfAmounts() {
			// given
			var money1 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(20));
			var money2 = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(15));

			var expected = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(5));

			// when
			var diff = money1.subtract(money2);

			//expect
			assertEquals(expected, diff);
		}

		@Test
		void givenMoney_whenSubtractingWithDifferenceCurrency_expectCurrencyOfFirstOne() {
			// given
			var money1 = new Money(Currencies.EUR.asCurrency(), new BigDecimal(20));
			var money2 = new Money(Currencies.USD.asCurrency(), new BigDecimal(15));

			var expected = new Money(Currencies.EUR.asCurrency(), new BigDecimal(5));

			// when
			var diff = money1.subtract(money2);

			//expect
			assertEquals(expected, diff);
		}
	}

	@Nested
	class Equals {

		@Test
		void givenMoneyWithNonZeroValue_whenComparingWithSameValue_expectTrue() {
			// given
			var moneyOriginal = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));
			var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));

			// when
			var equals = Objects.equals(moneyComparing, moneyOriginal);

			//expect
			assertTrue(equals);
		}

		@Test
		void givenMoneyWithNonZeroValue_whenComparingWithDifferentValue_expectFalse() {
			// given
			var moneyOriginal = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(42));
			var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(43));

			// when
			var equals = Objects.equals(moneyComparing, moneyOriginal);

			//expect
			assertFalse(equals);
		}

		@Test
		void givenMoneyZero_whenComparingWithZero_expectTrue() {
			// given
			var moneyOriginal = Money.ZERO;
			var moneyComparing = new Money(Currencies.DEFAULT.asCurrency(), new BigDecimal(0));

			// when
			var equals = Objects.equals(moneyComparing, moneyOriginal);

			//expect
			assertTrue(equals);
		}

		@Test
		void givenMoney_whenComparingWithDifferenceObject_expectFalse() {
			// given
			var money = new Money(Currencies.EUR.asCurrency(), new BigDecimal(20));
			var object = new Object();

			// when
			var equals = money.equals(object);

			//expect
			assertFalse(equals);
		}
	}

	@Nested
	class Validation {

		@Test
		void givenMoneyWithAllFieldsValid_whenValidating_expectTrue() {
			// given
			var validMoney = new Money(
					Currencies.USD.asCurrency(),
					new BigDecimal(10)
			);

			// when
			var isValid = validMoney.isValid();

			// expect
			assertTrue(isValid);
		}

		@Test
		void givenMoneyWithNullFieldsValid_whenValidating_expectTrue() {
			// given
			var invalidMoney = new Money(null, null);

			// when
			var isValid = invalidMoney.isValid();

			// expect
			assertFalse(isValid);
		}

		@Test
		void givenMoneyWithAnyFieldInvalid_whenValidating_expectFalse() {
			// given
			var validMoney = TestData.MoneyTestData.invalidMoney;

			// when
			var isValid = validMoney.isValid();

			// expect
			assertFalse(isValid);
		}
	}
}
