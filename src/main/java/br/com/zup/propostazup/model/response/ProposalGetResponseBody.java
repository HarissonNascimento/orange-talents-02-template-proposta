package br.com.zup.propostazup.model.response;

import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.enums.ProposalStatus;

public class ProposalGetResponseBody {
    private Long id;
    private String name;
    private String email;
    private ProposalStatus proposalStatus;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public ProposalGetResponseBody toProposalGetResponseBody(Proposal proposal) {
        this.id = proposal.getId();
        this.name = proposal.getName();
        this.email = proposal.getEmail();
        this.proposalStatus = proposal.getProposalStatus();
        return this;
    }


}
