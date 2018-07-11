import config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.customer.CustomerInvoicePipe;
import service.invoice.InvoiceProducer;

public final class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting Application");

        logger.info("Bootstrapping configurations");
        final AppConfig config = AppConfig.load();

        logger.info("Instantiating Streaming components");
        new Thread(() -> CustomerInvoicePipe.init(config.kafkaConfig())).start();

        logger.info("Instantiating Producer");
        InvoiceProducer.init(config.kafkaConfig());
        // Start Producer in a separate Thread
        new Thread(() -> {while (true) InvoiceProducer.send();}).start();

    }
}
