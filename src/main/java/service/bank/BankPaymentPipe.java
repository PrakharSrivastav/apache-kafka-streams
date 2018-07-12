package service.bank;

import config.AppConfig;
import model.bank.Payment;
import model.bank.PaymentSerde;
import model.notification.Notification;
import model.notification.NotificationSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class BankPaymentPipe {

    private static Boolean initializePipe = true;
    private static Logger logger = LoggerFactory.getLogger(BankPaymentPipe.class);

    public static void init(final AppConfig.KafkaConfig config) {
        if (initializePipe) {
            final Properties p = new Properties();
            p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.destination());
            p.put(StreamsConfig.APPLICATION_ID_CONFIG, "BankPaymentPipe");
            p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
            p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, PaymentSerde.class);

            final Map<String, AppConfig.KafkaConfig.Topic> topicMap = new HashMap<>();
            topicMap.put("NEW_INVOICE", config.topics().get(0));
            topicMap.put("PAY_INVOICE", config.topics().get(1));
            topicMap.put("NOTIFICATION", config.topics().get(2));

            try {
                final StreamsBuilder builder = new StreamsBuilder();
                builder.stream(topicMap.get("PAY_INVOICE").name())
                        .filter(BankPaymentPipe::notNull)
                        .map(BankPaymentPipe::apply)
                        .to(topicMap.get("NOTIFICATION").name(), Produced.with(Serdes.String(), new NotificationSerde()));
                final Topology topology = builder.build();
                final KafkaStreams streams = new KafkaStreams(topology, p);

                streams.start();
            } catch (Exception e) {
                logger.error("Error running pipe", e);
            }

            initializePipe = false;
        }
    }

    private static boolean notNull(Object k, Object v) {return v != null;}

    private static KeyValue<String, Notification> apply(Object key, Object value) {
        final Notification n = ((Payment) value).mapToNotification();
        logger.info("The Notification is {}", n);
        return new KeyValue<>((String) key, n);
    }
}
