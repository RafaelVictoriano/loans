package com.br.loans.application.ports.inbound;

import com.br.loans.adapters.outbound.dto.LoansDTO;
import com.br.loans.application.core.domain.CustomerDomain;

import java.util.List;

public interface LoansPort {

    List<LoansDTO> getLoans(CustomerDomain customerDomain);
}
