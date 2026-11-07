package com.bankcore.service;

import com.bankcore.dto.GenericResponse;
import com.bankcore.dto.TransactionGenericRequest;
import com.bankcore.model.Account;
import com.bankcore.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
public class BaseTransactionService {

    @Autowired
    private EntityFinderService entityFinderService;

    @Transactional
    public GenericResponse requestTransaction(TransactionGenericRequest request) {
        GenericResponse response = new GenericResponse();
        if (request == null || request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage("Invalid transaction amount.");
            return response;
        }

        String senderAccountNumber = request.getSenderAccountNumber();
        String receiverAccountNumber = request.getReceiverAccountNumber();
        if (senderAccountNumber == null || senderAccountNumber.isBlank()) {
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage("Sender account number is required.");
            return response;
        }

        Account senderAccount = entityFinderService.findByAccountNumber(senderAccountNumber);
        if (senderAccount == null || senderAccount.getAccountNumber() == null || senderAccount.getAccountNumber().isBlank()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setResponseMessage("Sender account not found.");
            return response;
        }

        if (receiverAccountNumber == null || receiverAccountNumber.isBlank()) {
            receiverAccountNumber = senderAccountNumber;
        }

        if (senderAccountNumber.equals(receiverAccountNumber)) {
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage("Transfer to the same account is not allowed.");
            return response;
        }

        Account receiverAccount = entityFinderService.findByAccountNumber(receiverAccountNumber);
        if (receiverAccount == null || receiverAccount.getAccountNumber() == null || receiverAccount.getAccountNumber().isBlank()) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setResponseMessage("Receiver account not found.");
            return response;
        }

        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage("Insufficient balance for this transaction.");
            return response;
        }

        BigDecimal updatedSenderBalance = senderAccount.getBalance().subtract(request.getAmount());
        BigDecimal updatedReceiverBalance = receiverAccount.getBalance().add(request.getAmount());

        senderAccount.setBalance(updatedSenderBalance);
        receiverAccount.setBalance(updatedReceiverBalance);

        entityFinderService.accountRepository.save(senderAccount);
        entityFinderService.accountRepository.save(receiverAccount);

        String transactionId = generateTranId();
        Transaction transaction = new Transaction();
        transaction.setCreatedTime(LocalDateTime.now());
        transaction.setTransactionId(transactionId);
        transaction.setTransactionMode(Transaction.TransactionType.valueOf(request.getTransactionMode() == null ? "WITHDRAW" : request.getTransactionMode().toUpperCase()));
        transaction.setTransactionStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setAmount(request.getAmount());
        transaction.setActionType(Transaction.ActionType.valueOf(request.getActionType() == null ? "DEBIT" : request.getActionType().toUpperCase()));
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber(receiverAccountNumber);
        transaction.setRemarks(request.getRemarks());
        transaction.setCompletedTime(LocalDateTime.now());
        entityFinderService.transactionRepository.save(transaction);

        response.setResponseCode(HttpStatus.OK.value());
        response.setResponseMessage("Transaction successful. ID: " + transactionId);
        return response;
    }

    private String generateTranId() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int RANDOM_LENGTH = 5;
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder();
        String timestamp = String.valueOf(Instant.now().toEpochMilli()).substring(2, 13);
        id.append(timestamp);
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }
        return id.toString();
    }

    public GenericResponse processTranRequest(TransactionGenericRequest request) {
        return requestTransaction(request);
    }
}
