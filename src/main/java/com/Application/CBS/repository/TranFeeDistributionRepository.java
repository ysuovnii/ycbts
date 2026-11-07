package com.bankcore.repository;


import com.bankcore.model.TranFee;
import com.bankcore.model.TranFeeDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TranFeeDistributionRepository extends JpaRepository<TranFeeDistribution, String> {
    Optional<TranFeeDistribution> findByDistributorAccountNumber(String distributorAccountNumber);
    List<TranFeeDistribution> findByTranFee(TranFee tranFee);
}
