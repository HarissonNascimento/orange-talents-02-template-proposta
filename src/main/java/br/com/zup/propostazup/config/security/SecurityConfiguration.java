package br.com.zup.propostazup.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(ar ->
                ar
                        .antMatchers(HttpMethod.GET, "/api/biometry/**").hasAuthority("SCOPE_card")
                        .antMatchers(HttpMethod.GET, "/api/proposal/**").hasAuthority("SCOPE_proposal")
                        .antMatchers(HttpMethod.POST, "/api/biometry/**").hasAuthority("SCOPE_card")
                        .antMatchers(HttpMethod.POST, "/api/proposal/**").hasAuthority("SCOPE_proposal")
                        .anyRequest().authenticated()
        ).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
