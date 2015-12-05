package com.gearservice.config;

import com.gearservice.config.filter.ReCaptchaAuthFilter;
import com.gearservice.config.properties.ReCaptchaProperties;
import com.gearservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ReCaptchaProperties.class)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private DataSource dataSource;
    @Autowired private UserService userDetailsService;
    @Autowired private ReCaptchaProperties reCaptchaProperties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()).and()
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled, full_name FROM user WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, role FROM authority WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .authenticationEntryPoint(new RedirectAuthenticationEntryPoint())
                .and().rememberMe()
                    .userDetailsService(userDetailsService)
                    .key("steam")
                    .useSecureCookie(true)
                    .tokenValiditySeconds(20000)
                .and().authorizeRequests()
                    .antMatchers("/index.html", "/", "/login", "/javascript/**", "/fonts/**", "/stylesheets/**", "/images/**", "/api/currency-rate")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/attention").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.GET, "/delay").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.POST, "/api/cheques/{\\d+}/diagnostics").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}/diagnostics/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}/notes/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/currency-rate-list").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/currency-rate").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/user").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/user/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and().logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and().csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class)
                .addFilterBefore(new ReCaptchaAuthFilter(reCaptchaProperties), BasicAuthenticationFilter.class);
    }

    private class RedirectAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.sendRedirect("/");
        }
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
