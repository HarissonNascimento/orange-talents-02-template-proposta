package br.com.zup.propostazup.client.account;

import br.com.zup.propostazup.client.account.config.AccountFallbackFactory;
import br.com.zup.propostazup.client.account.request.AccountPostRequestBody;
import br.com.zup.propostazup.client.account.response.AccountPostResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "account",
        url = "${api.account.url}",
        fallbackFactory = AccountFallbackFactory.class)
public interface Account {

    @PostMapping("/api/cartoes")
    AccountPostResponseBody createAccount(@RequestBody @Valid AccountPostRequestBody accountPostRequestBody);

}
