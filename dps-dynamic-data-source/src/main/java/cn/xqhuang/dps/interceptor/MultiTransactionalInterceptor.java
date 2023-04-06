package cn.xqhuang.dps.interceptor;

import cn.xqhuang.dps.holder.MultiConnectionContextHolder;
import cn.xqhuang.dps.proxy.ConnectProxy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class MultiTransactionalInterceptor {

    /**
     *  对添加了@MultiTransactional方法进行线程标记
     */
    @Before(value = "@annotation(cn.xqhuang.dps.annotation.MultiTransactional)")
    public void before() {
        MultiConnectionContextHolder.setMutilTransactionStatus(true);
    }

    /**
     *  对有@MultiTransactional方法进行线程标记
     *  且抛出异常的线程进行事务回退
     */
    @AfterThrowing(value = "@annotation(cn.xqhuang.dps.annotation.MultiTransactional)")
    public void afterThrowing() {
        // 从当前线程持有的链接、回退事务
        Set<ConnectProxy> currentConnections = MultiConnectionContextHolder.getCurrentConections();
        try {
            for (ConnectProxy connection : currentConnections) {
                connection.rollback();
                connection.realClose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            MultiConnectionContextHolder.clearConnections();
            MultiConnectionContextHolder.clearMultiTransactionStatus();
        }
    }

    /**
     *  对有@MultiTransactional线程标记
     *  且正常执行结束的方法进行事务提交
     *
     */
    @AfterReturning(value = "@annotation(cn.xqhuang.dps.annotation.MultiTransactional)")
    public void after() {
        // 跨库事务方法成功执行、提交事务
        Set<ConnectProxy> currentConnections = MultiConnectionContextHolder.getCurrentConections();
        try {
            for (ConnectProxy connection : currentConnections) {
                connection.realCommit();
                connection.realClose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            MultiConnectionContextHolder.clearConnections();
            MultiConnectionContextHolder.clearMultiTransactionStatus();
        }
    }
}