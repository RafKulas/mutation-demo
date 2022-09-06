package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AddressTest {
	@Test
	public void givenAddressWithAllFieldsValid_whenValidating_expectTrue() {
		// given
		var validAddress = TestData.AddressTestData.validAddress;

		// when
		var isValid = validAddress.isValid();

		// expect
		assertTrue(isValid);
	}

	@ParameterizedTest
	@ArgumentsSource(TestData.AddressTestData.InvalidAddressesArgumentsProvider.class)
	public void givenAddressWithAtLeastOneFieldNotValid_whenValidating_expectFalse(Address invalidAddress){
		// when
		var isValid = invalidAddress.isValid();

		// expect
		assertFalse(isValid);
	}
}
