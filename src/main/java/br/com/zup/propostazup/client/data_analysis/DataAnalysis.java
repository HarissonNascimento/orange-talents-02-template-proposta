package br.com.zup.propostazup.client.data_analysis;

import br.com.zup.propostazup.client.data_analysis.config.DataAnalysisFallbackFactory;
import br.com.zup.propostazup.client.data_analysis.request.DataAnalysisPostRequestBody;
import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "dataAnalysis",
        url = "${api.data-analysis.url}",
        fallbackFactory = DataAnalysisFallbackFactory.class)
public interface DataAnalysis {

    @PostMapping("/api/solicitacao")
    DataAnalysisPostResponseBody sendData(DataAnalysisPostRequestBody dataAnalysisPostRequestBody);
}
