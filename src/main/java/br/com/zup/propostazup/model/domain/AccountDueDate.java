package br.com.zup.propostazup.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class AccountDueDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dueDateId;
    private int day;
    private LocalDateTime creationDate;

    public AccountDueDate(String id,
                          int day,
                          LocalDateTime creationDate) {
        dueDateId = id;
        this.day = day;
        this.creationDate = creationDate;
    }

    @Deprecated
    protected AccountDueDate() {
    }

    public Long getId() {
        return id;
    }

    public String getDueDateId() {
        return dueDateId;
    }

    public int getDay() {
        return day;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
