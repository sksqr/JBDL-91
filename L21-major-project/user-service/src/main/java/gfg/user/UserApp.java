package gfg.user;


import gfg.com.kafka.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"gfg.user"}, scanBasePackageClasses = {KafkaProducerConfig.class} )
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
