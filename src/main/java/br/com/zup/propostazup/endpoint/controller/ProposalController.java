package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.data_analysis.DataAnalysis;
import br.com.zup.propostazup.client.data_analysis.request.DataAnalysisPostRequestBody;
import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import br.com.zup.propostazup.endpoint.repository.ProposalRepository;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.request.NewProposalPostRequestBody;
import br.com.zup.propostazup.model.response.ProposalGetResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/proposal")
public class ProposalController {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private DataAnalysis dataAnalysis;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createNewProposal(@RequestBody @Valid NewProposalPostRequestBody newProposalPostRequestBody) {

        Proposal proposal = newProposalPostRequestBody.toProposal();

        if (proposalRepository.existsByHashedDocument(proposal.getHashedDocument())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        proposalRepository.save(proposal);

        DataAnalysisPostResponseBody dataAnalysisResponse = dataAnalysis.sendData(
                new DataAnalysisPostRequestBody(proposal.getDocument(), proposal.getName(), proposal.getId()));

        proposal.updateProposalStatus(dataAnalysisResponse);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProposal(@PathVariable(value = "id") Long id) {

        Optional<Proposal> optionalProposal = proposalRepository.findById(id);

        if (optionalProposal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ProposalGetResponseBody().toProposalGetResponseBody(optionalProposal.get()));
    }
}
