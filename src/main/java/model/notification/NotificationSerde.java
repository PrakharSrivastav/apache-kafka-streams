package model.notification;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class NotificationSerde implements Serde<Notification> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}

    @Override
    public Serializer<Notification> serializer() {
        return new NotificationSerializer();
    }

    @Override
    public Deserializer<Notification> deserializer() {
        return new NotificationDeserializer();
    }
}
