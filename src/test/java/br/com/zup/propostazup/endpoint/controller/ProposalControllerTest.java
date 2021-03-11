package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.endpoint.repository.ProposalRepository;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.request.NewProposalPostRequestBody;
import br.com.zup.propostazup.model.response.ProposalGetResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.List;

import static br.com.zup.propostazup.util.RequisitionBuilder.getRequest;
import static br.com.zup.propostazup.util.RequisitionBuilder.postRequest;
import static br.com.zup.propostazup.util.creator.AddressPostRequestCreator.createValidAddressPostRequest;
import static br.com.zup.propostazup.util.creator.NewProposalPostRequestBodyCreator.createValidNewProposalPostRequestBody;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Proposal Controller Test")
class ProposalControllerTest {

    private final String URL_NEW_PROPOSAL = "/api/proposal";
    private final String URL_GET_PROPOSAL = "/api/proposal/{id}";

    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private NewProposalPostRequestBody requestBody;

    @BeforeEach
    public void setUp() {

        requestBody = createValidNewProposalPostRequestBody();

    }

    @Test
    @DisplayName("Get proposal, return 200 status code and ProposalGetResponseBody when find exists proposal")
    void getProposal_Return200StatusCodeAndProposalGetResponseBody_WhenFindExistsProposal() throws Exception {
        Proposal proposalToBeSaved = requestBody.toProposal();

        proposalRepository.save(proposalToBeSaved);

        ResultActions resultActions = getRequest(URL_GET_PROPOSAL, proposalToBeSaved.getId(), mockMvc).andExpect(status().isOk());

        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        ProposalGetResponseBody proposalGetResponseBody = objectMapper.readValue(jsonResponse, ProposalGetResponseBody.class);

        assertAll(
                () -> assertEquals(proposalToBeSaved.getId(), proposalGetResponseBody.getId()),
                () -> assertEquals(proposalToBeSaved.getName(), proposalGetResponseBody.getName()),
                () -> assertEquals(proposalToBeSaved.getEmail(), proposalGetResponseBody.getEmail()),
                () -> assertEquals(proposalToBeSaved.getProposalStatus(), proposalGetResponseBody.getProposalStatus())
        );

    }

    @Test
    @DisplayName("Get proposal, return 404 status code when find not exists proposal")
    void getProposal_Return404StatusCode_WhenFindNotExistsProposal() throws Exception {

        getRequest(URL_GET_PROPOSAL, 1L, mockMvc).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Create new proposal, return 201 status code and persist new proposal if given valid NewProposalRequestBody")
    void createNewProposal_Return201StatusCodeAndPersistNewProposal_IfGivenValidNewProposalRequestBody() throws Exception {
        int size = proposalRepository.findAll().size();

        ResultActions resultActions = postRequest(URL_NEW_PROPOSAL, requestBody, mockMvc).andExpect(status().isCreated());

        String location = resultActions.andReturn().getResponse().getHeader("Location");

        List<Proposal> proposals = proposalRepository.findAll();

        assertEquals(size + 1, proposals.size());

        Proposal proposal = proposals.get(size);

        assertAll(
                () -> assertNotNull(proposal),
                () -> assertEquals(requestBody.getName(), proposal.getName()),
                () -> assertEquals(requestBody.getDocument(), proposal.getDocument()),
                () -> assertEquals(requestBody.getSalary(), proposal.getSalary()),
                () -> assertEquals(requestBody.getEmail(), proposal.getEmail()),
                () -> assertNotNull(proposal.getId()),
                () -> assertNotNull(location),
                () -> assertTrue(location.contains(proposal.getId().toString()))
        );
    }

    @Test
    @DisplayName("Create new proposal, return 422 status code if proposal already registered")
    void createNewProposal_Return422StatusCode_IfProposalAlreadyRegistered() throws Exception {
        Proposal proposalToBeSave = requestBody.toProposal();

        proposalRepository.save(proposalToBeSave);

        ResultActions resultActions = postRequest(URL_NEW_PROPOSAL, requestBody, mockMvc);

        assertEquals(UNPROCESSABLE_ENTITY.value(), resultActions.andReturn().getResponse().getStatus());

    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given invalid document")
    void createNewProposal_Return400StatusCode_IfGivenInvalidDocument() throws Exception {
        requestBody = new NewProposalPostRequestBody("1111111111",
                "email@test.com",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given blank document")
    void createNewProposal_Return400StatusCode_IfGivenBlankDocument() throws Exception {
        requestBody = new NewProposalPostRequestBody("",
                "email@test.com",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given blank email")
    void createNewProposal_Return400StatusCode_IfGivenBlankEmail() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given invalid email")
    void createNewProposal_Return400StatusCode_IfGivenInvalidEmail() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "invalid",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given blank name")
    void createNewProposal_Return400StatusCode_IfGivenBlankName() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "email@test.com",
                "",
                createValidAddressPostRequest(),
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given null address")
    void createNewProposal_Return400StatusCode_IfGivenNullAddress() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "email@test.com",
                "Test",
                null,
                BigDecimal.TEN);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given null salary")
    void createNewProposal_Return400StatusCode_IfGivenNullSalary() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "email@test.com",
                "Test",
                createValidAddressPostRequest(),
                null);

        assertBadRequestInvalidProposal(requestBody);
    }

    @Test
    @DisplayName("Create new proposal, return 400 status code if given negative salary")
    void createNewProposal_Return400StatusCode_IfGivenNegativeSalary() throws Exception {
        requestBody = new NewProposalPostRequestBody("29066654015",
                "email@test.com",
                "Test",
                createValidAddressPostRequest(),
                BigDecimal.valueOf(-1));

        assertBadRequestInvalidProposal(requestBody);
    }

    private void assertBadRequestInvalidProposal(NewProposalPostRequestBody requestBody) throws Exception {
        ResultActions resultActions = postRequest(URL_NEW_PROPOSAL, requestBody, mockMvc);

        assertEquals(BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());

        Class<? extends Exception> aClass = requireNonNull(resultActions.andReturn().getResolvedException()).getClass();

        assertEquals(MethodArgumentNotValidException.class, aClass);
    }
}