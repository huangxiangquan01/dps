package cn.xqhuang.dps.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/1109:25
 */

@Component
@ServerEndpoint("/ws/{uid}")
public class WebSocketServerEndpoint {

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) throws IOException {
        System.out.println("连接成功" + session.getId());
        WsSessionManager.add(uid, session);     //加入set中
        WsSessionManager.send(uid + "连接成功", uid);
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session, @PathParam("uid") String uid) {
        WsSessionManager.remove(uid);
        System.out.println("连接关闭" + uid);
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public void onMsg(String text, @PathParam("uid") String uid) throws IOException {
        WsSessionManager.send(text, uid);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
