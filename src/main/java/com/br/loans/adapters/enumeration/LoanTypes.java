package com.br.loans.adapters.enumeration;

public enum LoanTypes {

    PERSONAL(4.),
    GUARANTEED(3.),
    CONSIGNMENT(2.);


    private final Double interestRate;

    LoanTypes(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
