package com.br.loans.application.core.service.strategy;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.application.core.domain.CustomerDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoansConsignmentStrategy implements LoansStrategy {


    @Override
    public boolean isApply(CustomerDomain customerDomain) {
        return customerDomain.income().compareTo(BigDecimal.valueOf(5000)) >= 0;
    }


    @Override
    public LoanTypes getLoanType() {
        return LoanTypes.CONSIGNMENT;
    }

}
