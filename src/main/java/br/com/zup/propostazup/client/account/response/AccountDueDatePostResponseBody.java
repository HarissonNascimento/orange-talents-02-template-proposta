package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.AccountDueDate;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AccountDueDatePostResponseBody {

    private String id;
    @JsonProperty("dia")
    private int day;
    @JsonProperty("dataDeCriacao")
    private LocalDateTime creationDate;

    public String getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public AccountDueDate toAccountDueDate() {
        return new AccountDueDate(this.id, this.day, this.creationDate);
    }
}
