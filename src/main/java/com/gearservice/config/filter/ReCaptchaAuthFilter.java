package com.gearservice.config.filter;

import com.gearservice.config.properties.ReCaptchaProperties;
import com.gearservice.model.authorization.ReCaptchaCheckerResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReCaptchaAuthFilter extends GenericFilterBean {

    private ReCaptchaProperties reCaptchaProperties;

    public ReCaptchaAuthFilter(ReCaptchaProperties reCaptchaProperties) {
        this.reCaptchaProperties = reCaptchaProperties;
    }

    private ReCaptchaCheckerResponse checkCaptcha(String key) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", reCaptchaProperties.getSecretKey());
        map.add("response", key);

        return new RestTemplate()
                .postForObject(reCaptchaProperties.getSiteVerify(), map, ReCaptchaCheckerResponse.class);

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        String authorization = request.getHeader("Authorization");
        String reCaptcha = request.getHeader("ReCaptcha");

        if (authorization == null || !authorization.startsWith("Basic ") || !reCaptchaProperties.isEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        if (reCaptcha == null || "[object Object]".equals(reCaptcha) || "null".equals(reCaptcha)) {
            response.setStatus(401);
            return;
        }

        if (!checkCaptcha(reCaptcha).getSuccess()) {
            response.setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}