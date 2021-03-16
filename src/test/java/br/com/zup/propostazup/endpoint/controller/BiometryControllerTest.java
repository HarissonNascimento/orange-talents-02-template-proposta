package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.model.domain.Biometry;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.request.NewBiometryPostRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Base64;
import java.util.List;

import static br.com.zup.propostazup.util.RequisitionBuilder.postRequest;
import static br.com.zup.propostazup.util.creator.ProposalAccountCreator.createValidProposalAccountToBeSave;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Biometry Controller Test")
@WithMockUser
class BiometryControllerTest {

    private final String URL_NEW_BIOMETRY = "/api/biometry/{cardId}";

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Create new biometry, return 201 status code and persist in database if given valid biometry")
    void createNewBiometry_Return201StatusCodeAndPersistIndDatabase_IfGivenValidBiometry() throws Exception {
        List<Biometry> biometryList = findAllBiometrics();

        assertTrue(biometryList.isEmpty());

        NewBiometryPostRequestBody requestBody = new NewBiometryPostRequestBody();
        requestBody.setFingerprint(Base64.getEncoder().encodeToString("Test encoder".getBytes()));

        postRequest(URL_NEW_BIOMETRY, proposalAccount.getId(), requestBody, mockMvc)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        biometryList = findAllBiometrics();

        assertFalse(biometryList.isEmpty());

        Biometry biometry = biometryList.get(0);

        assertAll(
                () -> assertNotNull(biometry),
                () -> assertEquals(requestBody.getFingerprint(), biometry.getFingerprint())
        );

    }

    @Test
    @DisplayName("Create new biometry, return 404 status code if given invalid card id")
    void createNewBiometry_Return404StatusCode_IfGivenInvalidCardId() throws Exception {
        NewBiometryPostRequestBody requestBody = new NewBiometryPostRequestBody();
        requestBody.setFingerprint(Base64.getEncoder().encodeToString("Test encoder".getBytes()));

        postRequest(URL_NEW_BIOMETRY, 9999, requestBody, mockMvc)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Create new biometry, return 400 status code if dont give fingerprint in base 64")
    void createNewBiometry_Return400StatusCode_IfDontGiveFingerprintInBase64() throws Exception {
        NewBiometryPostRequestBody requestBody = new NewBiometryPostRequestBody();
        requestBody.setFingerprint("Test encoder");

        postRequest(URL_NEW_BIOMETRY, proposalAccount.getId(), requestBody, mockMvc)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create new biometry, return 400 status code if dont give fingerprint in base 64")
    void createNewBiometry_Return400StatusCode_IfGivenBlankFingerprint() throws Exception {
        NewBiometryPostRequestBody requestBody = new NewBiometryPostRequestBody();
        requestBody.setFingerprint("");

        postRequest(URL_NEW_BIOMETRY, proposalAccount.getId(), requestBody, mockMvc)
                .andExpect(status().isBadRequest());

    }

    private List<Biometry> findAllBiometrics() {
        return entityManager.createQuery("select b from Biometry b", Biometry.class).getResultList();
    }

}