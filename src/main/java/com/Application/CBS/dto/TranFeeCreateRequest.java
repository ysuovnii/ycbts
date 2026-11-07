package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@Slf4j
public class TranFeeCreateRequest {
    private String tranTypeCode;
    private Boolean isSlabApplicable;
    private Boolean isFeeAmountFixed;
    private double feeFixedAmount;
    private Boolean isFeeAmountPercentage;
    private double feePercentageAmount;
    private Boolean isMaximumApplicable;
    private double feeMaximumAmount;
    private String description;


    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}
