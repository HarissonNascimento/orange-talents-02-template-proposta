package br.com.zup.propostazup.client.account;

import br.com.zup.propostazup.client.account.config.AccountFallbackFactory;
import br.com.zup.propostazup.client.account.request.AccountPostRequestBody;
import br.com.zup.propostazup.client.account.request.CardBlockingPostRequestBody;
import br.com.zup.propostazup.client.account.request.CardTravelPostRequestBody;
import br.com.zup.propostazup.client.account.response.AccountPostResponseBody;
import br.com.zup.propostazup.client.account.response.CardBlockingPostResponseBody;
import br.com.zup.propostazup.client.account.response.CardTravelPostResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "account",
        url = "${api.account.url}",
        fallbackFactory = AccountFallbackFactory.class)
public interface Account {

    @PostMapping("/api/cartoes")
    AccountPostResponseBody createAccount(@RequestBody @Valid AccountPostRequestBody accountPostRequestBody);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    CardBlockingPostResponseBody cardBlocking(@PathVariable String id, @RequestBody @Valid CardBlockingPostRequestBody cardBlockingPostRequestBody);

    @PostMapping("/api/cartoes/{id}/avisos")
    CardTravelPostResponseBody clientTravelNotice(@PathVariable String id, @RequestBody @Valid CardTravelPostRequestBody cardTravelPostRequestBody);
}
