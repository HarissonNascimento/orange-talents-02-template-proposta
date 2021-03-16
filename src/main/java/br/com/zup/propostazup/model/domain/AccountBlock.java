package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class AccountBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String remoteAddress;
    private LocalDateTime blockedIn;
    private String responsibleSystem;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountBlock(String remoteAddress,
                        LocalDateTime blockedIn,
                        String responsibleSystem) {

        this.remoteAddress = remoteAddress;
        this.blockedIn = blockedIn;
        this.responsibleSystem = responsibleSystem;
    }

    public AccountBlock(String remoteAddress,
                        LocalDateTime blockedIn,
                        String responsibleSystem,
                        ProposalAccount proposalAccount) {

        this.remoteAddress = remoteAddress;
        this.blockedIn = blockedIn;
        this.responsibleSystem = responsibleSystem;
        this.proposalAccount = proposalAccount;
    }

    @Deprecated
    protected AccountBlock() {
    }

    public Long getId() {
        return id;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public LocalDateTime getBlockedIn() {
        return blockedIn;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
