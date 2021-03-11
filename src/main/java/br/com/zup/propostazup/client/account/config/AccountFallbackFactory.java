package br.com.zup.propostazup.client.account.config;

import br.com.zup.propostazup.client.account.Account;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountFallbackFactory implements FallbackFactory<Account> {
    @Override
    public Account create(Throwable cause) {
        String message = String.format("Failed to request card, please try again! Cause: %s", cause.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
