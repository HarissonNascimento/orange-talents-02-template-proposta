package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.client.account.enums.StatusTravel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardTravelPostResponseBody {

    @JsonProperty("resultado")
    private StatusTravel result;

    public StatusTravel getResult() {
        return result;
    }

    public void setResult(StatusTravel result) {
        this.result = result;
    }
}
