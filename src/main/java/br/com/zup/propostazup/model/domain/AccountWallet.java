package br.com.zup.propostazup.model.domain;

import br.com.zup.propostazup.model.enums.WalletType;

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
    @Enumerated(EnumType.STRING)
    private WalletType emitter;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountWallet(String id,
                         String email,
                         LocalDateTime associatedIn,
                         WalletType emitter) {

        walletId = id;
        this.email = email;
        this.associatedIn = associatedIn;
        this.emitter = emitter;
    }

    @Deprecated
    protected AccountWallet() {
    }

    public AccountWallet(String id,
                         String email,
                         LocalDateTime now,
                         WalletType wallet,
                         ProposalAccount proposalAccount) {
        walletId = id;
        this.email = email;
        this.associatedIn = now;
        this.emitter = wallet;
        this.proposalAccount = proposalAccount;

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

    public WalletType getEmitter() {
        return emitter;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
