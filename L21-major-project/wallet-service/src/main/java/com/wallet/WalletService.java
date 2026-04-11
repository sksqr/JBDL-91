package com.wallet;

import com.wallet.dto.PGPaymentStatusDTO;
import com.wallet.dto.WalletInfo;
import com.wallet.entity.Wallet;
import com.wallet.repo.WalletRepo;
import gfg.com.kafka.TxnCompletedPayload;
import gfg.com.kafka.TxnInitPayload;
import gfg.com.kafka.UserCreatedPayload;
import gfg.com.kafka.WalletUpdatedPayload;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

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
                .requestId(userCreatedPayload.getRequestId())
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
        txnCompletedPayload.setRequestId(txnInitPayload.getRequestId());
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
                    MDC.get("requestId")
            );

            WalletUpdatedPayload walletUpdatedPayload2 =new WalletUpdatedPayload(
                    toWallet.getUserId(),
                    toWallet.getUserEmail(),
                    toWallet.getId(),
                    toWallet.getBalance(),
                    MDC.get("requestId")
            );
            Future<SendResult<String,Object>> walletUpdatedFuture1  = kafkaTemplate.send(walletUpdatedTopic,walletUpdatedPayload1.getUserEmail(),walletUpdatedPayload1);
            LOGGER.info("Pushed WalletUpdated to kafka: {}",walletUpdatedFuture1.get());

            Future<SendResult<String,Object>> walletUpdatedFuture2  = kafkaTemplate.send(walletUpdatedTopic,walletUpdatedPayload2.getUserEmail(),walletUpdatedPayload2);
            LOGGER.info("Pushed WalletUpdated to kafka: {}",walletUpdatedFuture2.get());
        }

        Future<SendResult<String,Object>> future  = kafkaTemplate.send(txnCompletedTopic,txnInitPayload.getFromUserId().toString(),txnCompletedPayload);
        LOGGER.info("Pushed TxnCompleted to kafka: {}",future.get());


    }


    public WalletInfo getWalletInfo(Long userId){
        LOGGER.info("Fetching wallet info for user: {}",userId);
        Wallet wallet = walletRepo.findByUserId(userId);
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setBalance(wallet.getBalance());
        walletInfo.setUserId(wallet.getUserId());
        walletInfo.setId(wallet.getId());
        return walletInfo;
    }

    public String processPgTxnId(String pgTxnId){
        PGPaymentStatusDTO pgPaymentStatusDTO = restTemplate.getForObject("http://localhost:9090/pg-service/payment-status/"+pgTxnId, PGPaymentStatusDTO.class);
        if (pgPaymentStatusDTO.getStatus().equalsIgnoreCase("SUCCESS")) {
            Wallet wallet = walletRepo.findByUserId(pgPaymentStatusDTO.getUserId());
            wallet.setBalance(wallet.getBalance() + pgPaymentStatusDTO.getAmount());
            walletRepo.save(wallet);
            return "Wallet Updated";
        }
        else{
            return "PG Txn Failed";
        }
    }
}
