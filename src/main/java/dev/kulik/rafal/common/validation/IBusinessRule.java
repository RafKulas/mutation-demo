package dev.kulik.rafal.common.validation;

import dev.kulik.rafal.domain.objects.Description;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class IBusinessRule<T> {
	protected Description description;

	abstract boolean isSatisfiedBy(T item);
}
