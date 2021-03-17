package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.client.account.Account;
import br.com.zup.propostazup.client.account.enums.StatusTravel;
import br.com.zup.propostazup.client.account.request.CardTravelPostRequestBody;
import br.com.zup.propostazup.client.account.response.CardTravelPostResponseBody;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.domain.TravelNotice;
import br.com.zup.propostazup.model.request.TravelPostRequestBody;
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
import java.time.LocalDate;
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
@DisplayName("Travel Controller Test")
@WithMockUser
class TravelControllerTest {

    private final String URL_TRAVEL_NOTICE = "/api/travel/{id}";

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
    @DisplayName("TravelNotice, return 200 status code and persist in database if given valid id card")
    void travelNotice_Return200StatusCodeAndPersistInDatabase_IfGivenValidIdCard() throws Exception {

        TravelPostRequestBody requestBody = new TravelPostRequestBody("Test Destiny", LocalDate.MAX);

        List<TravelNotice> allTravels = findAllTravels();

        assertTrue(allTravels.isEmpty());

        CardTravelPostResponseBody clientResponseBody = new CardTravelPostResponseBody();
        clientResponseBody.setResult(StatusTravel.CRIADO);

        BDDMockito.when(accountMock.clientTravelNotice(anyString(), any(CardTravelPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequestWithUserAgentHeader(URL_TRAVEL_NOTICE, proposalAccount.getId(), requestBody, mockMvc).andExpect(status().isOk());

        allTravels = findAllTravels();

        assertFalse(allTravels.isEmpty());

        TravelNotice travelNotice = allTravels.get(0);

        assertEquals(proposalAccount, travelNotice.getProposalAccount());
    }

    @Test
    @DisplayName("TravelNotice, return 404 status code if given invalid id card")
    void travelNotice_Return404StatusCode_IfGivenInvalidIdCard() throws Exception {

        TravelPostRequestBody requestBody = new TravelPostRequestBody("Test Destiny", LocalDate.MAX);

        CardTravelPostResponseBody clientResponseBody = new CardTravelPostResponseBody();
        clientResponseBody.setResult(StatusTravel.CRIADO);

        BDDMockito.when(accountMock.clientTravelNotice(anyString(), any(CardTravelPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequestWithUserAgentHeader(URL_TRAVEL_NOTICE, 9999, requestBody, mockMvc).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("TravelNotice, return 400 status code if given blank destiny")
    void travelNotice_Return400StatusCode_IfGivenBlankDestiny() throws Exception {

        TravelPostRequestBody requestBody = new TravelPostRequestBody("", LocalDate.MAX);

        CardTravelPostResponseBody clientResponseBody = new CardTravelPostResponseBody();
        clientResponseBody.setResult(StatusTravel.CRIADO);

        BDDMockito.when(accountMock.clientTravelNotice(anyString(), any(CardTravelPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequestWithUserAgentHeader(URL_TRAVEL_NOTICE, proposalAccount.getId(), requestBody, mockMvc).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("TravelNotice, return 400 status code if given in past date")
    void travelNotice_Return400StatusCode_IfGivenInPastDate() throws Exception {

        TravelPostRequestBody requestBody = new TravelPostRequestBody("Test Destiny", LocalDate.MIN);

        CardTravelPostResponseBody clientResponseBody = new CardTravelPostResponseBody();
        clientResponseBody.setResult(StatusTravel.CRIADO);

        BDDMockito.when(accountMock.clientTravelNotice(anyString(), any(CardTravelPostRequestBody.class)))
                .thenReturn(clientResponseBody);

        postRequestWithUserAgentHeader(URL_TRAVEL_NOTICE, proposalAccount.getId(), requestBody, mockMvc).andExpect(status().isBadRequest());

    }

    private List<TravelNotice> findAllTravels() {
        return entityManager.createQuery("select t from TravelNotice t", TravelNotice.class).getResultList();
    }

}