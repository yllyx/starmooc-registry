package io.github.jhipster.registry.security.oauth2;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public class SimplePrincipalExtractor implements PrincipalExtractor
{

    private final String oauth2PrincipalAttribute;

    private static final String[] PRINCIPAL_KEYS =
        new String[]{"user", "username", "userid", "user_id", "login", "id", "name"};

    public SimplePrincipalExtractor(String oauth2PrincipalAttribute)
    {
        this.oauth2PrincipalAttribute = oauth2PrincipalAttribute;
    }

    // @Override
    // public Object extractPrincipal(Map<String, Object> map) {
    // return map.getOrDefault(oauth2PrincipalAttribute, "unknown");
    // }

    @Override
    public Object extractPrincipal(Map<String, Object> map)
    {
        for (String key : PRINCIPAL_KEYS)
        {
            if (map.containsKey(key))
            {
                return map.get(key);
            }
        }

        return map.getOrDefault(oauth2PrincipalAttribute, "unknown");
    }
}
