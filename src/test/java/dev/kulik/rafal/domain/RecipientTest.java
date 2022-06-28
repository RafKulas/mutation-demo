package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipientTest {

	@Test
	public void givenRecipient_whenAllFieldsAreProperlyFilled_expectValid() {
		// given
		var validRecipient = new Recipient(new Name("Valid Name"), TestData.RecipientTestData.VALID_ADDRESS);

		// when
		var isValid = validRecipient.isValid();

		// expect
		assertTrue(isValid);
	}

	@ParameterizedTest
	@ArgumentsSource(TestData.RecipientTestData.InvalidRecipientArgumentsProvider.class)
	public void GivenRecipient_whenOneFieldIsInvalid_expectNotValidated(Address address, Name name) {
		// given
		var invalidRecipientLine = new Recipient(name, address);

		// when
		var isValid = invalidRecipientLine.isValid();

		// expect
		assertFalse(isValid);
	}
}
