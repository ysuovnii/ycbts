package com.bankcore.controller;


import com.bankcore.dto.*;
import com.bankcore.service.DepositWithdrawalService;
import com.bankcore.service.TranConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TranConfigurationService tranConfigurationService;
    @Autowired
    DepositWithdrawalService depositWithdrawalService;

    @ResponseBody
    @PostMapping("/v1/create-fee-distributor")
    public ResponseEntity<GenericResponse> createFeeDistributor(@RequestBody FeeDistributorCreateRequest request) {
        log.info("Fee-distributor create request: {}",request);
        return ResponseEntity.ok(tranConfigurationService.createFeeDistributor(request));
    }
    @ResponseBody
    @PostMapping("/v1/create-tran-fee-distribution")
    public ResponseEntity<GenericResponse> createTranFeeDistribution(@RequestBody TranFeeDistributionCreateRequest request) {
        log.info("Tran-fee-distribution create request: {}",request);
        return ResponseEntity.ok(tranConfigurationService.createTranFeeDistribution(request));
    }

    @ResponseBody
    @PostMapping("/v1/create-tran-type")
    public ResponseEntity<GenericResponse> createTranType(@RequestBody TranTypeCreateRequest request) {
        log.info("Tran-type create request: {}",request);
        return ResponseEntity.ok(tranConfigurationService.createTranType(request));
    }

    @ResponseBody
    @PostMapping("/v1/create-tran-fee")
    public ResponseEntity<GenericResponse> createTranFee(@RequestBody TranFeeCreateRequest request) {
        log.info("Tran-fee create request: {}",request);
        return ResponseEntity.ok(tranConfigurationService.createTranFee(request));
    }

    @ResponseBody
    @PostMapping("/v1/request-deposit")
    public ResponseEntity<GenericResponse> requestDeposit(@RequestBody TransactionGenericRequest request) {
        log.info("Request-deposit request: {}",request);
        return ResponseEntity.ok(depositWithdrawalService.processTranRequest(request));
    }
    @ResponseBody
    @PostMapping("/v1/request-withdraw")
    public ResponseEntity<GenericResponse> requestWithdraw(@RequestBody TransactionGenericRequest request) {
        log.info("Request-withdrawal request: {}",request);
        return ResponseEntity.ok(depositWithdrawalService.processTranRequest(request));
    }

}
