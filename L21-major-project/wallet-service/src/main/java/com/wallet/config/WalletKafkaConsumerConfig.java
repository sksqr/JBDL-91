package com.wallet.config;

import com.wallet.WalletService;
import com.wallet.entity.Wallet;
import com.wallet.repo.WalletRepo;
import gfg.com.kafka.TxnInitPayload;
import gfg.com.kafka.UserCreatedPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.ExecutionException;

@Configuration
public class WalletKafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(WalletKafkaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private WalletService walletService;

    @KafkaListener(topics = "${user.created.topic}", groupId = "wallet")
    public void consumeUserCreateTopic(ConsumerRecord payload) throws ExecutionException, InterruptedException {
        LOGGER.info("Read from kafka : {}", payload);
        UserCreatedPayload userCreatedPayload = OBJECT_MAPPER.readValue(payload.value().toString(), UserCreatedPayload.class);
        MDC.put("requestId", userCreatedPayload.getRequestId());
        LOGGER.info("Read from kafka : {}", userCreatedPayload);
        walletService.createWallet(userCreatedPayload);
        MDC.clear();
    }

    @KafkaListener(topics = "${txn.init.topic}", groupId = "wallet")
    public void consumeInitTxnTopic(ConsumerRecord payload) throws ExecutionException, InterruptedException {
        LOGGER.info("Read from kafka : {}", payload);
        TxnInitPayload txnInitPayload = OBJECT_MAPPER.readValue(payload.value().toString(), TxnInitPayload.class);
        MDC.put("requestId", txnInitPayload.getRequestId());
        LOGGER.info("Read from kafka : {}", txnInitPayload);
        walletService.walletTxn(txnInitPayload);
        MDC.clear();
    }



}
