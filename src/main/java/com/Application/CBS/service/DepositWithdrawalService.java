package com.bankcore.service;

import com.bankcore.dto.GenericResponse;
import com.bankcore.dto.TransactionGenericRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepositWithdrawalService extends BaseTransactionService{
    GenericResponse response= new GenericResponse();

    @Override
    public GenericResponse processTranRequest(TransactionGenericRequest request) {
        GenericResponse response= new GenericResponse();
        return requestTransaction(request);
    }
}
