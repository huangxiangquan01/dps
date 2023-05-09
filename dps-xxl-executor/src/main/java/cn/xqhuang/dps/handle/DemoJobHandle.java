package cn.xqhuang.dps.handle;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author huangxq
 * @description
 * @date 2023/4/14 10:16 星期五
 */
@Component
public class DemoJobHandle {

    private final static Logger logger = LoggerFactory.getLogger(DemoJobHandle.class);

    @XxlJob("demoJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("");
        logger.info("XXL_JOB, hello world");
    }
}
