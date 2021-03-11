package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class AccountBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idBlock;
    private LocalDateTime blockedIn;
    private String responsibleSystem;
    private boolean active;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public AccountBlock(String id,
                        LocalDateTime blockedIn,
                        String responsibleSystem,
                        boolean active) {

        idBlock = id;
        this.blockedIn = blockedIn;
        this.responsibleSystem = responsibleSystem;
        this.active = active;
    }

    @Deprecated
    protected AccountBlock() {
    }

    public Long getId() {
        return id;
    }

    public String getIdBlock() {
        return idBlock;
    }

    public LocalDateTime getBlockedIn() {
        return blockedIn;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public boolean isActive() {
        return active;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
