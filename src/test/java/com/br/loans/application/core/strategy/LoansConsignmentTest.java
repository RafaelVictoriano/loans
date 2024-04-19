package com.br.loans.application.core.strategy;

import com.br.loans.application.core.domain.CustomerDomain;
import com.br.loans.application.core.service.strategy.LoansConsignmentStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class LoansConsignmentTest {

    @InjectMocks
    private LoansConsignmentStrategy loansConsignmentStrategy;


    @Test
    void shouldEqualsTrueWhenCallIsApplyBecauseIncomeIsGreaterThanFiveThousand() {
        final var customerDomain = new CustomerDomain(20,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(6000),
                "SP");

        Assertions.assertTrue(loansConsignmentStrategy.isApply(customerDomain));

    }

    @Test
    void shouldEqualsTrueWhenCallIsApplyBecauseIncomeIsEqualsFiveThousandAndAgeThreeHundredAndLocationIsSP() {
        final var customerDomain = new CustomerDomain(26,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(5000),
                "SP");

        Assertions.assertTrue(loansConsignmentStrategy.isApply(customerDomain));
    }

    @Test
    void shouldEqualsFalseWhenCallIsApplyBecauseIncomeIsLessThanFiveThousand() {
        final var customerDomain = new CustomerDomain(26,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(4000),
                "RJ");

        Assertions.assertFalse(loansConsignmentStrategy.isApply(customerDomain));
    }


}
