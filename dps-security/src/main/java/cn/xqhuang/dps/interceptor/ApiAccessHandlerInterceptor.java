package cn.xqhuang.dps.interceptor;

import cn.xqhuang.dps.config.WebSecurityConfiguration;
import cn.xqhuang.dps.helper.SecurityContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ApiAccessHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiAccessHandlerInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isInfoEnabled()) {
            final String remoteAddr = getClientIp(request);
            final String httpMethod = request.getMethod();
            final String accessToken = request.getHeader(WebSecurityConfiguration.AUTHENTICATION_HEADER_NAME);

            String loginName = null;
            String userType = null;
            if (StringUtils.isNotBlank(accessToken)) {
                try {
                    loginName = SecurityContextHelper.getLoginName();
                } catch (Exception e) {
                    loginName = "";
                }
                try {
                    userType = SecurityContextHelper.getUserType();
                } catch (Exception e) {
                    userType = "";
                }
            }

            final String path = request.getRequestURI();
            final String queryString = request.getQueryString();

            log.info(String.format("remoteAddr: [%s], httpMethod: [%s], loginName: [%s], userType: [%s], path: [%s], queryString: [%s]",
                    remoteAddr,
                    httpMethod,
                    loginName,
                    userType,
                    path,
                    Objects.isNull(queryString) ? "" : queryString));
        }
        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(clientIp)) {
            clientIp = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
