package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String fingerprint;
    @NotNull
    @Valid
    @ManyToOne
    private ProposalAccount proposalAccount;

    public Biometry(@NotBlank String fingerprint,
                    @NotNull ProposalAccount proposalAccount) {
        this.fingerprint = fingerprint;
        this.proposalAccount = proposalAccount;
    }

    @Deprecated
    protected Biometry() {
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
