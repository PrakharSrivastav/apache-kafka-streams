package model.notification;

import java.util.Objects;

public final class Notification {
    private String invoiceId;
    private String customerId;
    private String status;
    private Float amount;

    public Notification(String invoiceId, String customerId, String status, Float amount) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
    }

    public Notification() {
    }

    public String getInvoiceId() { return invoiceId; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public Float getAmount() { return amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(getInvoiceId(), that.getInvoiceId()) &&
                Objects.equals(getCustomerId(), that.getCustomerId()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getInvoiceId(), getCustomerId(), getStatus(), getAmount());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "invoiceId='" + invoiceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }


}
