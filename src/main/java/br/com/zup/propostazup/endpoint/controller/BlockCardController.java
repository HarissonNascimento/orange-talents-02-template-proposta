package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.request.CardBlockingPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardBlockingPostResponseBody;
import br.com.zup.propostazup.config.header.ClientHostResolver;
import br.com.zup.propostazup.model.domain.AccountBlock;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.enums.StatusCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/block")
public class BlockCardController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Account account;
    @Autowired
    private ClientHostResolver clientHostResolver;

    @PostMapping("{id}")
    @Transactional
    public ResponseEntity<?> blockCard(@PathVariable("id") Long id,
                                       @RequestHeader("user-agent") String agent) {

        Optional<ProposalAccount> optionalProposalAccount = Optional.ofNullable(entityManager.find(ProposalAccount.class, id));

        if (optionalProposalAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProposalAccount proposalAccount = optionalProposalAccount.get();

        if (proposalAccount.getStatusCard().equals(StatusCard.BLOQUEADO)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        CardBlockingPostRequestBody clientRequestBody = new CardBlockingPostRequestBody();
        clientRequestBody.setResponsibleSystem(agent);

        CardBlockingPostResponseBody clientResponseBody = account.cardBlocking(proposalAccount.getIdAccount(), clientRequestBody);

        if (clientResponseBody.getResult().equals(StatusCard.FALHA)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        AccountBlock accountBlock = new AccountBlock(clientHostResolver.getClientIP(), LocalDateTime.now(), agent, proposalAccount);

        proposalAccount.lockCard(accountBlock, clientResponseBody.getResult());

        entityManager.merge(proposalAccount);

        return ResponseEntity.ok().build();
    }
}
