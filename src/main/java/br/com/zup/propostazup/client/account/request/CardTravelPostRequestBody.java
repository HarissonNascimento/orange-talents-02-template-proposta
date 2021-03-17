package br.com.zup.propostazup.client.account.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CardTravelPostRequestBody {

    @NotBlank
    @JsonProperty("destino")
    private String destiny;
    @Future
    @NotNull
    @JsonProperty("validoAte")
    private LocalDate validUntil;

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }
}
