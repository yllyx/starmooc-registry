package io.github.jhipster.registry.security.oauth2;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.jhipster.registry.security.AuthoritiesConstants;

public class SimpleAuthoritiesExtractor implements AuthoritiesExtractor
{

    private final String oauth2AuthoritiesAttribute;

    public SimpleAuthoritiesExtractor(String oauth2AuthoritiesAttribute)
    {
        this.oauth2AuthoritiesAttribute = oauth2AuthoritiesAttribute;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map)
    {
        if (extractPrincipal(map).equals("admin"))
        {
            return Optional.ofNullable((List<String>)map.get(oauth2AuthoritiesAttribute)).filter(
                it -> !it.isEmpty()).orElse(
                    Collections.singletonList(AuthoritiesConstants.ADMIN)).stream().map(
                        role -> new SimpleGrantedAuthority(role)).collect(toList());
        }
        else
        {
            return Optional.ofNullable((List<String>)map.get(oauth2AuthoritiesAttribute)).filter(
                it -> !it.isEmpty()).orElse(
                    Collections.singletonList(AuthoritiesConstants.USER)).stream().map(
                        role -> new SimpleGrantedAuthority(role)).collect(toList());
        }

    }

    private static final String[] PRINCIPAL_KEYS =
        new String[]{"user", "username", "userid", "user_id", "login", "id", "name"};

    private Object extractPrincipal(Map<String, Object> map)
    {
        for (String key : PRINCIPAL_KEYS)
        {
            if (map.containsKey(key))
            {
                return map.get(key);
            }
        }

        return null;
    }
}
