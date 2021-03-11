package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class AccountInstallments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String installmentsId;
    private Integer amount;
    private BigDecimal value;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountInstallments(String id,
                               Integer amount,
                               BigDecimal value) {

        installmentsId = id;
        this.amount = amount;
        this.value = value;
    }

    @Deprecated
    protected AccountInstallments() {
    }

    public Long getId() {
        return id;
    }

    public String getInstallmentsId() {
        return installmentsId;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
