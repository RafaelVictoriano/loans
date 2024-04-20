package com.br.loans.application.core.service.strategy;

import com.br.loans.application.core.domain.CustomerDomain;
import com.br.loans.application.core.domain.LoanTypes;

public interface LoansStrategy {

    boolean isApply(CustomerDomain customerDomain);

    LoanTypes getLoanType();


}
