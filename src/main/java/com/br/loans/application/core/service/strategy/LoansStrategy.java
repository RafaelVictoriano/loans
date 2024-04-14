package com.br.loans.application.core.service.strategy;

import com.br.loans.adapters.enumeration.LoanTypes;
import com.br.loans.application.core.domain.CustomerDomain;

public interface LoansStrategy {

    boolean isApply(CustomerDomain customerDomain);

    LoanTypes getLoanType();
}
