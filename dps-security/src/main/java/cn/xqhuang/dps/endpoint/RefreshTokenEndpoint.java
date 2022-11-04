package cn.xqhuang.dps.endpoint;

import cn.xqhuang.dps.config.JwtSettings;
import cn.xqhuang.dps.config.WebSecurityConfiguration;
import cn.xqhuang.dps.exception.JwtBadTokenException;
import cn.xqhuang.dps.exception.JwtInvalidatedTokenException;
import cn.xqhuang.dps.user.UserBasicInfo;
import cn.xqhuang.dps.auth.jwt.JwtAuthenticationService;
import cn.xqhuang.dps.auth.jwt.extractor.TokenExtractor;
import cn.xqhuang.dps.auth.jwt.verifier.TokenVerifier;
import cn.xqhuang.dps.model.token.JwtToken;
import cn.xqhuang.dps.model.token.JwtTokenFactory;
import cn.xqhuang.dps.model.token.RawAccessJwtToken;
import cn.xqhuang.dps.model.token.RefreshToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class RefreshTokenEndpoint {

    private final JwtTokenFactory tokenFactory;

    private final JwtSettings jwtSettings;

    private final TokenVerifier tokenVerifier;

    private final TokenExtractor tokenExtractor;

    private final JwtAuthenticationService jwtAuthenticationService;


    public RefreshTokenEndpoint(JwtAuthenticationService jwtAuthenticationService,
                                TokenVerifier tokenVerifier,
                                TokenExtractor tokenExtractor,
                                JwtTokenFactory tokenFactory,
                                JwtSettings jwtSettings) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
        this.tokenFactory = tokenFactory;
        this.jwtSettings = jwtSettings;
    }

    @RequestMapping(value="${spring.security.auth.refresh-token-url:}", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_UTF8_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfiguration.AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getRefreshTokenSigningKey()).orElseThrow(() -> new JwtBadTokenException(rawToken, "Token无效"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new JwtInvalidatedTokenException(refreshToken, "Token已作废");
        }

        String subject = refreshToken.getClaims().getBody().getSubject();
        String userType = refreshToken.getClaims().getBody().get("userType", String.class);
        UserBasicInfo userBasicInfo = jwtAuthenticationService.getUserBasicInfoBySubject(subject, userType);
        return tokenFactory.createAccessJwtToken(subject, userBasicInfo.getType());
    }
}
