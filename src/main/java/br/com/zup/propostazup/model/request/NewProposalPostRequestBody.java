package br.com.zup.propostazup.model.request;

import br.com.zup.propostazup.annotation.CPForCNPJ;
import br.com.zup.propostazup.model.domain.Proposal;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NewProposalPostRequestBody {

    @NotBlank
    @CPForCNPJ
    private final String document;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;
    @NotNull
    @Valid
    private final AddressPostRequest address;
    @NotNull
    @Positive
    private final BigDecimal salary;

    public NewProposalPostRequestBody(@NotBlank String document,
                                      @NotBlank @Email String email,
                                      @NotBlank String name,
                                      @NotNull @Valid AddressPostRequest address,
                                      @NotNull @Positive BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public AddressPostRequest getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Proposal toProposal() {
        return new Proposal(this.document, this.email, this.name, this.address.toAddress(), this.salary);
    }
}
