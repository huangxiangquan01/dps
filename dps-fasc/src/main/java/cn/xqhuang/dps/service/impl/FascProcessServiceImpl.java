package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.service.FascProcessService;
import com.fasc.open.api.bean.base.BaseRes;
import com.fasc.open.api.constants.RequestConstants;
import com.fasc.open.api.enums.doc.FileTypeEnum;
import com.fasc.open.api.utils.ResultUtil;
import com.fasc.open.api.v5_1.client.DocClient;
import com.fasc.open.api.v5_1.client.OpenApiClient;
import com.fasc.open.api.v5_1.client.ServiceClient;
import com.fasc.open.api.v5_1.req.doc.UploadFileByUrlReq;
import com.fasc.open.api.v5_1.res.doc.UploadFileByUrlRes;
import com.fasc.open.api.v5_1.res.service.AccessTokenRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Fasc流程服务实现
 *
 * @author huangxiangquan@yintatech.com
 * @date 2024/05/09 16:42
 */
@Service
public class FascProcessServiceImpl implements FascProcessService {

    @Resource
    private OpenApiClient openApiClient;

    private String getAccessToken() {
        try {
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            if (!Objects.equals(RequestConstants.SUCCESS_CODE, accessTokenRes.getCode())) {
                throw new com.fasc.open.api.exception.ApiException(accessTokenRes.getMsg());
            }
            return accessTokenRes.getData().getAccessToken();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadByUrl(String fileUrl) {
        try {
            DocClient docClient = new DocClient(openApiClient);
            UploadFileByUrlReq req = new UploadFileByUrlReq();
            req.setFileUrl(fileUrl);
            req.setFileType(FileTypeEnum.DOC.getCode());
            req.setAccessToken(getAccessToken());
            BaseRes<UploadFileByUrlRes> res = docClient.uploadFileByUrl(req);
            if (!Objects.equals(RequestConstants.SUCCESS_CODE, res.getCode())) {
                throw new RuntimeException(res.getMsg());
            }
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            System.out.println(res.getData().getFddFileUrl());
        } catch (Exception e ){
            throw new RuntimeException(e);
        }
    }
}
