package cn.xqhuang.dps.config;

import cn.xqhuang.dps.websocket.ClientWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketClientConfig {
    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public ClientWebSocketHandler clientWebSocketHandler() {
        return new ClientWebSocketHandler();
    }

    @Bean
    public WebSocketConnectionManager connectionManager() {
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(
                webSocketClient(),
                clientWebSocketHandler(),
                "url"
        );
        connectionManager.setAutoStartup(true);
        return connectionManager;
    }
}
