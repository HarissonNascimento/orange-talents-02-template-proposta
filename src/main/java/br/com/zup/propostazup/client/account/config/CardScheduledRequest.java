package br.com.zup.propostazup.client.account.config;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.request.AccountPostRequestBody;
import br.com.zup.propostazup.client.account.response.AccountPostResponseBody;
import br.com.zup.propostazup.endpoint.repository.ProposalRepository;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.enums.ProposalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CardScheduledRequest {

    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private Account account;

    @Scheduled(fixedDelayString = "${api.account.scheduled-time}")
    @Transactional
    public void requestCardForApprovedProposal(){
        List<Proposal> proposalList = proposalRepository.findAllByProposalStatusAndProposalAccountIsNull(ProposalStatus.ELEGIVEL);

        if (proposalList.isEmpty()){
            return;
        }

        proposalList.forEach(p -> {
            AccountPostRequestBody accountPostRequestBody = new AccountPostRequestBody(p.getDocument(), p.getName(), p.getId());

            AccountPostResponseBody accountResponse = this.account.createAccount(accountPostRequestBody);

            if (accountResponse != null){
                ProposalAccount proposalAccount = accountResponse.toProposalAccount(p);

                p.updateProposalAccount(proposalAccount);
            }
        });
    }

}
