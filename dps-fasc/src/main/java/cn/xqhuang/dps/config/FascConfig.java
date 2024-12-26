package cn.xqhuang.dps.config;

import com.alibaba.fastjson.JSON;
import com.fasc.open.api.config.HttpConfig;
import com.fasc.open.api.constants.RequestConstants;
import com.fasc.open.api.enums.http.SignTypeEnum;
import com.fasc.open.api.event.signtask.SignTaskActorSignedCallbackDto;
import com.fasc.open.api.exception.ApiException;
import com.fasc.open.api.stratey.DefaultJsonStrategy;
import com.fasc.open.api.utils.crypt.FddCryptUtil;
import com.fasc.open.api.utils.json.JacksonUtil;
import com.fasc.open.api.utils.json.ParameterizedTypeBaseRes;
import com.fasc.open.api.v5_1.client.OpenApiClient;
import com.fasc.open.api.v5_1.res.doc.UploadFileByUrlRes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * fasc配置
 *
 * @author huangxiangquan@yintatech.com
 * @date 2024/05/09 16:39
 */
@Configuration
public class FascConfig {

    @Bean
    public OpenApiClient openApiClient() {
        OpenApiClient openApiClient = new OpenApiClient("", "", "");

        return openApiClient;
    }
}
