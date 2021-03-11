package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.AccountWarning;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class AccountWarningPostResponseBody {

    @JsonProperty("validoAte")
    private LocalDate validUntil;
    @JsonProperty("destino")
    private String destiny;

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public String getDestiny() {
        return destiny;
    }

    public AccountWarning toAccountWarning() {
        return new AccountWarning(this.validUntil, this.destiny);
    }

}
