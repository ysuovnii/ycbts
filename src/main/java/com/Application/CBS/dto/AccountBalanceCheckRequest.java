package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@Slf4j
public class AccountBalanceCheckRequest {

    private String accountNumber;

    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}
