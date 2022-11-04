package cn.xqhuang.dps.auth.jwt;

import cn.xqhuang.dps.user.UserBasicInfo;
import cn.xqhuang.dps.user.UserContext;

public class JwtAuthenticationServiceAdapter implements JwtAuthenticationService {

    @Override
    public UserContext getUserContext(final String subject, final String userType) {
        return null;
    }

    @Override
    public UserBasicInfo login(final String username, final String password, final String userType) {
        return new UserBasicInfo.Builder()
                    .id(1L)
                    .loginName(username)
                    .build();

    }

    @Override
    public UserBasicInfo fastLogin(final String mobile, final String dynamicCode, final String userType) {
        return null;
    }

    @Override
    public UserBasicInfo getUserBasicInfoBySubject(final String subject, final String userType) {
        return null;
    }

    @Override
    public void changePassword(final String originPwd, final String newPwd, final String userType) {

    }

    @Override
    public void resetPassword(final Long userId, final String userType) {

    }
}
