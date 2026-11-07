package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@RequiredArgsConstructor
@Slf4j
public class TransactionGenericRequest {
    private BigDecimal amount;
    private String tranTypeCode;
    private String receiverAccountNumber;
    private String senderAccountNumber;
    private String transactionMode;
    private String actionType;
    private String transactionStatus;
    private String remarks;


    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}
