package br.com.zup.propostazup.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class RequisitionBuilder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ResultActions postRequest(String url, Object content, MockMvc mockMvc) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(content)));
    }

    public static ResultActions postRequest(String url, Object uriVars, Object content, MockMvc mockMvc) throws Exception {
        return mockMvc.perform(post(url, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(content)));
    }

    public static ResultActions postRequestWithUserAgentHeader(String url, Object uriVars, MockMvc mockMvc) throws Exception {
        return mockMvc.perform(post(url, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "Test"));
    }

    public static ResultActions getRequest(String url, Long objectId, MockMvc mockMvc) throws Exception {
        return mockMvc.perform(get(url, objectId));
    }

    private static String toJson(Object requestBody) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requestBody);
    }
}
