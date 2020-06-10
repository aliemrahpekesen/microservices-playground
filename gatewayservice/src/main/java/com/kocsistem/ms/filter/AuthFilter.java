package com.kocsistem.ms.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthFilter extends ZuulFilter {

	@Value("${server.path}")
	private String gatewayRootPath;

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		String authCheckUrl = UriComponentsBuilder.fromHttpUrl(gatewayRootPath).path("/auth/identity/check").build()
				.toUriString();
		String token = ctx.getRequest().getHeader("token") == null ? "" : ctx.getRequest().getHeader("token");
		if (!token.equals("")) {
			RestTemplate restCaller = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("token", token);
			HttpEntity<String> entity = new HttpEntity<>("", headers);

			ResponseEntity<Boolean> response = restCaller.exchange(authCheckUrl, HttpMethod.GET, entity, Boolean.class);
			System.out.println("RESPONSE : " + response.getBody().toString());

			if (!response.getBody()) {
				ctx.set("error.status_code", HttpServletResponse.SC_UNAUTHORIZED);
				ctx.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
				ctx.setResponseBody("Authorization Exception");
				ctx.setSendZuulResponse(false);
				return ctx;
			}

		} else {
			ctx.set("error.status_code", HttpServletResponse.SC_UNAUTHORIZED);
			ctx.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
			ctx.setResponseBody("Authorization Exception");
			ctx.setSendZuulResponse(false);
			return ctx;
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return !RequestContext.getCurrentContext().getRequest().getRequestURI().startsWith("/auth");
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
