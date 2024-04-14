package com.br.loans.application.core.service.strategy;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.application.core.domain.CustomerDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoansPersonalStrategy implements LoansStrategy {


    @Override
    public boolean isApply(CustomerDomain customerDomain) {
        return isIncomeGreaterThanMin(customerDomain)
                || (customerDomain.income().compareTo(BigDecimal.valueOf(5000)) <= 0
                && customerDomain.age() < 30
                && customerDomain.location().equalsIgnoreCase("SP"));
    }


    @Override
    public LoanTypes getLoanType() {
        return LoanTypes.PERSONAL;
    }

    private static boolean isIncomeGreaterThanMin(CustomerDomain customerDomain) {
        return customerDomain.income().compareTo(BigDecimal.valueOf(3000)) < 0;
    }
}
