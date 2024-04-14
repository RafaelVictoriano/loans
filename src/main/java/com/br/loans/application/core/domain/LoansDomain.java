package com.br.loans.application.core.domain;

import com.br.loans.adapters.enumeration.LoanTypes;

public record LoansDomain(LoanTypes loanTypes,
                          Double interestRate) {
}
