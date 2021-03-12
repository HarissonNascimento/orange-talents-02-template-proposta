package br.com.zup.propostazup.model.response;

import br.com.zup.propostazup.model.domain.Biometry;

public class BiometryGetResponseBody {

    private String fingerprint;
    private Long proposalAccountId;

    public BiometryGetResponseBody toBiometryGetResponseBody(Biometry biometry) {
        this.fingerprint = biometry.getFingerprint();
        this.proposalAccountId = biometry.getProposalAccount().getId();
        return this;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Long getProposalAccountId() {
        return proposalAccountId;
    }
}
