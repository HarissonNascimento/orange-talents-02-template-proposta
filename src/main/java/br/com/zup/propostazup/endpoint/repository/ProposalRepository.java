package br.com.zup.propostazup.endpoint.repository;

import br.com.zup.propostazup.model.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    boolean existsByDocument(String document);

}
