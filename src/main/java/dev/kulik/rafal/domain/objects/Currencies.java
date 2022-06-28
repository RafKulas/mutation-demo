package dev.kulik.rafal.domain.objects;

public enum Currencies {
	USD("USD"),
	EUR("EUR");

	public static Currencies DEFAULT = Currencies.USD;
	private final Currency currency;

	Currencies(String currency) {
		this.currency = new Currency(currency);
	}

	public Currency asCurrency() {
		return this.currency;
	}
}
