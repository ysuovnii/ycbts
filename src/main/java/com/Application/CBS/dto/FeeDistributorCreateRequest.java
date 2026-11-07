package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@Slf4j
public class FeeDistributorCreateRequest {

    private String distributorName;
    private String distributorAccountNumber;
    private String isActive;
    private String description;

    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}
