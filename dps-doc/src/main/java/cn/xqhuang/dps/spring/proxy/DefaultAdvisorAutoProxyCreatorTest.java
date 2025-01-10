package cn.xqhuang.dps.spring.proxy;

import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DefaultAdvisorAutoProxyCreatorTest {

    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        return advisorAutoProxyCreator;
    }

    private MethodInterceptor interceptor() {
        return new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                return invocation.proceed();
            }
        };
    }
    private Pointcut pointcut() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(Setter.class, true);
        // Pointcut methodPointcut = new AnnotationMatchingPointcut(null, DataPermission.class, true);
        return new ComposablePointcut(classPointcut);//.union(methodPointcut);
    }

    public static void main(String[] args) {

    }
}
