package model.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class NotificationSerializer implements Serializer<Notification> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, Notification data) {
        byte[] bytes = null;
        final ObjectMapper om = new ObjectMapper();
        try {
            bytes = om.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public void close() {}
}
