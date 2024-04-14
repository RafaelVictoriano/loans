package com.br.loans.application.core.service;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.adapters.inbound.entity.LoansEntity;
import com.br.loans.adapters.outbound.dto.LoansDTO;
import com.br.loans.adapters.outbound.repository.LoansRepository;
import com.br.loans.application.core.domain.CustomerDomain;
import com.br.loans.application.core.service.strategy.LoansStrategy;
import com.br.loans.application.ports.inbound.LoansPort;
import com.br.loans.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoansService implements LoansPort {

    private final List<LoansStrategy> loansStrategies;
    private final LoansRepository loansRepository;
    private static final Logger log = LogManager.getLogger(LoansService.class);

    public LoansService(List<LoansStrategy> loansStrategies, LoansRepository loansRepository) {
        this.loansStrategies = loansStrategies;
        this.loansRepository = loansRepository;
    }

    @Override
    public List<LoansDTO> getLoans(CustomerDomain customerDomain) {
        final var loansTypes = this.loansStrategies.stream().
                filter(loansStrategy -> loansStrategy.isApply(customerDomain))
                .map(LoansStrategy::getLoanType)
                .toList();

        return this.getLoansByLoansTypes(loansTypes)
                .stream()
                .map(loansEntity -> new LoansDTO(loansEntity.getLoansType(), loansEntity.getInterestRate()))
                .collect(Collectors.toList());

    }

    private List<LoansEntity> getLoansByLoansTypes(final Collection<LoanTypes> loanTypes) {
        final var loasnEntities = loansRepository.findByLoansTypeIn(loanTypes);
        log.info("Loans types retrieved, loansTypes:{}", loasnEntities);

        if (loasnEntities.isEmpty()) {
            throw new NotFoundException("Loans types not found");
        }
        return loasnEntities;
    }


}
