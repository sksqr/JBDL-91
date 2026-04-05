package gfg.transaction.controller;

import gfg.transaction.dto.TxnRequestDto;
import gfg.transaction.dto.TxnStatusDto;
import gfg.transaction.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/transaction-service")
public class TxnController {

    private static Logger LOGGER = LoggerFactory.getLogger(TxnController.class);

    @Autowired
    private TxnService txnService;

    @PostMapping("/txn")
    public ResponseEntity<String> initTxn(@RequestBody TxnRequestDto txnRequestDto) throws ExecutionException, InterruptedException {
        LOGGER.info("Starting transaction : {}", txnRequestDto);
        String txnid = txnService.initTxn(txnRequestDto);
        LOGGER.info("Finished transaction : {}", txnid);
        return ResponseEntity.accepted().body(txnid);
    }

    @GetMapping("/status/{txnId}")
    public ResponseEntity<TxnStatusDto> getTxnStatus(@PathVariable String txnId){
        return ResponseEntity.ok(txnService.getStatus(txnId));
    }

}
