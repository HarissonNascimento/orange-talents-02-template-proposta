package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.enums.StatusWallet;
import br.com.zup.propostazup.client.account.request.CardWalletPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardWalletPostResponseBody;
import br.com.zup.propostazup.model.domain.AccountWallet;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.enums.WalletType;
import br.com.zup.propostazup.model.request.WalletPostRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static br.com.zup.propostazup.util.RequisitionBuilder.postRequest;
import static br.com.zup.propostazup.util.creator.ProposalAccountCreator.createValidProposalAccountToBeSave;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Wallet Controller Test")
@WithMockUser
class WalletControllerTest {

    private final String URL_ASSOCIATE_WALLET = "/api/wallet/{id}";

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Account accountMock;

    private ProposalAccount proposalAccount;

    @BeforeEach
    public void setUp() {
        Proposal proposal = entityManager.find(Proposal.class, 1L);

        proposalAccount = createValidProposalAccountToBeSave(proposal);

        ReflectionTestUtils.setField(proposal, "proposalAccount", proposalAccount);

        entityManager.persist(proposal);
        entityManager.persist(proposalAccount);

    }

    @Test
    @DisplayName("Associate wallet, return 201 status code and persist in database if given valid id card")
    void associateWallet_Return201StatusCodeAndPersistInDatabase_IfGivenValidIdCard() throws Exception {

        List<AccountWallet> allWallets = findAllWallets();

        assertTrue(allWallets.isEmpty());

        CardWalletPostResponseBody clientResponseBody = new CardWalletPostResponseBody();
        clientResponseBody.setResult(StatusWallet.ASSOCIADA);

        BDDMockito.when(accountMock.clientAssociateWallet(anyString(), any(CardWalletPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        WalletPostRequestBody requestBody = new WalletPostRequestBody("test@email.com", WalletType.PAYPAL);

        postRequest(URL_ASSOCIATE_WALLET, proposalAccount.getId(), requestBody, mockMvc).andExpect(status().isCreated()).andExpect(header().exists("Location"));

        allWallets = findAllWallets();

        assertFalse(allWallets.isEmpty());

        AccountWallet accountWallet = allWallets.get(0);

        assertEquals(proposalAccount, accountWallet.getProposalAccount());

    }

    @Test
    @DisplayName("Associate wallet, return 404 status code if given invalid id card")
    void associateWallet_Return404StatusCode_IfGivenInvalidIdCard() throws Exception {

        WalletPostRequestBody requestBody = new WalletPostRequestBody("test@email.com", WalletType.PAYPAL);

        CardWalletPostResponseBody clientResponseBody = new CardWalletPostResponseBody();
        clientResponseBody.setResult(StatusWallet.ASSOCIADA);

        BDDMockito.when(accountMock.clientAssociateWallet(anyString(), any(CardWalletPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequest(URL_ASSOCIATE_WALLET, 9999, requestBody, mockMvc).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Associate wallet, return 400 status code if given invalid email")
    void associateWallet_Return400StatusCode_IfGivenInvalidEmail() throws Exception {

        WalletPostRequestBody requestBody = new WalletPostRequestBody("Invalid email", WalletType.PAYPAL);

        CardWalletPostResponseBody clientResponseBody = new CardWalletPostResponseBody();
        clientResponseBody.setResult(StatusWallet.ASSOCIADA);

        BDDMockito.when(accountMock.clientAssociateWallet(anyString(), any(CardWalletPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequest(URL_ASSOCIATE_WALLET, 9999, requestBody, mockMvc).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Associate wallet, return 400 status code if given blank email")
    void associateWallet_Return400StatusCode_IfGivenBlankEmail() throws Exception {

        WalletPostRequestBody requestBody = new WalletPostRequestBody("", WalletType.PAYPAL);

        CardWalletPostResponseBody clientResponseBody = new CardWalletPostResponseBody();
        clientResponseBody.setResult(StatusWallet.ASSOCIADA);

        BDDMockito.when(accountMock.clientAssociateWallet(anyString(), any(CardWalletPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequest(URL_ASSOCIATE_WALLET, 9999, requestBody, mockMvc).andExpect(status().isBadRequest());

    }

    private List<AccountWallet> findAllWallets() {
        return entityManager.createQuery("select w from AccountWallet w", AccountWallet.class).getResultList();
    }

}