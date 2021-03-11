package br.com.zup.propostazup.model.domain;

import br.com.zup.propostazup.annotation.CPForCNPJ;
import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import br.com.zup.propostazup.model.enums.ProposalStatus;

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
    @CPForCNPJ
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

    public Proposal(@NotBlank String document,
                    @NotBlank @Email String email,
                    @NotBlank String name,
                    @NotNull @Valid Address address,
                    @NotNull @Positive BigDecimal salary) {


        this.document = document.replaceAll("[-.]","");
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

    public String getDocument() {
        return document;
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

    public void updateProposalStatus(DataAnalysisPostResponseBody dataAnalysisResponse) {
        this.proposalStatus = dataAnalysisResponse.getRequestResult().toProposalStatus();
    }
}
