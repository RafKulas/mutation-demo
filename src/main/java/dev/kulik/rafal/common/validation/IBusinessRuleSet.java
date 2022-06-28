package dev.kulik.rafal.common.validation;

import java.util.List;

public interface IBusinessRuleSet<T> {
	int getCount();

	boolean isEmpty();

	List<String> getMessages();

	List<IBusinessRule<T>> brokenBy(ValidatedObject<T> item);

	boolean contains(IBusinessRule<T> rule);
}
