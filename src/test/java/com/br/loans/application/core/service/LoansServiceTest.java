package com.br.loans.application.core.service;

import com.br.loans.application.core.domain.LoanTypes;
import com.br.loans.adapters.inbound.entity.LoansEntity;
import com.br.loans.adapters.outbound.dto.LoansDTO;
import com.br.loans.adapters.outbound.repository.LoansRepository;
import com.br.loans.application.core.domain.CustomerDomain;
import com.br.loans.application.core.service.strategy.LoansConsignmentStrategy;
import com.br.loans.application.core.service.strategy.LoansGuaranteedStrategy;
import com.br.loans.application.core.service.strategy.LoansPersonalStrategy;
import com.br.loans.application.core.service.strategy.LoansStrategy;
import com.br.loans.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoansServiceTest {

    @InjectMocks
    private LoansService loansService;

    @Mock
    private LoansRepository loansRepository;

    @Spy
    private LoansConsignmentStrategy loansConsignmentStrategy;

    @Spy
    private LoansGuaranteedStrategy loansGuaranteedStrategy;

    @Spy
    private LoansPersonalStrategy loansPersonalStrategy;

    @BeforeEach
    public void setUp() {
        List<LoansStrategy> loansStrategies = Arrays.asList(loansConsignmentStrategy, loansPersonalStrategy, loansGuaranteedStrategy);
        ReflectionTestUtils.setField(loansService, "loansStrategies", loansStrategies);
    }


    @Test
    void shouldThrowNotFoundExceptionWhenGetLoansTypes() {
        doReturn(Boolean.FALSE).when(loansConsignmentStrategy).isApply(any());
        doReturn(Boolean.FALSE).when(loansPersonalStrategy).isApply(any());
        doReturn(Boolean.FALSE).when(loansGuaranteedStrategy).isApply(any());

        when(loansRepository.findByLoansTypeIn(any())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(NotFoundException.class, () -> loansService.getLoans(new CustomerDomain(18,
                        "98734563562",
                        "Rafael",
                        BigDecimal.valueOf(23),
                        "SP")),
                "Loans types not found");
    }

    @Test
    void shouldEqualsConsignmentWhenGetLoansTypes() {
        final var loansEntity = new LoansEntity();
        loansEntity.setLoansType(LoanTypes.CONSIGNMENT);
        loansEntity.setInterestRate(2.);

        doReturn(Boolean.TRUE).when(loansConsignmentStrategy).isApply(any());
        doReturn(Boolean.FALSE).when(loansPersonalStrategy).isApply(any());
        doReturn(Boolean.FALSE).when(loansGuaranteedStrategy).isApply(any());

        when(loansRepository.findByLoansTypeIn(eq(List.of(LoanTypes.CONSIGNMENT)))).thenReturn(List.of(loansEntity));

        final var loans = loansService.getLoans(new CustomerDomain(18,
                "98734563562",
                "Rafael",
                BigDecimal.valueOf(23),
                "SP"));

        assertEquals(new LoansDTO(LoanTypes.CONSIGNMENT, 2.), loans.get(0));
        assertEquals(1, loans.size());
    }


    @Test
    void shouldEqualsConsignmentPersonalWhenGetLoansTypes() {
        final var loansEntityConsignment = new LoansEntity();
        loansEntityConsignment.setLoansType(LoanTypes.CONSIGNMENT);
        loansEntityConsignment.setInterestRate(2.);

        final var loansEntityPersonal = new LoansEntity();
        loansEntityPersonal.setLoansType(LoanTypes.PERSONAL);
        loansEntityPersonal.setInterestRate(4.);

        doReturn(Boolean.TRUE).when(loansConsignmentStrategy).isApply(any());
        doReturn(Boolean.TRUE).when(loansPersonalStrategy).isApply(any());
        doReturn(Boolean.FALSE).when(loansGuaranteedStrategy).isApply(any());

        when(loansRepository.findByLoansTypeIn(eq(List.of(LoanTypes.CONSIGNMENT, LoanTypes.PERSONAL))))
                .thenReturn(List.of(loansEntityPersonal, loansEntityConsignment));

        final var loans = loansService.getLoans(new CustomerDomain(18,
                "98734563562",
                "Rafael",
                BigDecimal.valueOf(23),
                "SP"));

        assertEquals(new LoansDTO(LoanTypes.PERSONAL, 4.), loans.get(0));
        assertEquals(new LoansDTO(LoanTypes.CONSIGNMENT, 2.), loans.get(1));
        assertEquals(2, loans.size());
    }

    @Test
    void shouldEqualsConsignmentPersonalGuarentedWhenGetLoansTypes() {
        final var loansEntityConsignment = new LoansEntity();
        loansEntityConsignment.setLoansType(LoanTypes.CONSIGNMENT);
        loansEntityConsignment.setInterestRate(2.);

        final var loansEntityPersonal = new LoansEntity();
        loansEntityPersonal.setLoansType(LoanTypes.PERSONAL);
        loansEntityPersonal.setInterestRate(4.);

        final var loansEntityGuaranteed = new LoansEntity();
        loansEntityGuaranteed.setLoansType(LoanTypes.GUARANTEED);
        loansEntityGuaranteed.setInterestRate(3.);

        doReturn(Boolean.TRUE).when(loansConsignmentStrategy).isApply(any());
        doReturn(Boolean.TRUE).when(loansPersonalStrategy).isApply(any());
        doReturn(Boolean.TRUE).when(loansGuaranteedStrategy).isApply(any());

        when(loansRepository.findByLoansTypeIn(eq(List.of(LoanTypes.CONSIGNMENT, LoanTypes.PERSONAL, LoanTypes.GUARANTEED))))
                .thenReturn(List.of(loansEntityPersonal, loansEntityConsignment, loansEntityGuaranteed));

        final var loans = loansService.getLoans(new CustomerDomain(18,
                "98734563562",
                "Rafael",
                BigDecimal.valueOf(23),
                "SP"));

        assertEquals(new LoansDTO(LoanTypes.PERSONAL, 4.), loans.get(0));
        assertEquals(new LoansDTO(LoanTypes.CONSIGNMENT, 2.), loans.get(1));
        assertEquals(new LoansDTO(LoanTypes.GUARANTEED, 3.), loans.get(2));
        assertEquals(3, loans.size());
    }
}
