package br.com.zup.propostazup.util.creator;

import br.com.zup.propostazup.model.request.NewProposalPostRequestBody;

import java.math.BigDecimal;

import static br.com.zup.propostazup.util.creator.AddressPostRequestCreator.createValidAddressPostRequest;

public class NewProposalPostRequestBodyCreator {

    public static NewProposalPostRequestBody createValidNewProposalPostRequestBody(){
        return new NewProposalPostRequestBody("29066654015",
                "email@test.com",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.TEN);
    }
}
