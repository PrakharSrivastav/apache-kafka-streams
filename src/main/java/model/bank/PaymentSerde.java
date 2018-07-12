package model.bank;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class PaymentSerde implements Serde<Payment> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}

    @Override
    public Serializer<Payment> serializer() { return new PaymentSerializer(); }

    @Override
    public Deserializer<Payment> deserializer() { return new PaymentDeserializer(); }
}
