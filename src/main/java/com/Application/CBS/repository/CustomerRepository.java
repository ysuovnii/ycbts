package com.bankcore.repository;

import com.bankcore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, BigInteger> {
    Optional<Customer> findByCustomerMobileAndCustomerEmail(String mobile, String email);
}
