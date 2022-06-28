package dev.kulik.rafal.common.validation;

import java.util.List;

interface ValidatedObject<T> {

	List<IBusinessRule<T>> getBrokenRules();

	boolean isValid();
}
