package com.wallet.controller;


import com.wallet.WalletService;
import com.wallet.dto.AddMoneyRequest;
import com.wallet.dto.AddMoneyResponse;
import com.wallet.dto.WalletInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wallet-service")
public class WalletController {

    @Autowired
    private WalletService walletService;


    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/wallet/{userId}")
    public WalletInfo getUser(@PathVariable("userId") long userId){
        return walletService.getWalletInfo(userId);
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<WalletInfo> getBalance(@PathVariable Long userId) {
        WalletInfo walletBalanceDto = walletService.getWalletInfo(userId);
        return ResponseEntity.ok(walletBalanceDto);
    }

    @PostMapping("/add-money")
    public ResponseEntity<AddMoneyResponse> addMoney(@RequestBody AddMoneyRequest addMoneyRequest){
        addMoneyRequest.setMerchantId(1l);
        AddMoneyResponse addMoneyResponse = restTemplate.postForObject("http://localhost:9090/pg-service/init-payment", addMoneyRequest, AddMoneyResponse.class);
        return ResponseEntity.ok(addMoneyResponse);
    }

    @GetMapping("/add-money-status/{pgTxnId}")
    public ResponseEntity<String> addMoneyStatus(@PathVariable String pgTxnId){
        return ResponseEntity.ok(walletService.processPgTxnId(pgTxnId));
    }

}
