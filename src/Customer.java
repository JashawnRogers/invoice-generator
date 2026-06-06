import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final String email;

    public Customer(String name, String email) {
        this.id = UUID.randomUUID();

        if (!name.isBlank()) {
            this.name = normalizeText(name);
        } else {
            throw new RuntimeException("Customer name cannot be blank.");
        }

        if (!email.isBlank()) {
            this.email = normalizeText(email);
        } else {
            throw new RuntimeException("Customer email cannot be blank.");
        }
    }

    public static Customer createNew(String name,String email) {
        return new Customer(name, email);
    }

    public String normalizeText(String text) {
        return text.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
