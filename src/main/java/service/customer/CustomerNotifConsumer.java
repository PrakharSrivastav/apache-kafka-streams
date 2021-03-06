package service.customer;

import config.AppConfig;
import model.notification.Notification;
import model.notification.NotificationDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public final class CustomerNotifConsumer {
    private static Boolean initApp = true;
    private static Logger logger = LoggerFactory.getLogger(CustomerNotifConsumer.class);
    private static KafkaConsumer<String, Notification> kafkaConsumer;

    private static KafkaConsumer<String, Notification> getKafkaConsumer() {return kafkaConsumer;}

    public static void init(final AppConfig.KafkaConfig config) {
        if (initApp) {
            try {
                logger.info("Instantiating InvoiceNotifConsumer");
                Properties properties = new Properties();
                properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.destination());
                properties.put(ConsumerConfig.GROUP_ID_CONFIG, "c2-group");
                properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
                properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "ApacheKafkaGettingStarted");
                properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
                properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, NotificationDeserializer.class);
                kafkaConsumer = new KafkaConsumer<>(properties);
                getKafkaConsumer().subscribe(Arrays.asList(config.topics().get(2).name()));
                initApp = false;
            } catch (Exception e) {
                logger.error("Exception initializing consumer {}", CustomerNotifConsumer.class.getName(), e);
            }
        }
        consumeMessages();
    }

    private static void consumeMessages() {
        logger.info("Consuming");
        try {
            for (ConsumerRecord<String, Notification> record : getKafkaConsumer().poll(1000)) {
                logger.info("Consuming Notification for Customer {}", record.value().toString());
            }
        } catch (Exception e) {
            logger.error("Error reading kafka ", e);
            e.printStackTrace();
        }
    }
}
