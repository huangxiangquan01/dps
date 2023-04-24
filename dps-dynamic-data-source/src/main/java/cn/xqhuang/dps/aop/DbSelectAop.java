package cn.xqhuang.dps.aop;

import cn.xqhuang.dps.holder.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/24 11:50 星期一
 */
@Aspect
@Component
public class DbSelectAop {

    @Pointcut("@annotation(cn.xqhuang.dps.annotation.DB)")
    @Order(2)
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.setDataSourceType("db2");
    }
}
