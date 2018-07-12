import config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.bank.BankPaymentPipe;
import service.customer.CustomerInvoicePipe;
import service.customer.CustomerNotifConsumer;
import service.invoice.InvoiceNotifConsumer;
import service.invoice.InvoiceProducer;

public final class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting Application");

        logger.info("Bootstrapping configurations");
        final AppConfig config = AppConfig.load();

        logger.info("Instantiating Streaming components");
        new Thread(() -> CustomerInvoicePipe.init(config.kafkaConfig()), "CustomerPipe").start();
        new Thread(() -> BankPaymentPipe.init(config.kafkaConfig()), "BankingPipe").start();

        logger.info("Starting individual Consumers");
        new Thread(() -> {
            while (true) InvoiceNotifConsumer.init(config.kafkaConfig());
        }, "InvoiceNotificationConsumer").start();
        new Thread(() -> {
            while (true) {CustomerNotifConsumer.init(config.kafkaConfig());}
        }, "CustomerNotificationConsumer").start();

        // Start Producer in a separate Thread
        logger.info("Instantiating Producer");
        InvoiceProducer.init(config.kafkaConfig());
        new Thread(() -> {while (true) InvoiceProducer.send();}, "InvoiceProducer").start();

    }
}
