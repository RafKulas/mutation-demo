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
	public List<String> getMessages() {
		return rules.stream().map(rule -> rule.description.getValue()).toList();
	}

	@Override
	public List<IBusinessRule<T>> brokenBy(ValidatedObject<T> item) {
		return this.rules.stream()
				.filter(rule -> !rule.isSatisfiedBy((T)item))
				.toList();
	}

	@Override
	public boolean contains(IBusinessRule<T> rule) {
		return rules.contains(rule);
	}
}
