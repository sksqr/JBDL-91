package com.wallet;


import gfg.com.kafka.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wallet"}, scanBasePackageClasses = {KafkaProducerConfig.class} )
public class WalletApp {

    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class, args);

    }
}
