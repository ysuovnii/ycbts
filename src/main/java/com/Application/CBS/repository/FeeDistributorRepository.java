package com.bankcore.repository;

import com.bankcore.model.FeeDistributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeeDistributorRepository extends JpaRepository<FeeDistributor, String> {
    Optional<FeeDistributor> findByDistributorAccountNumber(String accountNumber);
}
