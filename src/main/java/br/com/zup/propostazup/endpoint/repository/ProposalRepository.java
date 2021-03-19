package br.com.zup.propostazup.endpoint.repository;

import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.enums.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    boolean existsByHashedDocument(String hashedDocument);

    List<Proposal> findAllByProposalStatusAndProposalAccountIsNull(ProposalStatus proposalStatus);
}
