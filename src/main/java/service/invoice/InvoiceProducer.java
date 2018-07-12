package service.invoice;

import config.AppConfig;
import model.invoice.Invoice;
import model.invoice.InvoiceSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;


/**
 * This is the master component that triggers the simulation process
 * The InvoiceProducer creates a new Invoice every minute and sends it to CustomerService
 *
 */
public final class InvoiceProducer {

    private static Headers headers = null;
    private static Boolean initializeApp = true;
    private static AppConfig.KafkaConfig.Topic topic = null;
    private static KafkaProducer<String, Invoice> kafkaProducer;
    private static Logger logger = LoggerFactory.getLogger(InvoiceProducer.class);

    private static Headers headers() {return headers;}

    private static AppConfig.KafkaConfig.Topic topic() {return topic;}

    private static KafkaProducer<String, Invoice> kafkaProducer() {return kafkaProducer;}

    // Initialize the producer
    public static void init(AppConfig.KafkaConfig config) {
        if (initializeApp) {
            logger.info("Initializing InvoiceProducer");

            final Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.destination());
            properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString().replaceAll("-", ""));
            properties.put(ProducerConfig.CLIENT_ID_CONFIG, "InvoiceProducer");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, InvoiceSerializer.class);
            kafkaProducer = new KafkaProducer<>(properties);

            if (topic == null) { topic = config.topics().get(0); }
            if (headers == null) { headers = setupHeaders(topic(), config); }

            initializeApp = false;
        }

        kafkaProducer().initTransactions();
    }

    // send message
    public static void send() {
        kafkaProducer().beginTransaction();
        try {
            kafkaProducer().send(newRecord());
            kafkaProducer().flush();
            kafkaProducer().commitTransaction();
            Thread.sleep(1 * 2000);
        } catch (Exception e) {
            kafkaProducer().abortTransaction();
            logger.error("InvoiceProducer :: Exception occurred. Aborting transaction", e);
        }
    }


    private static ProducerRecord<String, Invoice> newRecord() {
        final Invoice invoice = Invoice.newInvoice();
        logger.info("InvoiceProducer :: Sending new Invoice {}", invoice.toString());
        return new ProducerRecord<>(topic().name(), null, System.currentTimeMillis(), invoice.getId(), invoice, headers());
    }

    private static Headers setupHeaders(final AppConfig.KafkaConfig.Topic topic, final AppConfig.KafkaConfig config) {
        final Headers recordHeaders = new RecordHeaders();

        recordHeaders.add(new RecordHeader("Content-Type", config.contentType().getBytes()));
        recordHeaders.add(new RecordHeader("Encoding", config.encoding().getBytes()));
        recordHeaders.add(new RecordHeader("Source", topic.source().getBytes()));
        recordHeaders.add(new RecordHeader("Target", topic.target().getBytes()));

        return recordHeaders;
    }
}
