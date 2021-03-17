package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.request.CardBlockingPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardBlockingPostResponseBody;
import br.com.zup.propostazup.model.domain.AccountBlock;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.enums.StatusCard;
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

import static br.com.zup.propostazup.util.RequisitionBuilder.postRequestWithUserAgentHeader;
import static br.com.zup.propostazup.util.creator.ProposalAccountCreator.createValidProposalAccountToBeSave;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("BlockCard Controller Test")
@WithMockUser
class BlockCardControllerTest {

    private final String URL_BLOCK_CARD = "/api/block/{id}";

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
    @DisplayName("BlockCard, return 200 status code and persist in database if given valid id card")
    void blockCard_Return200StatusCodeAndPersistInDatabase_IfGivenValidIdCard() throws Exception {
        CardBlockingPostResponseBody clientResponseMock = new CardBlockingPostResponseBody();
        clientResponseMock.setResult(StatusCard.BLOQUEADO);

        List<AccountBlock> allBlocks = findAllBlock();

        assertTrue(allBlocks.isEmpty());

        BDDMockito.when(accountMock.cardBlocking(anyString(), any(CardBlockingPostRequestBody.class)))
                .thenReturn(clientResponseMock);

        postRequestWithUserAgentHeader(URL_BLOCK_CARD, proposalAccount.getId(), mockMvc).andExpect(status().isOk());

        allBlocks = findAllBlock();

        assertFalse(allBlocks.isEmpty());

        AccountBlock accountBlock = allBlocks.get(0);

        assertEquals(proposalAccount, accountBlock.getProposalAccount());

    }

    @Test
    @DisplayName("BlockCard, return 404 status code if given invalid id card")
    void blockCard_Return404StatusCode_IfGivenInvalidIdCard() throws Exception {
        CardBlockingPostResponseBody clientResponseMock = new CardBlockingPostResponseBody();
        clientResponseMock.setResult(StatusCard.BLOQUEADO);

        BDDMockito.when(accountMock.cardBlocking(anyString(), any(CardBlockingPostRequestBody.class)))
                .thenReturn(clientResponseMock);

        postRequestWithUserAgentHeader(URL_BLOCK_CARD, 9999, mockMvc).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("BlockCard, return 422 status code if given blocked card id")
    void blockCard_Return422StatusCode_IfGivenBlockedCardId() throws Exception {
        Proposal proposal = entityManager.find(Proposal.class, 1L);

        ProposalAccount blockedProposalAccount = createValidProposalAccountToBeSave(proposal);

        ReflectionTestUtils.setField(proposal, "proposalAccount", blockedProposalAccount);
        ReflectionTestUtils.setField(blockedProposalAccount, "statusCard", StatusCard.BLOQUEADO);

        entityManager.persist(proposal);
        entityManager.persist(blockedProposalAccount);

        CardBlockingPostResponseBody clientResponseMock = new CardBlockingPostResponseBody();
        clientResponseMock.setResult(StatusCard.BLOQUEADO);

        BDDMockito.when(accountMock.cardBlocking(anyString(), any(CardBlockingPostRequestBody.class)))
                .thenReturn(clientResponseMock);

        postRequestWithUserAgentHeader(URL_BLOCK_CARD, blockedProposalAccount.getId(), mockMvc).andExpect(status().is(422));

    }

    @Test
    @DisplayName("BlockCard, return 422 status code if client fails")
    void blockCard_Return422StatusCode_IfClientFails() throws Exception {
        CardBlockingPostResponseBody clientResponseMock = new CardBlockingPostResponseBody();
        clientResponseMock.setResult(StatusCard.FALHA);

        BDDMockito.when(accountMock.cardBlocking(anyString(), any(CardBlockingPostRequestBody.class)))
                .thenReturn(clientResponseMock);

        postRequestWithUserAgentHeader(URL_BLOCK_CARD, proposalAccount.getId(), mockMvc).andExpect(status().is(422));

    }

    private List<AccountBlock> findAllBlock() {
        return entityManager.createQuery("select b from AccountBlock b", AccountBlock.class).getResultList();
    }
}