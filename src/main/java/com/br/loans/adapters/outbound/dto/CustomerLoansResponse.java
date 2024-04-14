package com.br.loans.adapters.outbound.dto;

import java.util.List;

public record CustomerLoansResponse(String customer,
                                    List<LoansDTO> loans
) {
}
