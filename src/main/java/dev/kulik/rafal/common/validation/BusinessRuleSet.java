package dev.kulik.rafal.common.validation;

import java.util.List;

public class BusinessRuleSet<T extends ValidatedObject<T>> implements IBusinessRuleSet<T> {
	private final List<IBusinessRule<T>> rules;

	public BusinessRuleSet(List<IBusinessRule<T>> rules) {
		this.rules = rules;
	}

	@Override
	public int getCount() {
		return rules.size();
	}

	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}

	@Override
	public List<IBusinessRule<T>> brokenBy(ValidatedObject<T> item) {
		return this.rules.stream()
				.filter(rule -> !rule.isSatisfiedBy((T)item))
				.toList();
	}
}
