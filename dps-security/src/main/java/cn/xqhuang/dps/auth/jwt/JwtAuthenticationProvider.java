package cn.xqhuang.dps.auth.jwt;

import cn.xqhuang.dps.config.JwtSettings;
import cn.xqhuang.dps.exception.UserTypeNotSupportedException;
import cn.xqhuang.dps.user.UserContext;
import cn.xqhuang.dps.user.GrantedRole;
import cn.xqhuang.dps.model.token.JwtToken;
import cn.xqhuang.dps.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Objects;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link JwtToken} to perform authentication.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private JwtSettings jwtSettings;
    private JwtAuthenticationService jwtAuthenticationService;

    public void setJwtSettings(final JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    public void setJwtAuthenticationService(final JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String userType = jwsClaims.getBody().get("userType", String.class);

        UserContext userContext = jwtAuthenticationService.getUserContext(jwsClaims.getBody().getSubject(), userType);
        if (Objects.isNull(userContext)) {
            throw new UserTypeNotSupportedException(userType, String.format("不支持的用户类型:[%s]", userType));
        }

        return new JwtAuthenticationToken(userContext, AuthorityUtils.createAuthorityList(userContext.getUserProfile().getGrantedRoles().stream()
                .map(GrantedRole::getCode)
                .toArray(String[]::new)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
