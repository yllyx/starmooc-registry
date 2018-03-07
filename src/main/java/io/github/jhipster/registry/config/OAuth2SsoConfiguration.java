package io.github.jhipster.registry.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import io.github.jhipster.registry.security.AuthoritiesConstants;

@EnableOAuth2Sso
@Configuration
@Profile(Constants.PROFILE_OAUTH2)
@AutoConfigureBefore(OAuth2SecurityConfiguration.class)
public class OAuth2SsoConfiguration extends WebSecurityConfigurerAdapter {

    private final RequestMatcher authorizationHeaderRequestMatcher;

    public OAuth2SsoConfiguration(@Qualifier("authorizationHeaderRequestMatcher") RequestMatcher authorizationHeaderRequestMatcher) {
        this.authorizationHeaderRequestMatcher = authorizationHeaderRequestMatcher;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/content/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .requestMatcher(new NegatedRequestMatcher(authorizationHeaderRequestMatcher))
            .httpBasic()
            .realmName("JHipster Registry")
        .and()
            .authorizeRequests()
            .antMatchers("/services/**").authenticated()
            .antMatchers("/eureka/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/config/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/swagger-resources/configuration/**").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
            .anyRequest().permitAll();
    }
}
