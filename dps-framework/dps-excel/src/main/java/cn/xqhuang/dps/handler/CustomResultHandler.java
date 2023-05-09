package cn.xqhuang.dps.handler;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.utils.DownloadProcessor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
public class CustomResultHandler implements ResultHandler {
    private final DownloadProcessor downloadProcessor;

    public CustomResultHandler(DownloadProcessor downloadProcessor) {
        this.downloadProcessor = downloadProcessor;
    }


    @Override
    public void handleResult(ResultContext resultContext) {
        // 获取每条数据
        User userInfo = (User) resultContext.getResultObject();
        if (userInfo.getAge() > 50){
            downloadProcessor.moreSheetProcessData(userInfo.toArray(),"大于50岁");
        }else {
            downloadProcessor.moreSheetProcessData(userInfo.toArray(),"小于50岁");
        }
    }
}
