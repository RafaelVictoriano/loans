package com.br.loans.adapters.inbound.mapper.customer;

import com.br.loans.adapters.inbound.dto.CustomerDataRequest;
import com.br.loans.application.core.domain.CustomerDomain;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDomain toDomain(CustomerDataRequest customerDataRequest) {
        return new CustomerDomain(customerDataRequest.age(),
                customerDataRequest.cpf(),
                customerDataRequest.name(),
                customerDataRequest.income(),
                customerDataRequest.location());
    }
}
