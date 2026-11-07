package com.bankcore.service;

import com.bankcore.dto.CustomerCreateRequest;
import com.bankcore.dto.CustomerEditRequest;
import com.bankcore.dto.GenericResponse;
import com.bankcore.enumeration.AccountStatus;
import com.bankcore.model.Account;
import com.bankcore.model.Customer;
import com.bankcore.repository.AccountRepository;
import com.bankcore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    private Customer findCustomerExisting(String mobile, String email) {
        return customerRepository.findByCustomerMobileAndCustomerEmail(mobile,email).orElse(new Customer());
    }

    private Customer findByCustomerNumber(BigInteger customerNumber) {
        return customerRepository.findById(customerNumber).orElse(new Customer());
    }

    public GenericResponse createCustomer(CustomerCreateRequest request) {
        GenericResponse response= new GenericResponse();

        try{
            Customer createdCustomer= findCustomerExisting(request.getCustomerMobile(), request.getCustomerEmail());

            if ( null == createdCustomer.getCustomerEmail()) {
                Account account = new Account();
                //Creating Customer
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateOfBirth = dateFormat.parse(request.getCustomerDob());
                createdCustomer.setCustomerDob(dateOfBirth);
                createdCustomer.setCustomerEmail(request.getCustomerEmail());
                createdCustomer.setCustomerFirstName(request.getCustomerFirstName());
                createdCustomer.setCustomerMiddleName(request.getCustomerMiddleName());
                createdCustomer.setCustomerLastName(request.getCustomerLastName());
                createdCustomer.setCustomerMobile(request.getCustomerMobile());
                createdCustomer.setCustomerTin(request.getCustomerTin());
                createdCustomer.setCustomerSsn(request.getCustomerSsn());
                createdCustomer.setCustomerDrivingLicenseNumber(request.getCustomerDrivingLicenseNumber());
                createdCustomer.setCustomerPresentAddress(request.getCustomerPresentAddress());
                createdCustomer.setCustomerPermanentAddress(request.getCustomerPermanentAddress());
                createdCustomer.setCreatedTime(LocalDateTime.now());
                customerRepository.save(createdCustomer);

                //Creating Account
                account.setAccountType(Account.AccountType.valueOf(request.getAccountType()));
                account.setCreatedTime(LocalDateTime.now());
                account.setBalance(BigDecimal.ZERO);
                account.setAccountStatus(AccountStatus.ACTIVE.name());
                account.setCustomer(createdCustomer);
                account.setAccountName(request.getAccountName());
                accountRepository.save(account);


                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Customer created successfully");

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Account already exists !");
            }
            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }

    public GenericResponse editCustomer(CustomerEditRequest request) {
        GenericResponse response= new GenericResponse();

        try{
            BigInteger customerNumber = new BigInteger(request.getCustomerNumber());
            Customer customer =  findByCustomerNumber(customerNumber);

            if ( null != customer.getCustomerEmail()) {

                //Edit Customer
                if(null != request.getCustomerDob()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateOfBirth = dateFormat.parse(request.getCustomerDob());
                    customer.setCustomerDob(dateOfBirth);
                }
                if(null != request.getCustomerEmail()) {
                    customer.setCustomerEmail(request.getCustomerEmail());
                }
                if(null != request.getCustomerFirstName()) {
                    customer.setCustomerFirstName(request.getCustomerFirstName());
                }
                if(null != request.getCustomerMiddleName()) {
                    customer.setCustomerMiddleName(request.getCustomerMiddleName());
                }
                if(null != request.getCustomerLastName()) {
                    customer.setCustomerLastName(request.getCustomerLastName());
                }
                if(null != request.getCustomerMobile()) {
                    customer.setCustomerMobile(request.getCustomerMobile());
                }
                if(null != request.getCustomerTin()) {
                    customer.setCustomerTin(request.getCustomerTin());
                }
                if(null != request.getCustomerSsn()) {
                    customer.setCustomerSsn(request.getCustomerSsn());
                }
                if(null != request.getCustomerDrivingLicenseNumber()) {
                    customer.setCustomerDrivingLicenseNumber(request.getCustomerDrivingLicenseNumber());
                }
                if(null != request.getCustomerPresentAddress()) {
                    customer.setCustomerPresentAddress(request.getCustomerPresentAddress());
                }
                if(null != request.getCustomerPermanentAddress()) {
                    customer.setCustomerPermanentAddress(request.getCustomerPermanentAddress());
                }
                customer.setLastUpdatedTime(LocalDateTime.now());
                customerRepository.save(customer);

                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Customer updated successfully");

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Customer not found !");
            }
            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage("Failed to update customer !");
            return response;
        }
    }
}
