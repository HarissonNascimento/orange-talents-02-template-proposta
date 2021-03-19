package br.com.zup.propostazup.model.domain;

import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import br.com.zup.propostazup.model.enums.ProposalStatus;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String document;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotNull
    @Valid
    @Embedded
    private Address address;
    @NotNull
    @Positive
    private BigDecimal salary;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    @OneToOne(mappedBy = "proposal", cascade = CascadeType.ALL)
    private ProposalAccount proposalAccount;
    @NotBlank
    private String salt;
    @NotBlank
    private String hashedDocument;


    public Proposal(@NotBlank String document,
                    @NotBlank @Email String email,
                    @NotBlank String name,
                    @NotNull @Valid Address address,
                    @NotNull @Positive BigDecimal salary) {

        String untreatedDocument = document.replaceAll("[-.]", "");

        this.document = encryptDocument(untreatedDocument);
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.proposalStatus = ProposalStatus.NAO_ELEGIVEL;

    }

    @Deprecated
    protected Proposal() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public ProposalAccount getProposalAccount() {
        return proposalAccount;
    }

    public String getDocument() {
        return document;
    }

    public String getSalt() {
        return salt;
    }

    public String getHashedDocument() {
        return hashedDocument;
    }

    public void updateProposalStatus(DataAnalysisPostResponseBody dataAnalysisResponse) {
        this.proposalStatus = dataAnalysisResponse.getRequestResult().toProposalStatus();
    }

    public void updateProposalAccount(ProposalAccount proposalAccount) {
        if (proposalAccount == null) {
            return;
        }
        this.proposalAccount = proposalAccount;
    }

    private String encryptDocument(String untreatedDocument) {
        this.salt = KeyGenerators.string().generateKey();
        this.hashedDocument = Sha512DigestUtils.shaHex(untreatedDocument);
        return Encryptors.text("proposalEncrypt", this.salt).encrypt(untreatedDocument);
    }
}
