package cn.xqhuang.dps.spring.proxy;

import cn.xqhuang.dps.spring.proxy.impl.UserServiceImpl;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class ProxyFactoryTest {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(userService);
        proxyFactory.addAdvisor(new PointcutAdvisor() {
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return method.getName().contains("add");
                    }
                };
            }

            @Override
            public Advice getAdvice() {
                return (MethodInterceptor) invocation -> {
                    System.out.printf("before advice method invoke: %s\n", invocation.getMethod().getName());
                    Object proceed = invocation.proceed();
                    System.out.printf("after advice method invoke: %s\n", invocation.getMethod().getName());
                    return proceed;
                };
            }

            @Override
            public boolean isPerInstance() {
                return false;
            }
        });

        UserService proxy = (UserService) proxyFactory.getProxy();

        proxy.add("111212");

        proxy.add2("2");
    }
}
