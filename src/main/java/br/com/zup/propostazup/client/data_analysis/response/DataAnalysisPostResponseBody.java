package br.com.zup.propostazup.client.data_analysis.response;

import br.com.zup.propostazup.client.data_analysis.enums.AnalysisStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataAnalysisPostResponseBody {

    @JsonProperty("resultadoSolicitacao")
    private AnalysisStatus requestResult;

    public DataAnalysisPostResponseBody(AnalysisStatus requestResult) {
        this.requestResult = requestResult;
    }

    public DataAnalysisPostResponseBody() {
    }

    public AnalysisStatus getRequestResult() {
        return requestResult;
    }

}
