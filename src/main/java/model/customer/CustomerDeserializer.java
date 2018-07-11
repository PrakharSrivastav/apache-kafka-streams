package model.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public final class CustomerDeserializer implements Deserializer<Customer> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public Customer deserialize(String topic, byte[] data) {
        final ObjectMapper om = new ObjectMapper();
        Customer customer = null;
        try {
            customer = om.readValue(data, Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void close() {}
}
