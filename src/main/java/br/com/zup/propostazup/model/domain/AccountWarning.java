package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class AccountWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate validUntil;
    private String destiny;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountWarning(LocalDate validUntil,
                          String destiny) {
        this.validUntil = validUntil;
        this.destiny = destiny;
    }

    @Deprecated
    protected AccountWarning() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public String getDestiny() {
        return destiny;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
