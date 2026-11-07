package com.bankcore.service;

import com.bankcore.model.*;
import com.bankcore.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityFinderService implements EntityFinder{
    @Autowired
    TranFeeDistributionRepository tranFeeDistributionRepository;
    @Autowired
    FeeDistributorRepository feeDistributorRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TranTypeRepository tranTypeRepository;
    @Autowired
    TranFeeRepository tranFeeRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TranFeeDistribution findTranFeeDistribution(String distributor) {
        return tranFeeDistributionRepository.findByDistributorAccountNumber(distributor).orElse(new TranFeeDistribution());
    }
    @Override
    public FeeDistributor findFeeDistributor(String accountNumber) {
        return feeDistributorRepository.findByDistributorAccountNumber(accountNumber).orElse(new FeeDistributor());
    }
    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElse(new Account());
    }
    @Override
    public TranType findTranTypeByTypeCode(String typeCode) {
        return tranTypeRepository.findByTypeCode(TranType.TypeCode.valueOf(typeCode)).orElse(new TranType());
    }
    @Override
    public List<TranFee> findAllTranFee(TranType tranType) {
        return tranFeeRepository.findByTranType(tranType);
    }
    @Override
    public TranFee findTranFeeById(String tranFeeId) {
        return tranFeeRepository.findById(Integer.parseInt(tranFeeId)).orElse(new TranFee());
    }
    @Override
    public List<TranFeeDistribution> findAllDistributions(TranFee tranFee) {
        return tranFeeDistributionRepository.findByTranFee(tranFee);
    }
    @Override
    public Customer findByCustomerNumber(BigInteger customerNumber) {
        return customerRepository.findById(customerNumber).orElse(new Customer());
    }
}
