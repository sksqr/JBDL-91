package com.example.L20_Kafka_Consumer_demo;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);


    @KafkaListener(topics = "order_processed3", groupId = "app1")
    public void consumeData(Object payload){
        String data = (String) ((ConsumerRecord) payload).value();
        LOGGER.info("Data Consuming payload: {}",payload);
        LOGGER.info("Data Consuming Value: {}",data);
    }


}
