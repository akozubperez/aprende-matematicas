package com.github.akozubperez.math.arch.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;

@Configuration
@Profile("test-auth")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfigurationDev extends WebSecurityConfigurationBase {

    private static final String[] AUTH_WHITELIST = {
        // -- swagger ui
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs"
    };

    private SimpleAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfigurationDev(SimpleAuthenticationProvider authenticationProvider, SessionRegistry sessionRegistry) {
        super(sessionRegistry);
        this.authenticationProvider = authenticationProvider;
    }
    
    @Override
    protected List<String> getAndPatternswhiteList() {
        List<String> result = new ArrayList<>(super.getAndPatternswhiteList());
        result.addAll(Arrays.asList(AUTH_WHITELIST));
        return result;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
