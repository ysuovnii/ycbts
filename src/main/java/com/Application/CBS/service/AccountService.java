package com.bankcore.service;

import com.bankcore.dto.AccountBalanceCheckRequest;
import com.bankcore.dto.AccountCreateRequest;
import com.bankcore.dto.GenericResponse;
import com.bankcore.enumeration.AccountStatus;
import com.bankcore.model.Account;
import com.bankcore.model.Customer;
import com.bankcore.repository.AccountRepository;
import com.bankcore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EntityFinderService entityFinderService;

    public GenericResponse createAccount(AccountCreateRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            BigInteger customerNumber = new BigInteger(request.getCustomerId());
            Customer customer = entityFinderService.findByCustomerNumber(customerNumber);
            if(null != customer.getCustomerEmail()) {

                Account account = new Account();
                account.setAccountType(Account.AccountType.valueOf(request.getAccountType()));
                account.setAccountStatus(AccountStatus.ACTIVE.name());
                account.setBalance(BigDecimal.ZERO);
                account.setCreatedTime(LocalDateTime.now());
                account.setCustomer(customer);
                account.setAccountName(request.getAccountName());

                if (request.getAccountType().equalsIgnoreCase(Account.AccountType.DPS.toString())) {
                    account.setInstallmentAmount(Double.parseDouble(request.getInstallmentAmount()));
                    account.setInstallmentTenure(Double.parseDouble(request.getInstallmentTenure()));
                    account.setInstallmentCompleted(0.0);
                }
                accountRepository.save(account);

                List<HashMap<String, Object>> accountPayload = new ArrayList<>();
                HashMap<String, Object> map = new LinkedHashMap<>();
                map.put("accountNumber",account.getAccountNumber());
                accountPayload.add(map);

                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Account created successfully");
                response.setPayload(accountPayload);

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Customer doesn't exist !");
            }

            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage("Failed to create account !");
            return response;
        }
    }

    public GenericResponse checkAccountBalance(AccountBalanceCheckRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            Account account = entityFinderService.findByAccountNumber(request.getAccountNumber());
            if(null != account.getAccountNumber()) {
                BigDecimal accountBalance = account.getBalance();
                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Account current balance is: " + accountBalance);

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Account doesn't exist !");
            }

            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage("Failed to check account balance!");
            return response;
        }
    }
}
