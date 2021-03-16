package br.com.zup.propostazup.client.data_analysis;

import br.com.zup.propostazup.client.data_analysis.enums.AnalysisStatus;
import br.com.zup.propostazup.client.data_analysis.request.DataAnalysisPostRequestBody;
import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import br.com.zup.propostazup.endpoint.repository.ProposalRepository;
import br.com.zup.propostazup.model.domain.Proposal;
import br.com.zup.propostazup.model.enums.ProposalStatus;
import br.com.zup.propostazup.model.request.NewProposalPostRequestBody;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.zup.propostazup.util.RequisitionBuilder.postRequest;
import static br.com.zup.propostazup.util.creator.NewProposalPostRequestBodyCreator.createValidNewProposalPostRequestBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("Data Analysis Test")
@WithMockUser
class DataAnalysisTest {

    private final String URL_NEW_PROPOSAL = "/api/proposal";

    @MockBean
    private DataAnalysis dataAnalysis;

    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private MockMvc mockMvc;

    private NewProposalPostRequestBody requestBody;

    @BeforeEach
    public void setUp() {

        requestBody = createValidNewProposalPostRequestBody();

    }

    @Test
    @DisplayName("Create new proposal, proposal status value equals ELEGIVEL if data analysis reply SEM_RESTRICAO")
    void createNewProposal_ProposalStatusValueEqualsELEGIVEL_IfDataAnalysisReplySEM_RESTRICAO() throws Exception {
        BDDMockito.when(dataAnalysis.sendData(any(DataAnalysisPostRequestBody.class)))
                .thenReturn(new DataAnalysisPostResponseBody(AnalysisStatus.SEM_RESTRICAO));

        int size = proposalRepository.findAll().size();

        postRequest(URL_NEW_PROPOSAL, requestBody, mockMvc).andExpect(status().isCreated());

        List<Proposal> proposals = proposalRepository.findAll();

        assertEquals(size + 1, proposals.size());

        Proposal proposal = proposals.get(size);

        assertEquals(ProposalStatus.ELEGIVEL, proposal.getProposalStatus());
    }

    @Test
    @DisplayName("Create new proposal, proposal status value equals NAO_ELEGIVEL if data analysis reply COM_RESTRICAO")
    void createNewProposal_ProposalStatusValueEqualsNAO_ELEGIVEL_IfDataAnalysisReplyCOM_RESTRICAO() throws Exception {
        BDDMockito.when(dataAnalysis.sendData(any(DataAnalysisPostRequestBody.class)))
                .thenReturn(new DataAnalysisPostResponseBody(AnalysisStatus.COM_RESTRICAO));

        int size = proposalRepository.findAll().size();

        postRequest(URL_NEW_PROPOSAL, requestBody, mockMvc).andExpect(status().isCreated());

        List<Proposal> proposals = proposalRepository.findAll();

        assertEquals(size + 1, proposals.size());

        Proposal proposal = proposals.get(size);

        assertEquals(ProposalStatus.NAO_ELEGIVEL, proposal.getProposalStatus());
    }

}