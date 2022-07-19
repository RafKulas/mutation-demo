package dev.kulik.rafal.common;

import dev.kulik.rafal.common.validation.BusinessRule;
import dev.kulik.rafal.common.validation.BusinessRuleSet;
import dev.kulik.rafal.common.validation.IBusinessRuleSet;
import dev.kulik.rafal.domain.Address;
import dev.kulik.rafal.domain.Invoice;
import dev.kulik.rafal.domain.InvoiceLine;
import dev.kulik.rafal.domain.Money;
import dev.kulik.rafal.domain.Recipient;
import dev.kulik.rafal.domain.objects.AddressLine;
import dev.kulik.rafal.domain.objects.City;
import dev.kulik.rafal.domain.objects.Currencies;
import dev.kulik.rafal.domain.objects.Currency;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.InvoiceNumber;
import dev.kulik.rafal.domain.objects.Name;
import dev.kulik.rafal.domain.objects.ProductName;
import dev.kulik.rafal.domain.objects.State;
import dev.kulik.rafal.domain.objects.Zip;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class TestData {
	public static class MoneyTestData {
		public static final Money validMoney = new Money(
				Currencies.USD.asCurrency(),
				new BigDecimal(10)
		);

		public static final Money invalidMoney = new Money(
				Currencies.USD.asCurrency(),
				null
		);
	}

	public static class AddressTestData {
		public static final Address validAddress = new Address(
				new AddressLine("Slowackiego"),
				new City("Gdansk"),
				new State("pomorskie"),
				new Zip("80-298")
		);

		public static class InvalidAddressesArgumentsProvider implements ArgumentsProvider {

			private static final Description INVALID_CITY_MESSAGE = Description.of("City should be specified");
			private static final Description INVALID_ZIP_MESSAGE = Description.of("Zip code should be specified");
			private static final Description INVALID_STATE_MESSAGE = Description.of("State should be properly specified");
			private static final Description INVALID_ADDRESS_LINE_MESSAGE = Description.of("AddressLine should be specified");

			@Override
			public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
				return Stream.of(
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										new State("valid state"),
										null
								),
								INVALID_ZIP_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										new State("valid state"),
										new Zip("")
								),
								INVALID_ZIP_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										new State("valid state"),
										new Zip(" ")
								),
								INVALID_ZIP_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										null,
										new Zip("80-233")
								),
								INVALID_STATE_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										new State(" "),
										new Zip("80-233")
								),
								INVALID_STATE_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City("valid city"),
										new State(""),
										new Zip("80-233")
								),
								INVALID_STATE_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										null,
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_CITY_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City(""),
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_CITY_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine("valid address line"),
										new City(" "),
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_CITY_MESSAGE
						),
						Arguments.of(
								new Address(
										null,
										new City("valid city"),
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_ADDRESS_LINE_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine(""),
										new City("valid city"),
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_ADDRESS_LINE_MESSAGE
						),
						Arguments.of(
								new Address(
										new AddressLine(" "),
										new City("valid city"),
										new State("valid state"),
										new Zip("80-233")
								),
								INVALID_ADDRESS_LINE_MESSAGE
						)
				);
			}
		}
	}

	public static class RecipientTestData {
		public static Address VALID_ADDRESS = Stubs.AlwaysValidAddress.get();
		public static Address INVALID_ADDRESS = Stubs.AlwaysInvalidAddress.get();

		public static class InvalidRecipientArgumentsProvider implements ArgumentsProvider {

			private static final Description INVALID_NAME_MESSAGE = Description.of("Recipient name should be specified");
			private static final Description INVALID_ADDRESS_MESSAGE = Description.of("Address should be valid");

			@Override
			public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
				return Stream.of(
						Arguments.of(
								new Recipient(null, VALID_ADDRESS),
								INVALID_NAME_MESSAGE
						),
						Arguments.of(
								new Recipient(new Name(""), VALID_ADDRESS),
								INVALID_NAME_MESSAGE
						),
						Arguments.of(
								new Recipient(new Name(" "), VALID_ADDRESS),
								INVALID_NAME_MESSAGE
						),
						Arguments.of(
								new Recipient(new Name("Valid Name"), null),
								INVALID_ADDRESS_MESSAGE
						),
						Arguments.of(
								new Recipient(new Name("Valid Name"), INVALID_ADDRESS),
								INVALID_ADDRESS_MESSAGE
						)
				);
			}
		}
	}

	public static class InvoiceLineTestData {
		public static Money VALID_MONEY = Stubs.AlwaysValidMoney.get();
		public static Money INVALID_MONEY = Stubs.AlwaysInvalidMoney.get();

		public static class InvalidInvoiceLineArgumentProvider implements ArgumentsProvider {

			private static final Description INVALID_MONEY_MESSAGE =
					Description.of("Money should be valid");
			private static final Description INVALID_PRODUCT_NAME_MESSAGE =
					Description.of("Product name should be specified");

			@Override
			public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
				return Stream.of(
						Arguments.of(
								new InvoiceLine(null, new ProductName("Valid product name")),
								INVALID_MONEY_MESSAGE
						),
						Arguments.of(
								new InvoiceLine(INVALID_MONEY, new ProductName("Valid product name")),
								INVALID_MONEY_MESSAGE
						),
						Arguments.of(
								new InvoiceLine(VALID_MONEY, null),
								INVALID_PRODUCT_NAME_MESSAGE
						),
						Arguments.of(
								new InvoiceLine(VALID_MONEY, new ProductName(" ")),
								INVALID_PRODUCT_NAME_MESSAGE
						),
						Arguments.of(
								new InvoiceLine(VALID_MONEY, new ProductName("")),
								INVALID_PRODUCT_NAME_MESSAGE
						)
				);
			}
		}
	}

	public static class InvoiceTestData {
		public static Recipient VALID_RECIPIENT = Stubs.AlwaysValidRecipient.get();
		public static Recipient INVALID_RECIPIENT = Stubs.AlwaysInvalidRecipient.get();
		public static Address VALID_ADDRESS = Stubs.AlwaysValidAddress.get();
		public static Address INVALID_ADDRESS = Stubs.AlwaysInvalidAddress.get();
		public static InvoiceLine VALID_INVOICE_LINE = Stubs.AlwaysValidInvoiceLine.get();
		public static InvoiceLine INVALID_INVOICE_LINE = Stubs.AlwaysInvalidInvoiceLine.get();

		public static class InvalidInvoiceArgumentProvider implements ArgumentsProvider {

			private static final Description INVALID_INVOICE_NUMBER_MESSAGE =
					Description.of("Invoice number should be specified");
			private static final Description INVALID_RECIPIENT_MESSAGE =
					Description.of("Recipient should be valid");
			private static final Description INVALID_ADDRESS_MESSAGE =
					Description.of("Billing address should be valid");
			private static final Description INVALID_INVOICE_LINES_MESSAGE =
					Description.of("Invoice lines should all be valid");

			@Override
			public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
				return Stream.of(
						Arguments.of(
								new Invoice(
										null,
										VALID_RECIPIENT,
										VALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_INVOICE_NUMBER_MESSAGE
						),
						Arguments.of(
								new Invoice(
										new InvoiceNumber(""),
										VALID_RECIPIENT,
										VALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_INVOICE_NUMBER_MESSAGE
						),
						Arguments.of(
								new Invoice(
										new InvoiceNumber(" "),
										VALID_RECIPIENT,
										VALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_INVOICE_NUMBER_MESSAGE),
						Arguments.of(
								new Invoice(
										new InvoiceNumber("1"),
										null,
										VALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_RECIPIENT_MESSAGE
						),
						Arguments.of(
								new Invoice(
										new InvoiceNumber("1"),
										INVALID_RECIPIENT,
										VALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_RECIPIENT_MESSAGE),
						Arguments.of(new Invoice(
										new InvoiceNumber("1"),
										VALID_RECIPIENT,
										null,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_ADDRESS_MESSAGE),
						Arguments.of(new Invoice(
										new InvoiceNumber("1"),
										VALID_RECIPIENT,
										INVALID_ADDRESS,
										List.of(VALID_INVOICE_LINE)
								),
								INVALID_ADDRESS_MESSAGE),
						Arguments.of(new Invoice(
										new InvoiceNumber("1"),
										VALID_RECIPIENT,
										VALID_ADDRESS,
										null
								),
								INVALID_INVOICE_LINES_MESSAGE),
						Arguments.of(new Invoice(
										new InvoiceNumber("1"),
										VALID_RECIPIENT,
										VALID_ADDRESS,
										List.of(INVALID_INVOICE_LINE)
								),
								INVALID_INVOICE_LINES_MESSAGE)
				);
			}
		}
	}

	public static final class Stubs {
		private static class AlwaysValidAddress extends Address {
			private AlwaysValidAddress() {
				super(
						new AddressLine("addressLine"),
						new City("city"),
						new State("state"),
						new Zip("zip")
				);
			}

			public static AlwaysValidAddress get() {
				return new AlwaysValidAddress();
			}

			@Override
			protected IBusinessRuleSet<Address> rules() {
				return new BusinessRuleSet<>(emptyList());
			}
		}

		private static class AlwaysInvalidAddress extends Address {

			private AlwaysInvalidAddress() {
				super(
						new AddressLine("addressLine"),
						new City("city"),
						new State("state"),
						new Zip("zip")
				);
			}

			public static AlwaysInvalidAddress get() {
				return new AlwaysInvalidAddress();
			}

			@Override
			protected IBusinessRuleSet<Address> rules() {
				return new BusinessRuleSet<>(List.of(
						new BusinessRule<Address>(
								Description.of("It will always fail"), address -> false)));
			}
		}

		private static class AlwaysValidMoney extends Money {

			private AlwaysValidMoney() {
				super(Currencies.DEFAULT.asCurrency(), new BigDecimal(10));
			}

			public static AlwaysValidMoney get() {
				return new AlwaysValidMoney();
			}

			@Override
			protected IBusinessRuleSet<Money> rules() {
				return new BusinessRuleSet<>(emptyList());
			}
		}

		private static class AlwaysInvalidMoney extends Money {

			private AlwaysInvalidMoney() {
				super(Currencies.DEFAULT.asCurrency(), new BigDecimal(10));
			}

			public static AlwaysInvalidMoney get() {
				return new AlwaysInvalidMoney();
			}

			@Override
			protected IBusinessRuleSet<Money> rules() {
				return new BusinessRuleSet<>(List.of(
						new BusinessRule<Money>(
								Description.of("It will always fail"), money -> false)));
			}
		}

		private static class AlwaysValidRecipient extends Recipient {

			private AlwaysValidRecipient() {
				super(new Name("name"), AlwaysValidAddress.get());
			}

			public static AlwaysValidRecipient get() {
				return new AlwaysValidRecipient();
			}

			@Override
			protected IBusinessRuleSet<Recipient> rules() {
				return new BusinessRuleSet<>(Collections.emptyList());
			}
		}

		private static class AlwaysInvalidRecipient extends Recipient {

			private AlwaysInvalidRecipient() {
				super(new Name("name"), AlwaysValidAddress.get());
			}

			public static Recipient get() {
				return new AlwaysInvalidRecipient();
			}

			@Override
			protected IBusinessRuleSet<Recipient> rules() {
				return new BusinessRuleSet<>(List.of(
						new BusinessRule<Recipient>(
								Description.of("It will always fail"), money -> false)));
			}
		}

		private static class AlwaysValidInvoiceLine extends InvoiceLine {
			private AlwaysValidInvoiceLine() {
				super(
						AlwaysValidMoney.get(),
						new ProductName("product name")
				);
			}

			public static AlwaysValidInvoiceLine get() {
				return new AlwaysValidInvoiceLine();
			}

			@Override
			protected IBusinessRuleSet<InvoiceLine> rules() {
				return new BusinessRuleSet<>(Collections.emptyList());
			}
		}

		private static class AlwaysInvalidInvoiceLine extends InvoiceLine {
			private AlwaysInvalidInvoiceLine() {
				super(
						AlwaysValidMoney.get(),
						new ProductName("product name")
				);
			}

			public static AlwaysInvalidInvoiceLine get() {
				return new AlwaysInvalidInvoiceLine();
			}

			@Override
			protected IBusinessRuleSet<InvoiceLine> rules() {
				return new BusinessRuleSet<>(List.of(
						new BusinessRule<InvoiceLine>(
								Description.of("It will always fail"), money -> false)));
			}
		}
	}
}
