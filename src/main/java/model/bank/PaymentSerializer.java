package model.bank;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class PaymentSerializer implements Serializer<Payment> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Payment data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
