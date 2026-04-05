package gfg.transaction.service;

import gfg.com.kafka.TxnInitPayload;
import gfg.transaction.dto.TxnRequestDto;
import gfg.transaction.dto.TxnStatusDto;
import gfg.transaction.entity.Transaction;
import gfg.transaction.entity.TxnStatusEnum;
import gfg.transaction.repo.TxnRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class TxnService {

    private static Logger LOGGER = LoggerFactory.getLogger(TxnService.class);


    @Autowired
    private TxnRepo txnRepo;


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${txn.init.topic}")
    private String txnInitTopic;

    public String initTxn( TxnRequestDto txnRequestDto) throws ExecutionException, InterruptedException {

        String txnId = UUID.randomUUID().toString();
        Transaction txn = Transaction.builder()
                .amount(txnRequestDto.getAmount())
                .fromUserId(txnRequestDto.getFromUserId())
                .toUserId(txnRequestDto.getToUserId())
                .amount(txnRequestDto.getAmount())
                .txnId(txnId)
                .status(TxnStatusEnum.PENDING)
                .build();

        txn=txnRepo.save(txn);

        TxnInitPayload txnInitPayload = new TxnInitPayload();
        txnInitPayload.setFromUserId(txnRequestDto.getFromUserId());
        txnInitPayload.setToUserId(txnRequestDto.getToUserId());
        txnInitPayload.setAmount(txnRequestDto.getAmount());
        txnInitPayload.setId(txn.getId());

        Future<SendResult<String,Object>> future  = kafkaTemplate.
                send(txnInitTopic, txnInitPayload.getFromUserId().toString(),txnInitPayload);
        LOGGER.info("Pushed userCreatedPayload to kafka: {}",future.get());
        return txnId;
    }

    public TxnStatusDto getStatus(String txnId){
        Transaction transaction = txnRepo.findByTxnId(txnId);
        TxnStatusDto txnStatusDto = new TxnStatusDto();
        if(transaction != null){
            txnStatusDto.setReason(transaction.getReason());
            txnStatusDto.setStatus(transaction.getStatus().toString());
        }
        return txnStatusDto;

    }
}
