package com.br.loans.adapters.outbound.dto;

import com.br.loans.application.core.domain.LoanTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LoansDTO(LoanTypes type,
                       @JsonProperty("interest_rate") Double interestRate) {
}
