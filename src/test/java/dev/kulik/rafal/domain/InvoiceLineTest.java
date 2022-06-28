package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.ProductName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceLineTest {
	@Test
	public void givenInvoiceLine_whenAllFieldsAreProperlyFilled_expectValid() {
		// given
		var validInvoiceLine = new InvoiceLine(TestData.InvoiceLineTestData.VALID_MONEY, new ProductName("TestProduct"));

		// when
		var isValid = validInvoiceLine.isValid();

		// expect
		assertTrue(isValid);
	}

	@ParameterizedTest
	@ArgumentsSource(TestData.InvoiceLineTestData.InvalidInvoiceLineArgumentProvider.class)
	public void GivenInvoiceLine_whenOneFieldIsInvalid_expectNotValidated(Money money, ProductName name) {
		// given
		var invalidInvoiceLine = new InvoiceLine(money, name);

		// when
		var isValid = invalidInvoiceLine.isValid();

		// expect
		assertFalse(isValid);
	}
}
