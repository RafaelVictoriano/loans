package com.br.loans.application.core.service.strategy;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.application.core.domain.CustomerDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoansPersonalStrategy implements LoansStrategy {


    @Override
    public boolean isApply(CustomerDomain customerDomain) {
        return isIncomeLessThanThreeThousand(customerDomain.income())
                || (isIncomeEqualsOrLessThanFiveThousand(customerDomain.income())
                && isAgeLessThanThirty(customerDomain.age())
                && isLocationIsSP(customerDomain.location()));
    }


    @Override
    public LoanTypes getLoanType() {
        return LoanTypes.PERSONAL;
    }

    private static boolean isIncomeLessThanThreeThousand(BigDecimal income) {
        return income.compareTo(BigDecimal.valueOf(3000)) < 0;
    }

    private static boolean isIncomeEqualsOrLessThanFiveThousand(BigDecimal income) {
        return income.compareTo(BigDecimal.valueOf(5000)) <= 0;
    }

    private static boolean isAgeLessThanThirty(Integer age) {
        return age < 30;
    }

    private static boolean isLocationIsSP(String location) {
        return location.equalsIgnoreCase("SP");
    }
}
