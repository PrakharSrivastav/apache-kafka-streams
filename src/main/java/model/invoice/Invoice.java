package model.invoice;

import model.bank.Payment;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Invoice {
    private String id;
    private String customerId;
    private String status;
    private Float amount;

    public Invoice(String id, String customerId, String status, Float amount) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public Float getAmount() { return amount; }

    public static Invoice newInvoice() {
        return new Invoice(
                UUID.randomUUID().toString().replaceAll("-", ""),
                UUID.randomUUID().toString().replaceAll("-", ""),
                "NEW",
                new Random().nextFloat() * 100
        );
    }


    public Payment mapToPayment(){
        return new Payment(this.customerId,this.id,"PAY", this.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                Objects.equals(customerId, invoice.customerId) &&
                Objects.equals(status, invoice.status) &&
                Objects.equals(amount, invoice.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, status, amount);
    }

    @Override
    public String toString() {
        return "Invoice {" + "id='" + id + '\'' + ", customerId='" + customerId + '\'' + ", status='" + status + '\'' + ", amount=" + amount + '}';
    }
}
