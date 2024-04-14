package com.br.loans.adapters.controller;

import com.br.loans.adapters.inbound.dto.CustomerDataRequest;
import com.br.loans.adapters.inbound.mapper.customer.CustomerMapper;
import com.br.loans.adapters.outbound.dto.LoansResponseDTO;
import com.br.loans.application.core.service.LoansService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customer-loans")
public class LoansController {

    private final LoansService loansService;
    private final CustomerMapper customerMapper;

    public LoansController(LoansService loansService, CustomerMapper customerMapper) {
        this.loansService = loansService;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    public ResponseEntity<LoansResponseDTO> getLoans(@RequestBody @Valid CustomerDataRequest customerDataRequest){
        final var loans = this.loansService.getLoans(customerMapper.toDomain(customerDataRequest));
        return ResponseEntity.ok(new LoansResponseDTO(customerDataRequest.name(), loans));
    }
}
