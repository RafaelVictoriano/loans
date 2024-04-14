package com.br.loans.application.core.domain;

import java.math.BigDecimal;

public record CustomerDomain(Integer age,
                             String cpf,
                             String name,
                             BigDecimal income,
                             String location
) {
}
