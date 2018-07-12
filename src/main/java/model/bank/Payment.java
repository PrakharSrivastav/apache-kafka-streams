package model.bank;

import model.notification.Notification;

import java.util.Objects;
import java.util.Random;

public final class Payment {

    private String customerId;
    private String invoiceId;
    private String status;
    private Float amount;

    public Payment() {}

    public Payment(String customerId, String invoiceId, String status, Float amount) {
        this.customerId = customerId;
        this.invoiceId = invoiceId;
        this.status = status;
        this.amount = amount;
    }

    public String getCustomerId() { return customerId; }
    public String getInvoiceId() { return invoiceId; }
    public String getStatus() { return status; }
    public Float getAmount() { return amount; }

    public Notification mapToNotification() {
        return new Notification(this.getInvoiceId(), this.getCustomerId(), new Random().nextBoolean() == true ? "PAID" : "FAILED", this.getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(customerId, payment.customerId) &&
                Objects.equals(invoiceId, payment.invoiceId) &&
                Objects.equals(status, payment.status) &&
                Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, invoiceId, status, amount);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "customerId='" + customerId + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}
