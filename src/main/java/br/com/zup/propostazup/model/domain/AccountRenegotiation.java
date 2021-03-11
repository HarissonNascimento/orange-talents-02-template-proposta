package br.com.zup.propostazup.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class AccountRenegotiation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String renegotiationId;
    private Integer amount;
    private BigDecimal value;
    private LocalDateTime creationDate;

    public AccountRenegotiation(String id,
                                Integer amount,
                                BigDecimal value,
                                LocalDateTime creationDate) {

        renegotiationId = id;
        this.amount = amount;
        this.value = value;
        this.creationDate = creationDate;
    }

    public AccountRenegotiation() {
    }

    public Long getId() {
        return id;
    }

    public String getRenegotiationId() {
        return renegotiationId;
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
}
