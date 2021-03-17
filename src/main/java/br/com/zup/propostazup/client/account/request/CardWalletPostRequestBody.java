package br.com.zup.propostazup.client.account.request;

import br.com.zup.propostazup.model.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CardWalletPostRequestBody {

    @Email
    @NotBlank
    private String email;
    @NotNull
    @JsonProperty("carteira")
    private WalletType wallet;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WalletType getWallet() {
        return wallet;
    }

    public void setWallet(WalletType wallet) {
        this.wallet = wallet;
    }

}
