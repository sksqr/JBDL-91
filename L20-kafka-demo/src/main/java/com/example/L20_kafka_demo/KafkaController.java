package com.example.L20_kafka_demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);


    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @GetMapping("/push")
    ResponseEntity<String> pushDataToKafka(@RequestParam String topic, @RequestParam String data) throws ExecutionException, InterruptedException {
        Future future = kafkaTemplate.send(topic,data,data);
        LOGGER.info("Payload pushed to kafka : {}",future.get());
        return ResponseEntity.ok("Data pushed");
    }
}
