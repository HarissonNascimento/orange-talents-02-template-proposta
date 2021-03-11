package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccountWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String walletId;
    private String email;
    private LocalDateTime associatedIn;
    private String emitter;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountWallet(String id,
                         String email,
                         LocalDateTime associatedIn,
                         String emitter) {

        walletId = id;
        this.email = email;
        this.associatedIn = associatedIn;
        this.emitter = emitter;
    }

    @Deprecated
    protected AccountWallet() {
    }

    public Long getId() {
        return id;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociatedIn() {
        return associatedIn;
    }

    public String getEmitter() {
        return emitter;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
