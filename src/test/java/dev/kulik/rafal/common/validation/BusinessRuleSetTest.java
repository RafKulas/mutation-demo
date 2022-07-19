package dev.kulik.rafal.common.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusinessRuleSetTest {

	@Test
	void givenObjectWithNoRules_whenCheckingIfRuleSetIsEmpty_expectTrue(){
		// given
		var testObject = new TestObject();
		var emptyRuleSet = testObject.rules();

		// when
		var count = emptyRuleSet.getCount();
		var isEmpty = emptyRuleSet.isEmpty();

		// then
		assertEquals(0, count);
		assertTrue(isEmpty);
	}

	@Test
	void givenObjectWithAlwaysFalseRules_whenCheckingIfRuleSetIsEmpty_expectFalse(){
		// given
		var testObject = new TestObject(
				List.of(new BusinessRule<>(null, test -> false))
		);
		var emptyRuleSet = testObject.rules();

		// when
		var count = emptyRuleSet.getCount();
		var isEmpty = emptyRuleSet.isEmpty();

		// then
		assertEquals(1, count);
		assertFalse(isEmpty);
	}

	@Test
	void givenObjectWithAlwaysTrueRules_whenCheckingIfRuleSetIsEmpty_expectFalse(){
		// given
		var testObject = new TestObject(
				List.of(new BusinessRule<>(null, test -> true))
		);
		var emptyRuleSet = testObject.rules();

		// when
		var count = emptyRuleSet.getCount();
		var isEmpty = emptyRuleSet.isEmpty();

		// then
		assertEquals(1, count);
		assertFalse(isEmpty);
	}

	@Test
	void givenObjectWithAlwaysNoRules_whenGettingBrokenRules_expectEmptyList(){
		// given
		var testObject = new TestObject();
		var emptyRuleSet = testObject.rules();

		// when
		var brokenRules = emptyRuleSet.brokenBy(testObject);

		// then
		assertTrue(brokenRules.isEmpty());
	}

	@Test
	void givenObjectWithAlwaysTrueRules_whenGettingBrokenRules_expectNonEmptyList(){
		// given
		var testObject = new TestObject(
				List.of(new BusinessRule<>(null, test -> true))
		);
		var emptyRuleSet = testObject.rules();

		// when
		var count = emptyRuleSet.getCount();
		var isEmpty = emptyRuleSet.isEmpty();

		// then
		assertEquals(1, count);
		assertFalse(isEmpty);
	}

	@Test
	void givenObjectWithAlwaysFalseRules_whenGettingBrokenRules_expectNonEmptyList(){
		// given
		var testObject = new TestObject(
				List.of(new BusinessRule<>(null, test -> false))
		);
		var emptyRuleSet = testObject.rules();

		// when
		var brokenRules = emptyRuleSet.brokenBy(testObject);

		// then
		assertFalse(brokenRules.isEmpty());
	}

	static class TestObject extends ValidateDomainObject<TestObject> {
		private final List<IBusinessRule<TestObject>> testObjectRules;

		public TestObject() {
			this.testObjectRules = emptyList();
		}

		public TestObject(List<IBusinessRule<TestObject>> testObjectRules) {
			this.testObjectRules = testObjectRules;
		}

		@Override
		protected IBusinessRuleSet<TestObject> rules() {
			return new BusinessRuleSet<>(testObjectRules);
		}
	}
}
