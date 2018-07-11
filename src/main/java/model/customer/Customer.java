package model.customer;

import java.util.Objects;

public final class Customer {
    private String id;
    private String invoiceId;
    private String account;
    private String status;
    private Float amount;

    public Customer(String id, String invoiceId, String account, String status, Float amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.account = account;
        this.status = status;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getInvoiceId() { return invoiceId; }
    public String getAccount() { return account; }
    public String getStatus() { return status; }
    public Float getAmount() { return amount; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) &&
                Objects.equals(getInvoiceId(), customer.getInvoiceId()) &&
                Objects.equals(getAccount(), customer.getAccount()) &&
                Objects.equals(getStatus(), customer.getStatus()) &&
                Objects.equals(getAmount(), customer.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInvoiceId(), getAccount(), getStatus(), getAmount());
    }

    @Override
    public String toString() {
        return "Customer{" + "id='" + id + '\'' + ", invoiceId='" + invoiceId + '\'' + ", account='" + account + '\'' + ", status='" + status + '\'' + ", amount=" + amount + '}';
    }
}
