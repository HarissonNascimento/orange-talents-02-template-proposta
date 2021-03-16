package br.com.zup.propostazup.client.account.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CardBlockingPostRequestBody {

    @NotBlank
    @JsonProperty("sistemaResponsavel")
    private String responsibleSystem;

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public void setResponsibleSystem(String responsibleSystem) {
        this.responsibleSystem = responsibleSystem;
    }
}
