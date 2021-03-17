package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.enums.StatusWallet;
import br.com.zup.propostazup.client.account.request.CardWalletPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardWalletPostResponseBody;
import br.com.zup.propostazup.model.domain.AccountWallet;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.request.WalletPostRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Account account;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> associateWallet(@PathVariable("id") Long id,
                                             @RequestBody @Valid WalletPostRequestBody walletPostRequestBody){

        Optional<ProposalAccount> optionalProposalAccount = Optional.ofNullable(entityManager.find(ProposalAccount.class, id));

        if (optionalProposalAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProposalAccount proposalAccount = optionalProposalAccount.get();

        Optional<AccountWallet> optionalAccountWallet = proposalAccount.getAccountWallet()
                .stream().filter(w -> w.getEmitter().equals(walletPostRequestBody.getWalletType())).findAny();

        if (optionalAccountWallet.isPresent()){
            return  ResponseEntity.unprocessableEntity().build();
        }

        CardWalletPostRequestBody clientRequestBody = new CardWalletPostRequestBody();
        clientRequestBody.setEmail(walletPostRequestBody.getEmail());
        clientRequestBody.setWallet(walletPostRequestBody.getWalletType());

        CardWalletPostResponseBody clientResponseBody = account.clientAssociateWallet(proposalAccount.getIdAccount(), clientRequestBody);

        if (!clientResponseBody.getResult().equals(StatusWallet.ASSOCIADA)){
            return  ResponseEntity.unprocessableEntity().build();
        }

        AccountWallet accountWallet = new AccountWallet(clientResponseBody.getId(),
                clientRequestBody.getEmail(),
                LocalDateTime.now(),
                clientRequestBody.getWallet(),
                proposalAccount);

        proposalAccount.updateWallets(accountWallet);

        entityManager.persist(proposalAccount);

        URI location = fromCurrentContextPath().pathSegment("api/wallet/get/{id}").buildAndExpand(accountWallet.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
}
