package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.InvoiceNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceTest {

	private static final Invoice validInvoice = Invoice.builder()
			.invoiceNumber(new InvoiceNumber("1"))
			.addressToBill(TestData.InvoiceTestData.VALID_ADDRESS)
			.recipient(TestData.InvoiceTestData.VALID_RECIPIENT)
			.lines(List.of(TestData.InvoiceTestData.VALID_INVOICE_LINE))
			.build();

	@Test
	public void givenInvoice_whenAllFieldsAreProperlyFilledExceptDiscount_expectValid() {
		// when
		var isValid = validInvoice.isValid();

		// expect
		assertTrue(isValid);
	}

	@Test
	public void givenValidInvoice_whenAddingValidInvoiceLine_expectValid() {
		// given
		var valid = validInvoice.clone();

		// when
		valid.attachInvoiceLine(TestData.InvoiceTestData.VALID_INVOICE_LINE);
		var isValid = validInvoice.isValid();

		// expect
		assertTrue(isValid);
	}

	@Test
	public void givenValidInvoice_whenAddingInvalidInvoiceLine_expectInvalid() {
		// given
		var valid = validInvoice.clone();

		// when
		valid.attachInvoiceLine(TestData.InvoiceTestData.INVALID_INVOICE_LINE);
		var isValid = valid.isValid();

		// expect
		assertFalse(isValid);
	}

	@ParameterizedTest
	@ArgumentsSource(TestData.InvoiceTestData.InvalidInvoiceArgumentProvider.class)
	public void GivenInvoice_whenOneFieldIsInvalid_expectNotValidated(
			Invoice invalidInvoice, Description failMessage) {
		// when
		var isValid = invalidInvoice.isValid();
		var brokenRule = invalidInvoice.getBrokenRules().get(0);

		// expect
		assertFalse(isValid);
		assertEquals(failMessage, brokenRule.getDescription());
	}
}
