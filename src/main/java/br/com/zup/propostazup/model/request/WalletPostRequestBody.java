package br.com.zup.propostazup.model.request;

import br.com.zup.propostazup.model.enums.WalletType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WalletPostRequestBody {

    @Email
    @NotBlank
    private final String email;
    @NotNull
    private final WalletType walletType;

    public WalletPostRequestBody(@Email @NotBlank String email,@NotNull WalletType walletType) {
        this.email = email;
        this.walletType = walletType;
    }

    public String getEmail() {
        return email;
    }

    public WalletType getWalletType() {
        return walletType;
    }
}
