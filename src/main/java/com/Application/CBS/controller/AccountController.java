package com.bankcore.controller;

import com.bankcore.dto.AccountBalanceCheckRequest;
import com.bankcore.dto.AccountCreateRequest;
import com.bankcore.dto.GenericResponse;
import com.bankcore.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @ResponseBody
    @PostMapping("/v1/create-account")
    public ResponseEntity<GenericResponse> createAccount(@RequestBody AccountCreateRequest request) {
        log.info("Account create request {}",request);
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @ResponseBody
    @GetMapping("/v1/check-balance")
    public ResponseEntity<GenericResponse> checkBalance(@RequestBody AccountBalanceCheckRequest request) {
        log.info("Account balance check request {}",request);
        return ResponseEntity.ok(accountService.checkAccountBalance(request));
    }
}
