package cn.xqhuang.dps.model.token;

import cn.xqhuang.dps.config.JwtSettings;
import cn.xqhuang.dps.user.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 */
public class JwtTokenFactory {

    private static final String CLAIMS_KEY_USER_TYPE = "userType";

    private static final String CLAIMS_KEY_SCOPES = "scopes";

    private JwtSettings settings;

    public void setSettings(final JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     */
    public AccessJwtToken createAccessJwtToken(String username, String userType) {
        return createToken(username, userType, settings.getTokenExpirationTime(), ChronoUnit.MINUTES, false);
    }

    public JwtToken createRefreshToken(String username, String userType) {
        return createToken(username, userType, settings.getRefreshTokenExpTime(), ChronoUnit.MINUTES, true);
    }

    private AccessJwtToken createToken(final String subject,
                                       final String userType,
                                       long amountToAdd,
                                       final ChronoUnit timeUnit,
                                       boolean refreshToken) {
        final Claims claims = Jwts.claims().setSubject(subject);
        claims.put(CLAIMS_KEY_USER_TYPE, userType);
        if (refreshToken) {
            claims.put(CLAIMS_KEY_SCOPES, Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        }


        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime expireTime = currentTime.plus(amountToAdd, timeUnit);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey());

        if (refreshToken) {
            jwtBuilder = jwtBuilder.setId(UUID.randomUUID().toString());
        }

        return new AccessJwtToken(jwtBuilder.compact(), claims);
    }

}
