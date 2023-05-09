/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package cn.xqhuang.dps.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangxiangquan
 * @date
 */
public class WsSessionManager {

    public final static Logger log = LoggerFactory.getLogger(WsSessionManager.class);


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private final static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 保存连接 session 的地方
     */
    private final static ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param: key
     */
    public static void add(String key, Session session) {
        // 添加 session
        SESSION_POOL.put(key, session);
        onlineCount.incrementAndGet(); // + 1;
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param: key
     * @return:
     */
    public static Session remove(String key) {
        // 删除 session
        try {
            return SESSION_POOL.remove(key);
        } finally {
            onlineCount.decrementAndGet(); // + 1;
        }
    }

    /**
     * 删除并同步关闭连接
     *
     * @param: key
     */
    public static void removeAndClose(String key) {
        Session session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得 session
     *
     * @param: key
     * @return:
     */
    public static Session get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    /**
     * 群发自定义消息
     * */
    public static void send(String message, String uid) throws IOException {
        log.info("推送消息到窗口"+ "，推送内容:" + message);
        for (String id : SESSION_POOL.keySet()) {
            if (!Objects.equals(id, uid)) {
                sendMessage(get(id), message);
            }
        }
    }
    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}