import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Invoice Generator");

        System.out.print("Please enter the customer's name: ");
        String customerName = scanner.nextLine();

        System.out.print("Please enter the customer's email address: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Please enter the invoice date: ");
        String invoiceDate = scanner.nextLine();

        System.out.print("Please enter the invoice due date: ");
        String invoiceDueDate = scanner.nextLine();

        System.out.println("Please enter the first line item");
        System.out.print("Description: ");
        String lineItemDesc = scanner.nextLine();

        System.out.print("Quantity: ");
        int lineItemQuantity = scanner.nextInt();

        System.out.print("Unit price (xx.xx): ");
        BigDecimal lineItemUnitPrice = scanner.nextBigDecimal();

        LineItem lineItem = LineItem.createNew(lineItemDesc, lineItemQuantity, lineItemUnitPrice);
        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(lineItem);

        System.out.print("How many more line items would you like to create? (0-10): ");
        int numOfLineItems = scanner.nextInt();

        if (numOfLineItems > 10 || numOfLineItems < 0) {
            System.out.println("Please enter a number between 0 and 10 (inclusive).");

            System.out.print("How many more line items would you like to create? (0-10): ");
            numOfLineItems = scanner.nextInt();
        }

        for (int i = 0; i < numOfLineItems; i++) {
            System.out.print("Description: ");
            scanner.nextLine();
            lineItemDesc = scanner.nextLine();

            System.out.println("Description = " + lineItemDesc);
            System.out.print("Quantity: ");
            lineItemQuantity = scanner.nextInt();

            System.out.print("Unit price (xx.xx): ");
            lineItemUnitPrice = scanner.nextBigDecimal();

            LineItem extraLineItem = LineItem.createNew(lineItemDesc, lineItemQuantity, lineItemUnitPrice);
            lineItems.add(extraLineItem);
        }



        Customer customer = Customer.createNew(customerName, customerEmail);
        Invoice invoice = Invoice.createNew(customer, parseDate(invoiceDate), parseDate(invoiceDueDate), lineItems);

        // Focus on formatting invoice in terminal.
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date input", e.getParsedString(), e.getErrorIndex());
        }
    }
}
