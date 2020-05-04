package com.gavinkim.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinkim.dto.ResponseDto;
import com.gavinkim.type.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("[!] TokenAuthenticationEntryPoint: {}", authException.getMessage());
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        setOutputStream(response);
    }

    private void setOutputStream(HttpServletResponse response) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(ResponseType.UNAUTHORIZED.getCode());
        responseDto.setMessage(ResponseType.UNAUTHORIZED.getMessage());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, responseDto);
        out.flush();
    }
}
