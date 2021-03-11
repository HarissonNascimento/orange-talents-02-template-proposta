package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ProposalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idAccount;
    private LocalDateTime issuedOn;
    private String holder;
    @OneToMany(mappedBy = "proposalAccount", cascade = CascadeType.ALL)
    private List<AccountBlock> accountBlocks;
    @OneToMany(mappedBy = "proposalAccount", cascade = CascadeType.ALL)
    private List<AccountWarning> accountWarnings;
    @OneToMany(mappedBy = "proposalAccount", cascade = CascadeType.ALL)
    private List<AccountWallet> accountWallet;
    @OneToMany(mappedBy = "proposalAccount", cascade = CascadeType.ALL)
    private List<AccountInstallments> accountInstallments;
    private BigDecimal credit;
    @OneToOne(cascade = CascadeType.ALL)
    private AccountRenegotiation accountRenegotiation;
    @OneToOne(cascade = CascadeType.ALL)
    private AccountDueDate accountDueDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Proposal proposal;

    public ProposalAccount(String idAccount,
                           LocalDateTime issuedOn,
                           String holder,
                           List<AccountBlock> accountBlocks,
                           List<AccountWarning> accountWarnings,
                           List<AccountWallet> accountWallet,
                           List<AccountInstallments> accountInstallments,
                           BigDecimal credit,
                           AccountRenegotiation accountRenegotiation,
                           AccountDueDate accountDueDate,
                           Proposal proposal) {

        this.idAccount = idAccount;
        this.issuedOn = issuedOn;
        this.holder = holder;
        this.accountBlocks = accountBlocks;
        this.accountWarnings = accountWarnings;
        this.accountWallet = accountWallet;
        this.accountInstallments = accountInstallments;
        this.credit = credit;
        this.accountRenegotiation = accountRenegotiation;
        this.accountDueDate = accountDueDate;
        this.proposal = proposal;
    }

    @Deprecated
    protected ProposalAccount() {
    }

    public Long getId() {
        return id;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public String getHolder() {
        return holder;
    }

    public List<AccountBlock> getAccountBlocks() {
        return accountBlocks;
    }

    public List<AccountWarning> getAccountWarnings() {
        return accountWarnings;
    }

    public List<AccountWallet> getAccountWallet() {
        return accountWallet;
    }

    public List<AccountInstallments> getAccountInstallments() {
        return accountInstallments;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public AccountRenegotiation getAccountRenegotiation() {
        return accountRenegotiation;
    }

    public AccountDueDate getAccountDueDate() {
        return accountDueDate;
    }

    public Proposal getProposal() {
        return proposal;
    }
}
