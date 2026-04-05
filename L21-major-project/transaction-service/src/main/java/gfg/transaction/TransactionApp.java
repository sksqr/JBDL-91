package gfg.transaction;


import gfg.com.kafka.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"gfg.transaction"}, scanBasePackageClasses = {KafkaProducerConfig.class} )
public class TransactionApp {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApp.class, args);
    }
}
