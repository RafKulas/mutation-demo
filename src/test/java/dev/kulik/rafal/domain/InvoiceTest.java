package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.InvoiceNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceTest {
	@Test
	public void givenInvoice_whenAllFieldsAreProperlyFilledExceptDiscount_expectValid() {
		// given
		var validInvoice = new Invoice(
				new InvoiceNumber("1"),
				TestData.InvoiceTestData.VALID_RECIPIENT,
				TestData.InvoiceTestData.VALID_ADDRESS,
				List.of(TestData.InvoiceTestData.VALID_INVOICE_LINE)
		);

		// when
		var isValid = validInvoice.isValid();

		// expect
		assertTrue(isValid);
	}


	@ParameterizedTest
	@ArgumentsSource(TestData.InvoiceTestData.InvalidInvoiceArgumentProvider.class)
	public void GivenInvoice_whenOneFieldIsInvalid_expectNotValidated(Invoice invoice) {
		// when
		var isValid = invoice.isValid();

		// expect
		assertFalse(isValid);
	}
}
