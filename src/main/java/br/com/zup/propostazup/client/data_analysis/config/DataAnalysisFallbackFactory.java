package br.com.zup.propostazup.client.data_analysis.config;

import br.com.zup.propostazup.client.data_analysis.DataAnalysis;
import br.com.zup.propostazup.client.data_analysis.enums.AnalysisStatus;
import br.com.zup.propostazup.client.data_analysis.response.DataAnalysisPostResponseBody;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DataAnalysisFallbackFactory implements FallbackFactory<DataAnalysis> {
    @Override
    public DataAnalysis create(Throwable cause) {
        return response -> {

            if (cause instanceof FeignException.UnprocessableEntity) {
                return new DataAnalysisPostResponseBody(AnalysisStatus.COM_RESTRICAO);
            }

            String message = String.format("Failed to verify proposal! Cause: %s", cause.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        };
    }
}

