package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;


@Data
@RequiredArgsConstructor
public class CustomerCreateRequest {

    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerDob;
    private String customerEmail;
    private String customerMobile;
    private String createdTime;
    private String lastUpdateTime;
    private String deactivatedTime;
    private String deletedTime;
    private String customerPresentAddress;
    private String customerPermanentAddress;
    private String customerSsn;
    private String customerTin;
    private String customerDrivingLicenseNumber;
    private String accountType;
    private String accountName;

    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}