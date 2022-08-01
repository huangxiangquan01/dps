package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author huangxq
 * @description: 泛化调用
 * @date 2022/5/915:51
 */
public class GenericDubboConsumerDemoTest extends DubboConsumerTest {

    @Reference(id = "demoService", version = "default", interfaceName = "cn.xqhuang.dps.service.DemoService", generic = true)
    private GenericService genericService;

    @Test
    public void test() {
        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"周瑜"});
        System.out.println(result);
    }
}