# Mutation testing with Pitest (PIT)

**To begin with, the purpose of this project is NOT a theoretical 
introduction to mutation testing. I would more like to present the practical application of the [Pitest](https://pitest.org/) plugin (being more precisely, [Gradle version](https://gradle-pitest-plugin.solidsoft.info/) of it *#TeamGradle*) and what can be achieved with it.**

### Table of Content
1. [Step 1 - Create simple project to have canvas to work on](https://github.com/RafKulas/mutation-demo/tree/step_1/create_project#create-simple-project)
2. [Step 2 - Create unit tests for project](https://github.com/RafKulas/mutation-demo/tree/step_2/create_tests#add-unit-tests)
3. [Step 3 - Add mutation testing plugin](https://github.com/RafKulas/mutation-demo/tree/step_3/add_mutation_tests#add-mutation-testing)
4. [Step 4 - Improve and refactor existing tests](https://github.com/RafKulas/mutation-demo/tree/step_4/refactor_after_pitest#improve-and-refactor-tests)

## Create simple project

For testing, it will be easiest to create a library that is simple to test. 
A fairly simple real-world example is an invoice system and its validation.

A library created in this way will be able to create invoices and validate them, 
and in case of a problem, it will be easy to retrieve what caused the validation error.

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

To make sure that our library validates invoices correctly, we need to write some tests. 
Since the logic is quite simple, the best solution is unit tests.

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

Which takes a fully valid invoice and checks that our validator returns the correct answer 
(which is `true`, since every field is filled in correctly).

## Add mutation testing

To add mutation tests to our project, the easiest way is to use the PITest Plugin 
ported to Gradle by [Marcin Zajączkowski](https://github.com/szpak).
Here I would recommend to read short quickstart [documentation](https://gradle-pitest-plugin.solidsoft.info/).
We will use easiest approach which is single project with one report. 
You can do it by adding PITest plugin to your `build.*` file.

If you are using groovy, like me, your new part should look something like this:
```groovy
plugins {
    id 'java'
    id 'info.solidsoft.pitest' version '1.9.0'
}

<...>

test {
    useJUnitPlatform()
}
pitest {
    junit5PluginVersion = '1.0.0'
    threads.set(4)
    threads.set(<Any amount you need>)
    exportLineCoverage.set(true)
    timestampedReports.set(false)
}
```

With this you can either run command verification/pitest in your IDE or 
use command line in your working directory:
```shell
./gradlew pitest
```

In this process you should get report in `build/reports` folder in `index.html` and `mutations.xml` file.
With given result you can analyze quality of your tests.

## Improve existing tests

After analyze of report we can see that our score os not that bad but neither good, 
something in between and because we want to have top quality product we will fix it!

We got worst score on packages `dev.kulik.rafal.domain` and `dev.kulik.rafal.domain.objects`:

| Name                             | Line Coverage | Mutation Coverage | Test Strength |
|----------------------------------|:-------------:|:-----------------:|:-------------:|
| `dev.kulik.rafal.domain`         |      76%      |        57%        |      81%      |
| `dev.kulik.rafal.domain.objects` |     100%      |        10%        |      33%      |

We can check what classes we neglected (low code coverage) and what is truly tested (low mutation coverage).
After some investigation we can find out we didn't tested `isValid()` function. 
We didn't check if there were broken rules. Also, there were no null checks.
After fixing those thing we can increase our test quality to:

| Name                             | Line Coverage | Mutation Coverage | Test Strength |
|----------------------------------|:-------------:|:-----------------:|:-------------:|
| `dev.kulik.rafal.domain`         |      95%      |        76%        |      89%      |
| `dev.kulik.rafal.domain.objects` |     100%      |        33%        |      71%      |

Which is much more impressive as a score
