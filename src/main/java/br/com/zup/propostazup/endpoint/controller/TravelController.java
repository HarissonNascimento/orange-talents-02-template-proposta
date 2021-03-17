package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.enums.StatusTravel;
import br.com.zup.propostazup.client.account.request.CardTravelPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardTravelPostResponseBody;
import br.com.zup.propostazup.config.header.ClientHostResolver;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.domain.TravelNotice;
import br.com.zup.propostazup.model.request.TravelPostRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Account account;
    @Autowired
    private ClientHostResolver clientHostResolver;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> travelNotice(@PathVariable("id") Long id,
                                          @RequestHeader("user-agent") String agent,
                                          @RequestBody @Valid TravelPostRequestBody travelPostRequestBody){

        Optional<ProposalAccount> optionalProposalAccount = Optional.ofNullable(entityManager.find(ProposalAccount.class, id));

        if (optionalProposalAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProposalAccount proposalAccount = optionalProposalAccount.get();

        CardTravelPostRequestBody clientRequestBody = new CardTravelPostRequestBody();
        clientRequestBody.setDestiny(travelPostRequestBody.getDestiny());
        clientRequestBody.setValidUntil(travelPostRequestBody.getReturnIn());

        CardTravelPostResponseBody clientResponseBody = account.clientTravelNotice(proposalAccount.getIdAccount(), clientRequestBody);

        if (clientResponseBody.getResult().equals(StatusTravel.FALHA)){
            return ResponseEntity.badRequest().build();
        }

        TravelNotice travelNotice = new TravelNotice(clientRequestBody.getValidUntil(), clientRequestBody.getDestiny(), clientHostResolver.getClientIP(), agent, proposalAccount);

        proposalAccount.updateTravels(travelNotice);

        entityManager.persist(proposalAccount);

        return ResponseEntity.ok().build();

    }
}
