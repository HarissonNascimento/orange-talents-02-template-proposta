package br.com.zup.propostazup.model.request;

import br.com.zup.propostazup.annotation.Base64;
import br.com.zup.propostazup.model.domain.Biometry;
import br.com.zup.propostazup.model.domain.ProposalAccount;

import javax.validation.constraints.NotBlank;

public class NewBiometryPostRequestBody {

    @NotBlank
    @Base64
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometry toBiometry(ProposalAccount proposalAccount) {
        return new Biometry(this.fingerprint, proposalAccount);
    }
}
