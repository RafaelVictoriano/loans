package com.br.loans.adapters.inbound.entity;

import com.br.loans.adapters.enumeration.LoanTypes;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "tb_loans")
@Entity
public class LoansEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "loan_tp")
    @Enumerated(EnumType.STRING)
    private LoanTypes loansType;
    @Column(name = "interest_rt")
    private Double interestRate;
    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanTypes getLoansType() {
        return loansType;
    }

    public void setLoansType(LoanTypes loansType) {
        this.loansType = loansType;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "LoansEntity{" +
                "id=" + id +
                ", loansType=" + loansType +
                ", interestRate=" + interestRate +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}
