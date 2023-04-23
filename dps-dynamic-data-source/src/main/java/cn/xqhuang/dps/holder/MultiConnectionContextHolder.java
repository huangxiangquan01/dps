package cn.xqhuang.dps.holder;

import cn.xqhuang.dps.proxy.ConnectProxy;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Class: MultiConnectionContextHolder</p>
 * <p>Description:  跨库事务线程标记、当前所有资源的持有者</p>
 *
 * @author Deven
 * @version 1.0
 * @date 2021-03-31
 */
public class MultiConnectionContextHolder {

    private static final InheritableThreadLocal<Set<ConnectProxy>> connectionThreadLocal = new InheritableThreadLocal<>();

    /**
     * 是否开启跨库事务 状态标记
     */
    private static final InheritableThreadLocal<Boolean> multiTransactionStatus = new InheritableThreadLocal<>();

    /**
     * 清除当前线程持有链接
     */
    public static void clearConnections() {
        connectionThreadLocal.remove();
    }

    /**
     * 获取当前线程持有链接
     */
    public static Set<ConnectProxy> getCurrentConnections() {
        if (CollectionUtils.isEmpty(connectionThreadLocal.get())) {
            return Collections.EMPTY_SET;
        }
        return connectionThreadLocal.get();
    }

    /**
     * 新增当前线程持有链接
     */
    public static void addConnection(ConnectProxy connection) {
        Set<ConnectProxy> connectProxies = connectionThreadLocal.get();
        if (Objects.isNull(connectProxies)) {
            connectProxies = new HashSet<>();
        }
        connectProxies.add(connection);
        connectionThreadLocal.set(connectProxies);
    }

    /**
     * @Description 查询当前线程是否开启了跨库事务
     * @return boolean
     */
     public static boolean getMultiTransactionStatus() {
         if (Objects.isNull(multiTransactionStatus.get())) {
             return false;
         }
         return multiTransactionStatus.get();
     }

    /**
     * @Description 设置当前线程跨库事务状态
     * @return boolean
     */
    public static void setMultiTransactionStatus(boolean status) {
        multiTransactionStatus.set(status);
    }


    /**
     * 清除当前线程跨库事务状态
     */
    public static void clearMultiTransactionStatus() {
        multiTransactionStatus.remove();
    }

}