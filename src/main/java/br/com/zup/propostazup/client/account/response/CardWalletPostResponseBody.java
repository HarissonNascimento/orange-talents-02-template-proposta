package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.client.account.enums.StatusWallet;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardWalletPostResponseBody {

    @JsonProperty("resultado")
    private StatusWallet result;
    private String id;

    public StatusWallet getResult() {
        return result;
    }

    public void setResult(StatusWallet result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
