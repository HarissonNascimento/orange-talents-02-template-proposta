package br.com.zup.propostazup.client.account.response;


import br.com.zup.propostazup.model.domain.AccountWallet;
import br.com.zup.propostazup.model.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AccountWalletPostResponseBody {

    private String id;
    private String email;
    @JsonProperty("associadaEm")
    private LocalDateTime associatedIn;
    @JsonProperty("emissor")
    private WalletType emitter;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociatedIn() {
        return associatedIn;
    }

    public WalletType getEmitter() {
        return emitter;
    }

    public AccountWallet toAccountWallet() {
        return new AccountWallet(this.id, this.email, this.associatedIn, this.emitter);
    }
}
