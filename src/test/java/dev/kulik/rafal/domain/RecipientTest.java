package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	public void GivenRecipient_whenOneFieldIsInvalid_expectNotValidated(
			Recipient invalidRecipient, Description failMessage) {
		// when
		var isValid = invalidRecipient.isValid();
		var brokenRule = invalidRecipient.getBrokenRules().get(0);

		// expect
		assertFalse(isValid);
		assertEquals(failMessage, brokenRule.getDescription());
	}
}
