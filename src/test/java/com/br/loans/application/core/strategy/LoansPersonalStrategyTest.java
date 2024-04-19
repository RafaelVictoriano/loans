package com.br.loans.application.core.strategy;

import com.br.loans.application.core.domain.CustomerDomain;
import com.br.loans.application.core.service.strategy.LoansPersonalStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class LoansPersonalStrategyTest {

    @InjectMocks
    private LoansPersonalStrategy loansPersonalStrategy;


    @Test
    void shouldEqualsTrueWhenCallIsApplyBecauseIncomeIsLessThreeThousand() {
        final var customerDomain = new CustomerDomain(20,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(1600),
                "SP");

        Assertions.assertTrue(loansPersonalStrategy.isApply(customerDomain));

    }

    @Test
    void shouldEqualsTrueWhenCallIsApplyBecauseIncomeIsLessFiveThousandAndAgeThreeHundredAndLocationIsSP() {
        final var customerDomain = new CustomerDomain(26,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(4000),
                "SP");

        Assertions.assertTrue(loansPersonalStrategy.isApply(customerDomain));
    }

    @Test
    void shouldEqualsFalseWhenCallIsApplyBecauseIncomeIsNotLessThanFiveThousand() {
        final var customerDomain = new CustomerDomain(26,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(6000),
                "SP");

        Assertions.assertFalse(loansPersonalStrategy.isApply(customerDomain));
    }

    @Test
    void shouldEqualsFalseWhenCallIsApplyBecauseLocationIsNotSP() {
        final var customerDomain = new CustomerDomain(26,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(4000),
                "RJ");

        Assertions.assertFalse(loansPersonalStrategy.isApply(customerDomain));
    }

    @Test
    void shouldEqualsFalseWhenCallIsApplyBecauseAgeIsNotLessThanThirty() {
        final var customerDomain = new CustomerDomain(30,
                "46853215489",
                "José almeida",
                BigDecimal.valueOf(4000),
                "SP");

        Assertions.assertFalse(loansPersonalStrategy.isApply(customerDomain));
    }
}
