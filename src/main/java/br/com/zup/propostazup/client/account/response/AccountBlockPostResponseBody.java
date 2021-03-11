package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.AccountBlock;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AccountBlockPostResponseBody {

    @JsonProperty("id")
    private String id;
    @JsonProperty("bloqueadoEm")
    private LocalDateTime blockedIn;
    @JsonProperty("sistemaResponsavel")
    private String responsibleSystem;
    @JsonProperty("ativo")
    private boolean active;

    public String getId() {
        return id;
    }

    public LocalDateTime getBlockedIn() {
        return blockedIn;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public boolean isActive() {
        return active;
    }

    public AccountBlock toAccountBlock() {
        return new AccountBlock(this.id, this.blockedIn, this.responsibleSystem, this.active);
    }
}
