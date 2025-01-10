package cn.xqhuang.dps.spring.proxy;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

public class BeanNameAutoProxyCreatorTest {

    public static void main(String[] args) {
        BeanNameAutoProxyCreatorTest beanNameAutoProxyCreatorTest = new BeanNameAutoProxyCreatorTest();
    }





    private BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();

        beanNameAutoProxyCreator.setBeanNames("userSe*");
        return beanNameAutoProxyCreator;
    }
}
