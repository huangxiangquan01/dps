package cn.xqhuang.dps.helper;

import cn.xqhuang.dps.user.UserContext;
import cn.xqhuang.dps.user.GrantedRole;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityContextHelper {

    public static UserContext getUserContext() {
        return (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        final UserContext userContext = getUserContext();
        return userContext.getUserProfile().getBasicInfo().getId();
    }

    public static String getLoginName() {
        final UserContext userContext = getUserContext();
        return userContext.getUserProfile().getBasicInfo().getLoginName();
    }

    //获取邮箱地址
    public static String getEmail() {
        final UserContext userContext = getUserContext();
        return userContext.getUserProfile().getBasicInfo().getEmail();
    }

    public static String getUserType() {
        final UserContext userContext = getUserContext();
        return userContext.getUserProfile().getBasicInfo().getType();
    }

    public static List<String> getGrantedRoles() {
        return getUserContext()
                .getUserProfile()
                .getGrantedRoles()
                .stream()
                .map(GrantedRole::getCode)
                .collect(Collectors.toList());
    }
}
