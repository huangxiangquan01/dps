package cn.xqhuang.dps.spring.proxy;

import cn.xqhuang.dps.spring.proxy.impl.UserServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProxyFactoryBeanTest {

    public static void main(String[] args) {
        ProxyFactoryBeanTest proxyFactoryBeanTest = new ProxyFactoryBeanTest();

        UserService userService = new UserServiceImpl();

        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userService);
        proxyFactoryBean.addAdvice(proxyFactoryBeanTest.getMethodInterceptor());

        UserService object = (UserService)proxyFactoryBean.getObject();
        object.add("222");
    }

    public MethodInterceptor getMethodInterceptor() {
        return new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                System.out.printf("before advice method invoke: %s\n", invocation.getMethod().getName());
                Object proceed = invocation.proceed();
                System.out.printf("after advice method invoke: %s\n", invocation.getMethod().getName());
                return proceed;
            }
        };
    }
}
