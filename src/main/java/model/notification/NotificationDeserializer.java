package model.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class NotificationDeserializer implements Deserializer<Notification> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {  }

    @Override
    public Notification deserialize(String topic, byte[] data) {
        if (data == null) return null;
        final ObjectMapper om = new ObjectMapper();
        Notification n = null;
        try {
            n = om.readValue(data, Notification.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void close() {}
}
