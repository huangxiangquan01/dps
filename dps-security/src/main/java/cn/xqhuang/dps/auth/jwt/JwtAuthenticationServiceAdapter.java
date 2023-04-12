package cn.xqhuang.dps.auth.jwt;

import cn.xqhuang.dps.exception.UserTypeNotSupportedException;
import cn.xqhuang.dps.user.UserBasicInfo;
import cn.xqhuang.dps.user.UserContext;
import org.apache.xmlbeans.UserType;

public class JwtAuthenticationServiceAdapter implements JwtAuthenticationService {

    @Override
    public UserContext getUserContext(final String subject, final String userType) {
        /*final UserType userTypeEnum = EnumUtil.codeOf(UserType.class, userType);
        UserContext userContext;
        if (UserType.SYS_USER == userTypeEnum) {
            userContext = sysUserJwtAuthService.loadUserContextByUsername(subject);
        } else if (UserType.COOPERATED_PLATFORM_USER == userTypeEnum) {
            userContext = cooperatedPlatformUserJwtAuthService.loadUserContextByUsername(subject);
        } else {
            throw new UserTypeNotSupportedException(userType, String.format("不支持的用户类型:[%s]", userType));
        }*/
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
