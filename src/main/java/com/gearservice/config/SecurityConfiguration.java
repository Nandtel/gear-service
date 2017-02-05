package com.gearservice.config;

import com.gearservice.config.filter.ReCaptchaAuthFilter;
import com.gearservice.config.properties.ReCaptchaProperties;
import com.gearservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Class SecurityConfiguration is configuration
 * Config for Spring Security
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ReCaptchaProperties.class)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserService userDetailsService;
    private final ReCaptchaProperties reCaptchaProperties;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, UserService userDetailsService, ReCaptchaProperties reCaptchaProperties) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.reCaptchaProperties = reCaptchaProperties;
    }

    /**
     * Method configureGlobal configures base params.
     * Add overrated userDetailsService, new BCrypt password encoder, new query for searching users
     * @param auth is AuthenticationManagerBuilder for config object
     * @throws Exception
     */
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

    /**
     * Method passwordEncoder creates new BCrypt password encoder
     * @return new BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    /**
     * Method configure is main config class for http security
     * @param http is HttpSecurity for configuring http security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .authenticationEntryPoint(new RedirectAuthenticationEntryPoint())
                .and().rememberMe()
                    .userDetailsService(userDetailsService)
                    .key("steam")
                    .useSecureCookie(true)
                    .tokenValiditySeconds(25000)
                .and().authorizeRequests()
                    .antMatchers("/index.html", "/", "/login", "/javascript/**", "/fonts/**",
                            "/stylesheets/**", "/images/**", "/api/currency-rate", "/favicon.ico")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/attention").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.GET, "/delay").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.POST, "/api/cheques/{\\d+}/diagnostics").hasAnyAuthority("ROLE_ADMIN", "ROLE_ENGINEER", "ROLE_BOSS")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}/diagnostics/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}/notes/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/cheques/{\\d+}").hasAuthority("ROLE_ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/photo/{\\d+}/{\\d+}").hasAuthority("ROLE_ADMIN")
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
                .addFilterBefore(new ReCaptchaAuthFilter(reCaptchaProperties), BasicAuthenticationFilter.class)
                .headers().contentSecurityPolicy("default-src https: 'self'; " +
                    "object-src 'none'; " +
                    "script-src 'self' https://www.google.com https://www.gstatic.com; " +
                    "style-src 'self' 'unsafe-inline'; " +
                    "img-src 'self' data:; " +
                    "connect-src 'self' ws://127.0.0.1:35729")
                .and().addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "no-referrer-when-downgrade"));
    }

    /**
     * Method RedirectAuthenticationEntryPoint is entry point for correct handling error with unauthorized exception
     */
    private class RedirectAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }

    /**
     * Method csrfHeaderFilter creates filter for correct csrf security
     * @return OncePerRequestFilter for correct csrf security
     */
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
                        cookie.setSecure(true);
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    /**
     * Method csrfTokenRepository creates repository for csrf security token
     * @return repository for csrf security token
     */
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
