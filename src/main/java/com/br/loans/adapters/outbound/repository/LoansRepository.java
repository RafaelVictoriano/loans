package com.br.loans.adapters.outbound.repository;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.adapters.inbound.entity.LoansEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LoansRepository extends JpaRepository<LoansEntity, Long> {

    List<LoansEntity> findByLoansTypeIn(Collection<LoanTypes> loansType);
}
