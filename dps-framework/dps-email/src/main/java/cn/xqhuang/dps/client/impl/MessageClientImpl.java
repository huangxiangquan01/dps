package cn.xqhuang.dps.client.impl;

import cn.hutool.http.HttpUtil;
import cn.xqhuang.dps.client.MessageClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期五, 5月 05, 2023, 09:23
 **/
@Service
public class MessageClientImpl implements MessageClient {

    @Value("${wechat.appid:}")
    private String appid = "";

    @Value("${wechat.secret:}")
    private String secret = "";

    @Override
    public void sendMessage(String templateId, Map<String, String> data) {
        String accessToken = getAccessToken();
        List<String> openIds = getOpenIdsFromWX(accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        Map<String, Object> body = new HashMap<>();
        body.put("template_id", templateId);
        body.put("data", data);
        for (String openId : openIds) {
            body.put("touser", openId);
            String responseEntity = HttpUtil.post(url, body);
            System.out.println("推送消息: " + responseEntity);
        }
    }

    /**
     * 获取access_token
     * @return 获取access_token
     */
    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String response = HttpUtil.get(url);
        JSONObject object = JSONObject.parseObject(response);
        return object.getString("access_token");
    }

    /**
     * 通过微信接口获得微信公众号订阅者的openid
     */
    public List<String> getOpenIdsFromWX(String accessToken) {
        // https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        String response = HttpUtil.post(url,"");
        JSONObject object = JSONObject.parseObject(response);
        List<String> openIds = JSONObject.parseArray(object.getJSONObject("data").getString("openid"), String.class);
        return openIds;
    }
}
