package com.gavinkim.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;

@Slf4j
public class AuditingFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		long start = Instant.now().toEpochMilli();
		chain.doFilter(req,res);
		long elapsed = Instant.now().toEpochMilli()  - start;
		HttpServletRequest request = (HttpServletRequest) req;
		log.info("Request[uri=" + request.getRequestURI() + ", method="+request.getMethod() + " elapsed="+elapsed+"ms]");
	}
}
