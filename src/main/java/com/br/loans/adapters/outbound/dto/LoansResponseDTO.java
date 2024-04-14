package com.br.loans.adapters.outbound.dto;

import java.util.List;

public record LoansResponseDTO(String customer,
                               List<LoansDTO> loans) {
}
