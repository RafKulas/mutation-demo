package dev.kulik.rafal.domain;

import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.Discount;
import dev.kulik.rafal.domain.objects.InvoiceNumber;
import dev.kulik.rafal.common.validation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Invoice extends ValidateDomainObject<Invoice> {
	private final InvoiceNumber invoiceNumber;
	private final Recipient recipient;
	private final Address addressToBill;
	private final List<InvoiceLine> lines;
	private final Discount discount;

	public Invoice(InvoiceNumber invoiceNumber, Recipient recipient, Address addressToBill, List<InvoiceLine> lines) {
		this(invoiceNumber, recipient, addressToBill, lines, new Discount(0.0));
	}

	public void attachInvoiceLine(InvoiceLine line) {
		lines.add(line);
	}

	@Override
	protected IBusinessRuleSet<Invoice> rules() {
		return new BusinessRuleSet<>(List.of(
				ValidationRules.InvoiceNumber,
				ValidationRules.BillingAddress,
				ValidationRules.Recipient,
				ValidationRules.Lines));
	}

	public static class ValidationRules {
		public static IBusinessRule<Invoice> InvoiceNumber =
				new BusinessRule<>(
						Description.of("Invoice number should be specified"),
						invoice -> !invoice.invoiceNumber.getValue().isEmpty()
				);

		public static IBusinessRule<Invoice> BillingAddress =
				new BusinessRule<>(
						Description.of("Billing address should be valid"),
						invoice -> invoice.addressToBill != null && invoice.addressToBill.isValid()
				);

		public static IBusinessRule<Invoice> Recipient =
				new BusinessRule<>(
						Description.of("Recipient should be valid"),
						invoice -> invoice.recipient != null && invoice.recipient.isValid()
				);

		public static IBusinessRule<Invoice> Lines =
				new BusinessRule<>(
						Description.of("Invoice lines should all be valid"),
						invoice -> !invoice.lines.isEmpty() && invoice.lines.stream().allMatch(ValidateDomainObject::isValid)
				);
	}
}
