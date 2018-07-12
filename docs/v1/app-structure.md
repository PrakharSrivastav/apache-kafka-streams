# Architecture

The Invoice system would consistently produce new invoices. This system will be modelled as standalone Kafka Producer that will publish message to topic `NEW_INVOICE`

The different systems reacting to the new invoice messages and producing side-effects (publishing to other topics) will be modelled using Kafka-Streams

InvoiceService (Producer)
    - Standalone Kafka Producer
    - Sends new Invoice every minute
    
InvoiceService (Consumer)
    - KafkaConsumer
    - Reads from NOTIFICATION topic
    - Accepts message using headers
    - Changes the status of the invoice to processed
CusotmerService (Consuming from NOTIFICATION)
    - KafkaStream
    - Reads from NOTIFICATION topic
    - Changes the status in the CustomerService   
    
CustomerService (Consumer from NEW_INVOICE)
    - KafkaStream
    - Reads message from NEW_INVOICE topic
    - Transforms Invoice to Payment format
    - Sends to SETTLE_INVOICE topic
    
 
    
BankingService
    - KafkaStream
    - Reads from SETTLE_INVOICE topic
    - Accpets only new Payment with action pay
    - Performs payment
    - Sends message to NOTIFICATION Topic
    