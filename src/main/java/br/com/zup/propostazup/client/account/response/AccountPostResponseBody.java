package br.com.zup.propostazup.client.account.response;

import br.com.zup.propostazup.model.domain.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AccountPostResponseBody {

    @JsonProperty("id")
    private String idAccount;
    @JsonProperty("emitidoEm")
    private LocalDateTime issuedOn;
    @JsonProperty("titular")
    private String holder;
    @JsonProperty("bloqueios")
    private List<AccountBlockPostResponseBody> block;
    @JsonProperty("avisos")
    private List<AccountWarningPostResponseBody> warning;
    @JsonProperty("carteiras")
    private List<AccountWalletPostResponseBody> wallet;
    @JsonProperty("parcelas")
    private List<AccountInstallmentsPostResponseBody> installments;
    @JsonProperty("limite")
    private BigDecimal credit;
    @JsonProperty("renegociacao")
    private AccountRenegotiationPostResponseBody renegotiation;
    @JsonProperty("vencimento")
    private AccountDueDatePostResponseBody dueDate;
    @JsonProperty("idProposta")
    private Long proposalId;

    public String getIdAccount() {
        return idAccount;
    }

    public LocalDateTime getIssuedOn() {
        return issuedOn;
    }

    public String getHolder() {
        return holder;
    }

    public List<AccountBlockPostResponseBody> getBlock() {
        return block;
    }

    public List<AccountWarningPostResponseBody> getWarning() {
        return warning;
    }

    public List<AccountWalletPostResponseBody> getWallet() {
        return wallet;
    }

    public List<AccountInstallmentsPostResponseBody> getInstallments() {
        return installments;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public AccountRenegotiationPostResponseBody getRenegotiation() {
        return renegotiation;
    }

    public AccountDueDatePostResponseBody getDueDate() {
        return dueDate;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public ProposalAccount toProposalAccount(Proposal proposal) {

        if (!proposal.getId().equals(proposalId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inconvertible for this proposal!");
        }

        List<AccountBlock> accountBlocks =
                this.block.stream().map(AccountBlockPostResponseBody::toAccountBlock).collect(Collectors.toList());

        List<AccountWarning> accountWarnings =
                this.warning.stream().map(AccountWarningPostResponseBody::toAccountWarning).collect(Collectors.toList());

        List<AccountWallet> accountWallet =
                this.wallet.stream().map(AccountWalletPostResponseBody::toAccountWallet).collect(Collectors.toList());

        List<AccountInstallments> accountInstallments =
                this.installments.stream().map(AccountInstallmentsPostResponseBody::toAccountInstallments).collect(Collectors.toList());

        AccountRenegotiation accountRenegotiation = new AccountRenegotiation();

        if (this.renegotiation != null) {
            accountRenegotiation = this.renegotiation.toAccountRenegotiation();
        }

        AccountDueDate accountDueDate = this.dueDate.toAccountDueDate();

        return new ProposalAccount(this.idAccount,
                this.issuedOn,
                this.holder,
                accountBlocks,
                accountWarnings,
                accountWallet,
                accountInstallments,
                this.credit,
                accountRenegotiation,
                accountDueDate,
                proposal);
    }
}
