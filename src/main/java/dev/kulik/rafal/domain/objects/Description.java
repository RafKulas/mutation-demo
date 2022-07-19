package dev.kulik.rafal.domain.objects;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Description extends ValueObject<String> {

	public Description(String value) {
		super(value);
	}

	public static Description of(String value){
		return new Description(value);
	}
}
