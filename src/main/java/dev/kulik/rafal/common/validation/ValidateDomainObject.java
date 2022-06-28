package dev.kulik.rafal.common.validation;

import java.util.List;

public abstract class ValidateDomainObject<T> implements ValidatedObject<T> {
	@Override
	public boolean isValid() {
		return getBrokenRules().isEmpty();
	}

	@Override
	public List<IBusinessRule<T>> getBrokenRules() {
		return this.rules().brokenBy(this);
	}

	protected abstract IBusinessRuleSet<T> rules();
}
