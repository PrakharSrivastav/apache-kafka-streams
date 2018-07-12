package model.invoice;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class InvoiceSerdes implements Serde<Invoice> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}

    @Override
    public Serializer<Invoice> serializer() { return new InvoiceSerializer(); }

    @Override
    public Deserializer<Invoice> deserializer() { return new InvoiceDeserializer(); }
}
