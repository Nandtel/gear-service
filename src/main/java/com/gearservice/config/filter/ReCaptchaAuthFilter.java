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

/**
 * Class ReCaptchaAuthFilter is configuration
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

public class ReCaptchaAuthFilter extends GenericFilterBean {

    private ReCaptchaProperties reCaptchaProperties;

    public ReCaptchaAuthFilter(ReCaptchaProperties reCaptchaProperties) {
        this.reCaptchaProperties = reCaptchaProperties;
    }

    /**
     * Method checkCaptcha checks Google ReCaptcha answer
     * @param key for Google ReCaptcha answer
     * @return Google ReCaptcha answer
     */
    private ReCaptchaCheckerResponse checkCaptcha(String key, String ip) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", reCaptchaProperties.getSecretKey());
        map.add("response", key);
        map.add("remoteip", ip);

        return new RestTemplate()
                .postForObject(reCaptchaProperties.getSiteVerify(), map, ReCaptchaCheckerResponse.class);
    }

    /**
     * Method doFilter is overrated method of Filter, that handle answer of Google RaCaptcha server
     * @param req is servlet request
     * @param res is servlet response
     * @param chain of spring security's set of chains
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        String authorization = request.getHeader("Authorization");
        String reCaptcha = request.getHeader("ReCaptcha");
        String ip = request.getRemoteAddr();

        if (authorization == null || !authorization.startsWith("Basic ") || !reCaptchaProperties.isEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        if (reCaptcha == null || "[object Object]".equals(reCaptcha) || "null".equals(reCaptcha)) {
            response.setStatus(401);
            return;
        }

        if (!checkCaptcha(reCaptcha, ip).getSuccess()) {
            response.setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}