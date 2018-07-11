package model.bank;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class PaymentDeserializer implements Deserializer<Payment> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Payment deserialize(String topic, byte[] data) {
        return null;
    }

    @Override
    public void close() {

    }
}
