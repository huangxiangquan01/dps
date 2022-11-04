package cn.xqhuang.dps.endpoint;

import cn.xqhuang.dps.user.UserContext;
import cn.xqhuang.dps.user.UserProfile;
import cn.xqhuang.dps.helper.SecurityContextHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveUserProfileEndpoint {

    @RequestMapping(value="${spring.security.auth.retrieve-user-profile-url:}", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_UTF8_VALUE })
    public UserProfile retrieveUserProfile() {
        UserContext userContext = SecurityContextHelper.getUserContext();
        return userContext.getUserProfile();
    }
}
