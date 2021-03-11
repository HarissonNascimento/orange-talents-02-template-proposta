package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.AccountRenegotiation;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountRenegotiationPostResponseBody {

    private String id;
    @JsonProperty("quantidade")
    private Integer amount;
    @JsonProperty("valor")
    private BigDecimal value;
    @JsonProperty("dataDeCriacao")
    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public AccountRenegotiation toAccountRenegotiation() {
        return new AccountRenegotiation(this.id, this.amount, this.value, this.creationDate);
    }
}
