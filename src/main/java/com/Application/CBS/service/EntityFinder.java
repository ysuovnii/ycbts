package com.bankcore.service;

import com.bankcore.model.*;

import java.math.BigInteger;
import java.util.List;

public interface EntityFinder {
    TranFeeDistribution findTranFeeDistribution(String distributor);
    FeeDistributor findFeeDistributor(String accountNumber);
    Account findByAccountNumber(String accountNumber);
    TranType findTranTypeByTypeCode(String typeCode);
    List<TranFee> findAllTranFee(TranType tranType);
    TranFee findTranFeeById(String tranFeeId);
    List<TranFeeDistribution> findAllDistributions(TranFee tranFee);
    Customer findByCustomerNumber(BigInteger customerNumber);
}
