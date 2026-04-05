package gfg.transaction.config;

import gfg.com.kafka.TxnCompletedPayload;
import gfg.transaction.entity.Transaction;
import gfg.transaction.entity.TxnStatusEnum;
import gfg.transaction.repo.TxnRepo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class TransactionKafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(TransactionKafkaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private TxnRepo txnRepo;

    @KafkaListener(topics = "${txn.completed.topic}", groupId = "txn")
    public void consumeTxnInitTopic(ConsumerRecord payload) {
        TxnCompletedPayload txnCompletedPayload = OBJECT_MAPPER.readValue(payload.value().toString(), TxnCompletedPayload.class);
        LOGGER.info("Read from kafka : {}", txnCompletedPayload);
        Transaction transaction = txnRepo.findById(txnCompletedPayload.getId()).get();
        if(!txnCompletedPayload.getSuccess()){
            transaction.setStatus(TxnStatusEnum.FAILED);
            transaction.setReason(txnCompletedPayload.getReason());
        }
        else{
            transaction.setStatus(TxnStatusEnum.SUCCESS);
        }
        txnRepo.save(transaction);
    }
}
