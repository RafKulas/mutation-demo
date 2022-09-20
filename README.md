# Mutation testing with Pitest (PIT)

**To begin with, the purpose of this project is NOT a theoretical introduction to mutation testing. I would more like to present the practical application of the [Pitest](https://pitest.org/) plugin (being more precisely, [Gradle version](https://gradle-pitest-plugin.solidsoft.info/) of it *#TeamGradle*) and what can be achieved with it.**

### Table of Content
1. [Step 1 - Create simple project to have canvas to work on](https://github.com/RafKulas/mutation-demo/tree/step_1/create_project#create-simple-project)
2. [Step 2 - Create unit tests for project](https://github.com/RafKulas/mutation-demo/tree/step_2/create_tests#add-unit-tests)
3. [Step 3 - Add mutation testing plugin](https://github.com/RafKulas/mutation-demo/tree/step_3/add_mutation_tests#add-mutation-testing)
4. [Step 4 - Improve and refactor existing tests](https://github.com/RafKulas/mutation-demo/tree/step_4/refactor_after_pitest#improve-and-refactor-tests)

## Create simple project

For testing, it will be easiest to create a library that is simple to test. A fairly simple real-world example is an invoice system and its validation.

A library created in this way will be able to create invoices and validate them, and in case of a problem, it will be easy to retrieve what caused the validation error.

Example of use:
```java
var invoice = Invoice
        .builder()
        .recipient(rafKulas)
        .invoiceNumber(invoiceNumber)
        .addressToBill(address)
        .lines(invoiceLines)
        .build();
var invoiceHasProperRecipient = !invoice
        .getBrokenRules().contains(Invoice.ValidationRules.Recipient);

assert invoiceHasProperRecipient == true
```

## Add Unit Tests

To make sure that our library validates invoices correctly, we need to write some tests. Since the logic is quite simple, the best solution is unit tests.

With power and magic of JUnit5 we can write simple tests like:

```java
@Test
public void givenInvoice_whenAllFieldsAreProperlyFilledExceptDiscount_expectValid() {
    // given
    var validInvoice = new Invoice(
            new InvoiceNumber("1"),
            TestData.InvoiceTestData.VALID_RECIPIENT,
            TestData.InvoiceTestData.VALID_ADDRESS,
            List.of(TestData.InvoiceTestData.VALID_INVOICE_LINE)
    );

    // when
    var isValid = validInvoice.isValid();

    // expect
    assertTrue(isValid);
}
```

Which takes a fully valid invoice and checks that our validator returns the correct answer (which is `true`, since every field is filled in correctly).
