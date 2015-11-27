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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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
                .httpBasic().and()
                .rememberMe().tokenValiditySeconds(5).and()
                .addFilterBefore(new ReCaptchaAuthFilter(reCaptchaProperties), BasicAuthenticationFilter.class)
                .authorizeRequests()
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
                .antMatchers("/index.html", "/", "/login", "/javascript/**", "/fonts/**",
                        "/stylesheets/**", "/images/**", "/api/currency-rate")
                .permitAll().anyRequest().authenticated().and()
                .logout().invalidateHttpSession(true).and()
                .csrf().csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
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
