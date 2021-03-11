package br.com.zup.propostazup.client.account.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountPostRequestBody {

    @JsonProperty("documento")
    @NotBlank
    private final String document;
    @JsonProperty("nome")
    @NotBlank
    private final String name;
    @JsonProperty("idProposta")
    @NotNull
    private final Long proposalId;

    public AccountPostRequestBody(@NotBlank String document,
                                  @NotBlank String name,
                                  @NotNull Long proposalId) {
        this.document = document;
        this.name = name;
        this.proposalId = proposalId;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public Long getProposalId() {
        return proposalId;
    }

}
