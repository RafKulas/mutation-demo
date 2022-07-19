package dev.kulik.rafal.common.validation;

import dev.kulik.rafal.domain.objects.Description;

import java.util.function.Predicate;

public class BusinessRule<T extends ValidatedObject<T>> extends IBusinessRule<T> {
	private final Predicate<T> matchPredicate;

	public BusinessRule(Description description, Predicate<T> matchPredicate) {
		super(description);
		this.matchPredicate = matchPredicate;
	}

	@Override
	public boolean isSatisfiedBy(T item) {
		return matchPredicate.test(item);
	}
}
