package cn.xqhuang.dps.auth.jwt;

import cn.xqhuang.dps.user.UserBasicInfo;
import cn.xqhuang.dps.user.UserContext;

public interface JwtAuthenticationService {

    UserContext getUserContext(final String subject, final String userType);

    UserBasicInfo login(final String username, final String password, final String userType);

    UserBasicInfo fastLogin(final String mobile, final String dynamicCode, final String userType);

    UserBasicInfo getUserBasicInfoBySubject(final String subject, final String userType);

    void changePassword(final String originPwd, final String newPwd, final String userType);

    void resetPassword(final Long userId, final String userType);
}
