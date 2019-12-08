package com.github.akozubperez.math.arch.security;

import java.util.Arrays;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@RequiredArgsConstructor
public abstract class WebSecurityConfigurationBase extends WebSecurityConfigurerAdapter {

    public static final String XSRF_TOKEN = "XSRF-TOKEN";
    public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";

    private static final String[] AUTH_WHITELIST = {
        "/",
        "/auth/login",
        "/auth/signup",
        "/*.html",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js",
        "/**/*.jpg",
        "/**/*.gif",
        "/**/*.mp4",
        "/**/*.png",
        "/**/*.pdf",
        "/webjars/**"
    };

    @NonNull
    private SessionRegistry sessionRegistry;

    protected List<String> getAndPatternswhiteList() {
        return Arrays.asList(AUTH_WHITELIST);
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.headers().cacheControl().disable();
        http.headers().httpStrictTransportSecurity().disable();
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry);
        http.headers().frameOptions().deny();
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).denyAll()
                .antMatchers(HttpMethod.TRACE).denyAll()
                .antMatchers(HttpMethod.HEAD).denyAll()
                .antMatchers(getAndPatternswhiteList().stream().toArray(String[]::new)).permitAll()
                .antMatchers("/actuator", "/actuator/**", "/operation-events", "/operation-events/**").access("@c.hasPrivilege('OPERATION')")
                .antMatchers("/**/*.sql.tar.gz").access("@c.hasPrivilege('OPERATION')")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/auth/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID", XSRF_TOKEN).logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
    }
}
