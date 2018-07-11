package service.customer;

import config.AppConfig;
import model.invoice.Invoice;
import model.invoice.InvoiceDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public final class CustomerInvoicePipe {

    private static Boolean initializePipe = true;
    private static Logger logger = LoggerFactory.getLogger(CustomerInvoicePipe.class);

    public static void init(final AppConfig.KafkaConfig config) {
        if (initializePipe) {
            final Properties p = new Properties();
            p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.destination());
            p.put(StreamsConfig.APPLICATION_ID_CONFIG, "CustomerInvoicePipe");
            p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, String.class);
            p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, InvoiceDeserializer.class);

            final StreamsBuilder builder = new StreamsBuilder();

            builder.stream("NEW_INVOICE")
//                    .map((key, value) -> { return ((Invoice) value).mapToPayment(); })
                    .foreach((k, v) -> {
                        logger.info("The recieved message is {}", ((Invoice) v).toString());
                    });
            final Topology topology = builder.build();
            final KafkaStreams streams = new KafkaStreams(topology, p);
            initializePipe = false;
        }
    }
}
