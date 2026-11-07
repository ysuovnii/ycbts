package com.bankcore.repository;

import com.bankcore.model.TranFee;
import com.bankcore.model.TranType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranFeeRepository extends JpaRepository<TranFee, Integer> {
    List<TranFee> findByTranType(TranType tranType);
}
