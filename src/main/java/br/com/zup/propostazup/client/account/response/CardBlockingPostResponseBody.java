package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.enums.StatusCard;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardBlockingPostResponseBody {

    @JsonProperty("resultado")
    private StatusCard result;

    public StatusCard getResult() {
        return result;
    }

    public void setResult(StatusCard result) {
        this.result = result;
    }
}
