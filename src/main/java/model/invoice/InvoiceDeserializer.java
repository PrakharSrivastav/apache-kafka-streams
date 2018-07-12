package model.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;


public final class InvoiceDeserializer implements Deserializer<Invoice> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public Invoice deserialize(String topic, byte[] data) {
        if (data == null) return null;
        final ObjectMapper om = new ObjectMapper();
        Invoice invoice = null;
        try {
            invoice = om.readValue(data, Invoice.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    @Override
    public void close() {}
}
