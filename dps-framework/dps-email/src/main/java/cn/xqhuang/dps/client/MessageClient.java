package cn.xqhuang.dps.client;

import java.util.Map;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期五, 5月 05, 2023, 09:21
 **/
public interface MessageClient {

    void sendMessage(String templateId, Map<String, String> data);

}
