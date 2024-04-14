package com.br.loans.adapters.inbound.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record CustomerDataRequest(@NotNull @Min(1) Integer age,
                                  @NotNull @CPF String cpf,
                                  @NotBlank String name,
                                  @NotNull BigDecimal income,
                                  @NotNull String location
                          ) {
}
