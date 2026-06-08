import java.math.BigDecimal;
import java.math.RoundingMode;

public class LineItem {
    private final String description;
    private final int quantity;
    private final BigDecimal unitPrice;

    public LineItem(String description, int quantity, BigDecimal unitPrice) {
        this.description = validateAndNormalizeText(description);

        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new RuntimeException("Line item's quantity must be greater than 0.");
        }

        if (unitPrice.compareTo(BigDecimal.ZERO) > 0) {
            this.unitPrice = unitPrice;
        } else {
            throw new RuntimeException("Line item unit price must be greater than $0.");
        }
    }

    public static LineItem createNew(String description, int quantity, BigDecimal unitPrice) {
        return new LineItem(description, quantity, unitPrice);
    }

    public String validateAndNormalizeText(String text) {
        if (text == null || text.isBlank()) {
            throw new RuntimeException("A line item description is required.");
        }
        return text.trim();
    }

    public BigDecimal calculateTotal(int quantity, BigDecimal unitPrice) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
