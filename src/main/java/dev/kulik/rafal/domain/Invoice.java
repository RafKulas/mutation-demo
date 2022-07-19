package dev.kulik.rafal.domain;

import dev.kulik.rafal.common.validation.BusinessRule;
import dev.kulik.rafal.common.validation.BusinessRuleSet;
import dev.kulik.rafal.common.validation.IBusinessRule;
import dev.kulik.rafal.common.validation.IBusinessRuleSet;
import dev.kulik.rafal.common.validation.ValidateDomainObject;
import dev.kulik.rafal.domain.objects.Description;
import dev.kulik.rafal.domain.objects.Discount;
import dev.kulik.rafal.domain.objects.InvoiceNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class Invoice extends ValidateDomainObject<Invoice> implements Cloneable {
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

	@Override
	public Invoice clone() {
		return new Invoice(
				new InvoiceNumber(this.invoiceNumber.getValue()),
				this.recipient.clone(),
				this.addressToBill.clone(),
				this.getLines()
		);
	}

	public List<InvoiceLine> getLines() {
		return this.lines == null ? null : this.lines.stream().map(InvoiceLine::clone).collect(Collectors.toList());
	}

	public static class ValidationRules {
		public static IBusinessRule<Invoice> InvoiceNumber =
				new BusinessRule<>(
						Description.of("Invoice number should be specified"),
						invoice -> !Objects.equals(invoice.getInvoiceNumber(), null) &&
								!invoice.invoiceNumber.getValue().trim().isEmpty()
				);

		public static IBusinessRule<Invoice> BillingAddress =
				new BusinessRule<>(
						Description.of("Billing address should be valid"),
						invoice -> !Objects.equals(invoice.getAddressToBill(), null) &&
								invoice.addressToBill != null && invoice.addressToBill.isValid()
				);

		public static IBusinessRule<Invoice> Recipient =
				new BusinessRule<>(
						Description.of("Recipient should be valid"),
						invoice -> !Objects.equals(invoice.getRecipient(), null) &&
								invoice.recipient != null && invoice.recipient.isValid()
				);

		public static IBusinessRule<Invoice> Lines =
				new BusinessRule<>(
						Description.of("Invoice lines should all be valid"),
						invoice -> !Objects.equals(invoice.getLines(), null) &&
								!invoice.lines.isEmpty() && invoice.lines.stream().allMatch(ValidateDomainObject::isValid)
				);
	}
}
