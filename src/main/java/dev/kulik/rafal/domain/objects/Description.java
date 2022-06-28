package dev.kulik.rafal.domain.objects;

public class Description extends ValueObject<String> {

	public Description(String value) {
		super(value);
	}

	public static Description of(String value){
		return new Description(value);
	}
}
