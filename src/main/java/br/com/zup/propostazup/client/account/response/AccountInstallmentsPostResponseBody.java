package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.AccountInstallments;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AccountInstallmentsPostResponseBody {

    private String id;
    @JsonProperty("quantidade")
    private Integer amount;
    @JsonProperty("valor")
    private BigDecimal value;

    public String getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public AccountInstallments toAccountInstallments() {
        return new AccountInstallments(this.id, this.amount, this.value);
    }
}
