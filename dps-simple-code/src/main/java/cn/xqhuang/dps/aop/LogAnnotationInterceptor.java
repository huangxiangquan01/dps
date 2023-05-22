package cn.xqhuang.dps.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期三, 5月 17, 2023, 09:00
 **/
public class LogAnnotationInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            System.out.println("方法已经被执行");
        }
    }
}
