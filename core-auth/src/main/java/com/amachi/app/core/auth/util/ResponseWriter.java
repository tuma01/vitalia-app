package com.amachi.app.core.auth.util;

import com.amachi.app.core.common.api.ApiResponse;
import com.amachi.app.core.common.error.ErrorDetail;
import com.amachi.app.core.common.http.HttpStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResponseWriter {

    private final ObjectMapper objectMapper;

    public void writeError(HttpServletResponse response,
                           HttpStatusCode status,
                           ErrorDetail errorDetail,
                           String path) throws IOException {

        ApiResponse<Object> apiResponse = ApiResponse.error(status, errorDetail, path);

        response.setStatus(status.getCode());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    public void writeError(HttpServletResponse response,
                           HttpStatusCode status,
                           String userMessage,
                           String developerMessage,
                           String path) throws IOException {

        ErrorDetail detail = ErrorDetail.from(
                null,
                userMessage,
                developerMessage,
                null
        );
        writeError(response, status, detail, path);
    }
}

