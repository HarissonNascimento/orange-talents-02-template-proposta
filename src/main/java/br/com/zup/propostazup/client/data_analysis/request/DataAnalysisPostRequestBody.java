package br.com.zup.propostazup.client.data_analysis.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DataAnalysisPostRequestBody {

    @JsonProperty("documento")
    @NotBlank
    private final String document;
    @JsonProperty("nome")
    @NotBlank
    private final String name;
    @JsonProperty("idProposta")
    @NotNull
    private final Long proposalId;

    public DataAnalysisPostRequestBody(@NotBlank String document,
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
