import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Invoice {
    private final UUID invoiceNumber;
    private final Customer customer;
    private final LocalDate invoiceDate;
    private final LocalDate dueDate;
    private final List<LineItem> lineItems;
    private final double TAX_RATE = 0.086;

    public Invoice(Customer customer, LocalDate invoiceDate, LocalDate dueDate, List<LineItem> lineItems) {
        this.invoiceNumber = UUID.randomUUID();

        if (customer != null) {
            this.customer = customer;
        } else {
            throw new RuntimeException("A customer is required to create an invoice.");
        }

        if (invoiceDate != null) {
            this.invoiceDate = invoiceDate;
        } else {
            throw new RuntimeException("An invoice must have an invoice date.");
        }

        if (dueDate != null) {
            if (dueDate.isAfter(invoiceDate) || dueDate.isEqual(invoiceDate)) {
                this.dueDate = dueDate;
            } else {
                throw new RuntimeException("The due date must be on or after the invoice date.");
            }
        } else {
            throw new RuntimeException("A due date must be provided.");
        }

        if (lineItems == null || lineItems.isEmpty()) {
            this.lineItems = lineItems;
        } else {
            throw new RuntimeException("Line items are required to create an invoice.");
        }
    }

    public static Invoice createNew(Customer customer,
                                    LocalDate invoiceDate,
                                    LocalDate dueDate,
                                    List<LineItem> lineItems) {
        return new Invoice(customer, invoiceDate,dueDate, lineItems);
    }

    public BigDecimal calculateSubtotal(List<LineItem> lineItems) {
        return lineItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTax(BigDecimal subtotal) {
        return subtotal.multiply(BigDecimal.valueOf(TAX_RATE))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax) {
        return subtotal.add(tax);
    }

    public UUID getInvoiceNumber() {
        return invoiceNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public double getTAX_RATE() {
        return TAX_RATE;
    }
}
