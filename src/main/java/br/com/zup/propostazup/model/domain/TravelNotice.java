package br.com.zup.propostazup.model.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class TravelNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate validUntil;
    private String destiny;
    private LocalDateTime reportedOn;
    private String remoteAddress;
    private String responsibleSystem;
    @ManyToOne
    private ProposalAccount proposalAccount;

    public TravelNotice(LocalDate validUntil,
                          String destiny) {
        this.validUntil = validUntil;
        this.destiny = destiny;
    }

    public TravelNotice(LocalDate validUntil, String destiny, String remoteAddress, String responsibleSystem, ProposalAccount proposalAccount) {
        this.validUntil = validUntil;
        this.destiny = destiny;
        this.remoteAddress = remoteAddress;
        this.responsibleSystem = responsibleSystem;
        this.proposalAccount = proposalAccount;
        reportedOn = LocalDateTime.now();
    }

    @Deprecated
    protected TravelNotice() {
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

    public LocalDateTime getReportedOn() {
        return reportedOn;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }
}
