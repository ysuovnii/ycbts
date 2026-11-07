package com.bankcore.service;

import com.bankcore.dto.*;
import com.bankcore.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranConfigurationService {

    @Autowired
    EntityFinderService entityFinderService;

    public GenericResponse createFeeDistributor(FeeDistributorCreateRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            Account account = entityFinderService.findByAccountNumber(request.getDistributorAccountNumber());
            if(null != account.getAccountNumber()) {
                FeeDistributor feeDistributor = entityFinderService.findFeeDistributor(request.getDistributorAccountNumber());

                if(null != feeDistributor.getDistributorAccountNumber()) {
                    response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                    response.setResponseMessage("Fee Distributor already exists!");
                    return response;
                } else {
                    feeDistributor.setActive(true);
                    feeDistributor.setCreatedTime(LocalDateTime.now());
                    feeDistributor.setDistributorName(request.getDistributorName());
                    feeDistributor.setDistributorAccountNumber(request.getDistributorAccountNumber());
                    feeDistributor.setDescription(request.getDescription());
                    entityFinderService.feeDistributorRepository.save(feeDistributor);

                    response.setResponseCode(HttpStatus.OK.value());
                    response.setResponseMessage("Fee Distributor created successfully.");
                }

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Account couldn't be found to create a Fee Distributor.");
            }
            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }

    public GenericResponse createTranFeeDistribution(TranFeeDistributionCreateRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            FeeDistributor feeDistributor = entityFinderService.findFeeDistributor(request.getFeeDistributorAccountNumber());
            TranFee tranFee = entityFinderService.findTranFeeById(request.getTranFeeId());

            if ( null != feeDistributor.getDistributorAccountNumber()) {
                List<TranFeeDistribution> tranFeeDistributions = entityFinderService.findAllDistributions(tranFee);

                for(TranFeeDistribution tranFeeDistribution: tranFeeDistributions) {
                    if(null == tranFeeDistribution.getDistributorAccountNumber()) {
                        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                        response.setResponseMessage("Tran Fee Distribution not found!");
                        return response;
                    }
                    if (tranFeeDistribution.getTranFeeId() == Integer.parseInt(request.getTranFeeId())) {
                        response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                        response.setResponseMessage("Tran Fee Distribution already exists!");
                        return response;
                    }
                }

                TranFeeDistribution newTranFeeDistribution = new TranFeeDistribution();
                newTranFeeDistribution.setActive(request.getIsActive());
                newTranFeeDistribution.setTranFee(tranFee);
                newTranFeeDistribution.setCreatedTime(LocalDateTime.now());
                newTranFeeDistribution.setDistributorName(feeDistributor.getDistributorName());
                newTranFeeDistribution.setFeePercentage(Double.parseDouble(request.getFeePercentage()));
                newTranFeeDistribution.setDistributorAccountNumber(feeDistributor.getDistributorAccountNumber());
                newTranFeeDistribution.setDescription(request.getDescription());
                newTranFeeDistribution.setFeeDistributor(feeDistributor);
                entityFinderService.tranFeeDistributionRepository.save(newTranFeeDistribution);

                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Tran Fee Distribution created successfully.");

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Fee Distributor not found");
            }
            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }

    public GenericResponse createTranType(TranTypeCreateRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            TranType tranType = entityFinderService.findTranTypeByTypeCode(request.getTypeCode());
            if(null != tranType.getTypeCode()) {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Transaction type already exists!");
            } else {
                tranType.setActive(request.getIsActive());
                tranType.setTypeCode(TranType.TypeCode.valueOf(request.getTypeCode()));
                tranType.setCreatedTime(LocalDateTime.now());
                tranType.setDescription(request.getDescription());
                tranType.setFeeApplicable(request.getIsFeeApplicable());
                tranType.setReceiverAccountType(request.getReceiverAccountType());
                tranType.setSenderAccountType(request.getSenderAccountType());
                tranType.setReceiverAccountNumber(request.getReceiverAccountNumber());
                tranType.setSenderAccountNumber(request.getSenderAccountNumber());
                tranType.setTransactionGroup(request.getTransactionGroup());
                entityFinderService.tranTypeRepository.save(tranType);
                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Transaction type successfully created.");

            }
            return response;

        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            //response.setResponseMessage("Failed to create Transaction type.");
            response.setResponseMessage(e.getMessage());

            return response;

        }
    }

    public GenericResponse createTranFee(TranFeeCreateRequest request) {
        GenericResponse response = new GenericResponse();
        try {
            TranType tranType = entityFinderService.findTranTypeByTypeCode(request.getTranTypeCode());

            if(null != tranType.getTypeCode()) {
                TranFee tranFee = new TranFee();
                tranFee.setTranType(tranType);
                tranFee.setCreatedTime(LocalDateTime.now());
                tranFee.setDescription(request.getDescription());

                response.setResponseCode(HttpStatus.OK.value());
                response.setResponseMessage("Transaction fee successfully created.");

                if(request.getIsFeeAmountFixed()) {
                    tranFee.setFeeAmountFixed(true);
                    tranFee.setFeeFixedAmount(request.getFeeFixedAmount());
                    entityFinderService.tranFeeRepository.save(tranFee);
                    return response;
                }
                if (request.getIsFeeAmountPercentage()) {
                    tranFee.setFeeAmountPercentage(true);
                    tranFee.setFeePercentageAmount(request.getFeePercentageAmount());
                    if (request.getIsMaximumApplicable()) {
                        tranFee.setMaximumApplicable(true);
                        tranFee.setFeeMaximumAmount(request.getFeeMaximumAmount());
                    }
                    entityFinderService.tranFeeRepository.save(tranFee);
                    return response;
                }

            } else {
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage("Transaction type code doesn't exists!");
            }

            return response;
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseMessage(e.getMessage());
            return response;
        }
    }
}