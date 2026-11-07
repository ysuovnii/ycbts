package com.bankcore.dto;

import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;


@Data
@RequiredArgsConstructor
public class CustomerEditRequest {

    private String customerNumber;
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerDob;
    private String customerEmail;
    private String customerMobile;
    private String customerPresentAddress;
    private String customerPermanentAddress;
    private String customerSsn;
    private String customerTin;
    private String customerDrivingLicenseNumber;
    private String accountType;

    public String toJson(){
        return new Gson().toJson(this);
    }
    public HashMap toHashMap(){
        return new Gson().fromJson(this.toJson(), HashMap.class);
    }
}