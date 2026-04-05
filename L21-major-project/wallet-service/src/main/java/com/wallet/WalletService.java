package com.wallet;

import com.wallet.entity.Wallet;
import com.wallet.repo.WalletRepo;
import gfg.com.kafka.TxnCompletedPayload;
import gfg.com.kafka.TxnInitPayload;
import gfg.com.kafka.UserCreatedPayload;
import gfg.com.kafka.WalletUpdatedPayload;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class WalletService {

    private static Logger LOGGER = LoggerFactory.getLogger(WalletService.class);


    @Autowired
    private WalletRepo walletRepo;

    @Value("${wallet.update.topic}")
    private String walletUpdatedTopic;


    @Value("${txn.completed.topic}")
    private String txnCompletedTopic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void createWallet(UserCreatedPayload userCreatedPayload) throws ExecutionException, InterruptedException {
        Wallet wallet = new Wallet();
        wallet.setBalance(100.00);
        wallet.setUserId(userCreatedPayload.getUserId());
        wallet.setUserEmail(userCreatedPayload.getUserEmail());
        wallet = walletRepo.save(wallet);

        WalletUpdatedPayload  walletUpdatedPayload = WalletUpdatedPayload.builder()
                .walletId(wallet.getId())
                .newBalance(wallet.getBalance())
                .userEmail(wallet.getUserEmail())
                .userId(wallet.getUserId())
                .build();

        Future<SendResult<String,Object>> future  = kafkaTemplate.
                send(walletUpdatedTopic, walletUpdatedPayload.getUserEmail(),walletUpdatedPayload);
        LOGGER.info("Pushed userCreatedPayload to kafka: {}",future.get());
    }

    @Transactional
    public void walletTxn(TxnInitPayload txnInitPayload) throws ExecutionException, InterruptedException {
        Wallet fromWallet = walletRepo.findByUserId(txnInitPayload.getFromUserId());
        TxnCompletedPayload txnCompletedPayload = new TxnCompletedPayload();
        txnCompletedPayload.setId(txnInitPayload.getId());
        if(fromWallet.getBalance() < txnInitPayload.getAmount()){
            txnCompletedPayload.setSuccess(false);
            txnCompletedPayload.setReason("Low Balance");
        }
        else{
            txnCompletedPayload.setSuccess(true);

            Wallet toWallet = walletRepo.findByUserId(txnInitPayload.getToUserId());
            fromWallet.setBalance(fromWallet.getBalance()- txnInitPayload.getAmount());
            toWallet.setBalance(toWallet.getBalance() + txnInitPayload.getAmount());

            WalletUpdatedPayload walletUpdatedPayload1 = new WalletUpdatedPayload(
                    fromWallet.getUserId(),
                    fromWallet.getUserEmail(),
                    fromWallet.getId(),
                    fromWallet.getBalance(),
                    "123"
            );

            WalletUpdatedPayload walletUpdatedPayload2 =new WalletUpdatedPayload(
                    toWallet.getUserId(),
                    toWallet.getUserEmail(),
                    toWallet.getId(),
                    toWallet.getBalance(),
                    "123"
            );
            Future<SendResult<String,Object>> walletUpdatedFuture1  = kafkaTemplate.send(walletUpdatedTopic,walletUpdatedPayload1.getUserEmail(),walletUpdatedPayload1);
            LOGGER.info("Pushed WalletUpdated to kafka: {}",walletUpdatedFuture1.get());

            Future<SendResult<String,Object>> walletUpdatedFuture2  = kafkaTemplate.send(walletUpdatedTopic,walletUpdatedPayload2.getUserEmail(),walletUpdatedPayload2);
            LOGGER.info("Pushed WalletUpdated to kafka: {}",walletUpdatedFuture2.get());
        }

        Future<SendResult<String,Object>> future  = kafkaTemplate.send(txnCompletedTopic,txnInitPayload.getFromUserId().toString(),txnCompletedPayload);
        LOGGER.info("Pushed TxnCompleted to kafka: {}",future.get());


    }
}
