package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@Slf4j
public class AccountCreateRequest {

    private String customerId;
    private String AccountType;
    private String AccountName;
    private String installmentAmount;
    private String installmentTenure;

    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}
