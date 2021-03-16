package br.com.zup.propostazup.util.creator;

import br.com.zup.propostazup.model.domain.AccountDueDate;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProposalAccountCreator {

    public static ProposalAccount createValidProposalAccountToBeSave(Proposal proposal){
        return new ProposalAccount("testIdAccount",
                LocalDateTime.now(),
                proposal.getName(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                BigDecimal.TEN,
                null,
                new AccountDueDate("idTest", 30, LocalDateTime.now()),
                proposal);
    }
}
